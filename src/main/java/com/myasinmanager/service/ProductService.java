package com.myasinmanager.service;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.google.common.collect.Lists;
import com.myasinmanager.dto.BatchSummary;
import com.myasinmanager.exception.ConflictException;
import com.myasinmanager.exception.SPAPIException;
import com.myasinmanager.model.ProductEntity;
import com.myasinmanager.model.TagEntity;
import com.myasinmanager.repository.ProductRepository;
import com.myasinmanager.repository.TagRepository;
import com.myasinmanager.security.model.User;
import com.myasinmanager.security.repository.UserRepository;
import com.myasinmanager.spapi.api.CatalogApi;
import com.myasinmanager.spapi.api.FeesApi;
import com.myasinmanager.spapi.api.ProductPricingApi;
import com.myasinmanager.spapi.client.ApiException;
import com.myasinmanager.spapi.model.catalog.Item;
import com.myasinmanager.spapi.model.catalog.ItemSearchResults;
import com.myasinmanager.spapi.model.product.fees.*;
import com.myasinmanager.spapi.model.product.pricing.CompetitivePriceType;
import com.myasinmanager.spapi.model.product.pricing.GetPricingResponse;
import com.myasinmanager.spapi.model.product.pricing.OfferListingCountType;
import com.myasinmanager.spapi.model.product.pricing.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@Transactional
public class ProductService {

    private static final String MARKETPLACE_ID_US = "ATVPDKIKX0DER";

    private static final List<String> MARKETPLACES_IDS = List.of(MARKETPLACE_ID_US);

    private static final List<String> INCLUDE_DATA = List.of("attributes", "identifiers", "images", "productTypes",
            "salesRanks", "summaries");

    private static final List<String> SUPPLIERS = List.of("Walmart", "Chedraui", "Etsy", "Ebay");
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CatalogApi catalogApi;

    @Autowired
    private ProductPricingApi productPricingApi;

    @Autowired
    private FeesApi feesApi;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private UserRepository userRepository;

    public Page<ProductEntity> findAll(Pageable pageable, String username, Integer[] tags) {
        if (Objects.isNull(username)) {
            throw new ConflictException("Username must be null");
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
        log.debug("User:{}", user.getId());
        Page<ProductEntity> productsPaginatedByUserId = productRepository.findByUserId(user.getId(), pageable);

        if (Objects.nonNull(tags) && tags.length > 0) {
            List<Integer> tagsIds = Stream.of(tags).mapToInt(i -> i).boxed().collect(Collectors.toList());
            List<ProductEntity> productsFilteredByTags = productsPaginatedByUserId.getContent().stream()
                    .filter(p -> p.getTagsId().containsAll(tagsIds)).collect(Collectors.toList());
            log.debug("Response  findAll:{}", productsFilteredByTags);
            return new PageImpl<ProductEntity>(productsFilteredByTags, pageable, productsFilteredByTags.size());
        }
        log.debug("Response  findAll:{}", productsPaginatedByUserId.getContent());
        return productsPaginatedByUserId;
    }
    public ProductEntity create(ProductEntity product) {
        log.debug("Inserting product :{}", product);
        ProductEntity productSaved = productRepository.save(product);
        log.debug("Product with asin {} saved", product.getAsin());
        return productSaved;
    }

    public void setTagsToProduct(Integer productId, List<Integer> tagsIds) {
        log.debug("Adding tags ");
        ProductEntity product = productRepository.findById(productId.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));

        Set<TagEntity> tags = tagsIds.stream().filter(Objects::nonNull)
                .map(t -> tagRepository.findById(t.longValue()).get()).collect(Collectors.toSet());

        product.setTags(tags);
        productRepository.save(product);
        log.debug("Tags were added to the product");
    }

    public void addNewTagToProduct(Integer productId, String username, String tagName) {
        log.debug("Adding tags");
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));

        ProductEntity product = productRepository.findById(productId.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));

        TagEntity tag = TagEntity
                .builder()
                .name(tagName)
                .userId(user.getId())
                .build();
        tagRepository.save(tag);

        product.getTags().add(tag);
        productRepository.save(product);
        log.debug("Tags were added to the product");
    }

    public void setNotesToProduct(Integer productId, String notes) {
        log.debug("Adding notes {}", notes);
        ProductEntity product = productRepository.findById(productId.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));

        product.setNotes(notes);

        productRepository.save(product);
        log.debug("Notes were added to the product");
    }

    public void updateProduct(Integer productId, ProductEntity productRequest) {
        log.debug("Updating product");
        ProductEntity product = productRepository.findById(productId.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));

        if (Objects.nonNull(productRequest.getSupplier())) {
            product.setSupplier(productRequest.getSupplier());
        }

        if (Objects.nonNull(productRequest.getSupplierLink())) {
            product.setSupplierLink(productRequest.getSupplierLink());
        }

        if (Objects.nonNull(productRequest.getBuyCost())) {
            final BigDecimal previousBuyCost = product.getBuyCost();
            final BigDecimal buyCost = productRequest.getBuyCost();
            final BigDecimal currentBBPrice = product.getCurrentBBPrice();
            final BigDecimal feesAmount = currentBBPrice.subtract(product.getNetProfit()).subtract(previousBuyCost);
            // Calculations to get the ROI and profit
            BigDecimal netProfit = currentBBPrice.subtract(feesAmount).subtract(buyCost);
            BigDecimal roi = netProfit.divide(buyCost, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));

            product.setRoi(roi);
            product.setNetProfit(netProfit);
            product.setBuyCost(buyCost);
            log.debug("Product buy cost updated {}", product);
        }

        productRepository.save(product);
        log.debug("Product updated");
    }

    public void deleteProduct(Integer productId) {
        log.debug("Deleting product");
        ProductEntity product = productRepository.findById(productId.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));

        productRepository.delete(product);
        log.debug("Product deleted");
    }


    public void createByBatch(List<ProductEntity> productsRequest, String username) {
        log.debug("In ProductService createByBatch");
        // User validation
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));

        // Slip the items in batch of 20

        List<List<ProductEntity>> productsBatch = Lists.partition(productsRequest, 10);
        log.debug("Products batch size {}", productsBatch.size());

        List<BatchSummary> batchSummary = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Void>> tasks = new ArrayList<>();

        for (List<ProductEntity> batch : productsBatch) {
//            tasks.add(() -> {
            try {

                processBatch(batch, user, batchSummary);
                log.debug("Waiting for next batch 3 seconds");
                Thread.sleep(2000);
            } catch (SPAPIException e) {
                log.error("SPAPI Error processing batch with exception {}", e.getMessage(), e);
            } catch (Exception e) {
                log.error("Unexpected Error processing batch {}", e.getMessage(), e);
            }
//                return null;
//            });
        }
//        try {
//            executorService.invokeAll(tasks);
//        } catch (InterruptedException e) {
//            log.error("Error processing in parallel", e);
//        }
        log.debug("---------------------------------------------------Report------------------------------------------------------");
        log.debug("Products by batch created, summary");
        log.debug("Total input items {}", productsRequest.size());
        log.debug("Items created successfully {}", batchSummary.stream().map(BatchSummary::getSuccess).reduce(0, Integer::sum));
        log.debug("Items failed {}", batchSummary.stream().map(batch -> batch.getFailedItems().size()).reduce(0, Integer::sum));
        log.debug("Items not found {}", batchSummary.stream().map(batch -> batch.getItemsNotFound().size()).reduce(0, Integer::sum));
        log.debug("---------------------------------------------------Errors------------------------------------------------------");
        batchSummary.stream().flatMap(batch -> batch.getFailedItems().stream()).forEach(item -> log.debug("Item failed [asin={}, message={}]", item.getAsin(), item.getMessage()));
        log.debug("---------------------------------------------------Report------------------------------------------------------");
    }


    private void processBatch(List<ProductEntity> productsRequest, User user, List<BatchSummary> batchSummary) throws SPAPIException {

        BatchSummary batch = new BatchSummary();

        // ASIN from csv productsRequest imported
        List<String> asins = productsRequest.stream().map(p -> p.getAsin()).collect(Collectors.toList());

        // Fetch items in SPAPI Catalog items
        ItemSearchResults itemResponse = getItemSearchResults(productsRequest, batchSummary, batch, asins);

        // Get competitive pricing to get the bb price
        GetPricingResponse getPricingResponse = getGetPricingResponse(productsRequest, batchSummary, batch, asins);

        List<BatchSummary.FailedItems> failedItems = new ArrayList<>();
        AtomicInteger success = new AtomicInteger(0);

        List<Price> prices = getPricingResponse.getPayload();
        // Process items
        for (Item item : itemResponse.getItems()) {
            String asin = item.getAsin();
            try {
                // Price item
                Optional<Price> priceItemOpt = prices.stream().filter(p -> p.getASIN().equals(asin)).findFirst();
                if (priceItemOpt.isEmpty()) {
                    log.error("Price item not found for asin [{}]", asin);
                    failedItems.add(BatchSummary.FailedItems
                            .builder()
                            .asin(asin)
                            .message("Price not found")
                            .build());
                    continue;
                }
                Price priceItem = priceItemOpt.get();
                // Get the lowest price
                Optional<CompetitivePriceType> pricing = priceItem.getProduct().getCompetitivePricing().getCompetitivePrices().stream().filter(cp -> Objects.nonNull(cp.getCompetitivePriceId())).findFirst();
                // Get the seller count
                Integer sellerCount = 0;
                Optional<OfferListingCountType> sellerCountOptNew = priceItem.getProduct().getCompetitivePricing().getNumberOfOfferListings().stream().filter(l -> l.getCondition().equals("New")).findFirst();

                if (sellerCountOptNew.isPresent()) {
                    sellerCount = sellerCountOptNew.get().getCount();
                } else {
                    Optional<OfferListingCountType> sellerCountOptUsed = priceItem.getProduct().getCompetitivePricing().getNumberOfOfferListings().stream().filter(l -> l.getCondition().equals("Used")).findFirst();
                    if (sellerCountOptUsed.isPresent()) {
                        sellerCount = sellerCountOptUsed.get().getCount();
                    }
                }

                BigDecimal bbPrice = BigDecimal.ZERO;
                if (pricing.isPresent()) {
                    bbPrice = pricing.get().getPrice().getListingPrice().getAmount();
                }
                BigDecimal feesAmount = getFeesAmount(asin, bbPrice);

                ProductEntity request = productsRequest.stream().filter(p -> p.getAsin().equals(item.getAsin())).findFirst().get();
                ProductEntity productRequest = productItemToProductEntity(request, item, bbPrice, feesAmount, sellerCount);
                productRequest.setUserId(user.getId());

                Optional<ProductEntity> productDBOpt = productRepository.findByAsinAndUserId(productRequest.getAsin(), user.getId());
                if (productDBOpt.isPresent()) {
                    ProductEntity productDB = productDBOpt.get();

                    // Update product only if supplier link, supplier or buy cost are different
                    if (!request.getSupplier().equals(productDB.getSupplier()) && !request.getSupplierLink().equals(productDB.getSupplierLink())) {
                        log.info("Supplier changed from {} to {}", request.getSupplier(), productRequest.getSupplier());
                        success.getAndIncrement();
                        this.create(productRequest);
                    } else {
                        log.info("Supplier not changed");
                    }
                } else {
                    success.getAndIncrement();
                    this.create(productRequest);
                }
            } catch (Exception e) {
                var message = String.format("Unexpected error processing item [%s]", e.getMessage());
                BatchSummary.FailedItems failedItem = BatchSummary.FailedItems
                        .builder()
                        .asin(asin)
                        .message(message)
                        .build();
                failedItems.add(failedItem);
                log.error(message, e);
            }
        }
        List<String> asinInCatalog = itemResponse.getItems().stream().map(i -> i.getAsin()).collect(Collectors.toList());

        batch.setTotal(productsRequest.size());
        batch.setItemsNotFound(productsRequest.stream().map(i -> i.getAsin()).filter(asin -> !asinInCatalog.contains(asin)).collect(Collectors.toList()));
        batch.setSuccess(success.get());
        batch.setFailedItems(failedItems);
        batch.setFailed(failedItems.size());
        batchSummary.add(batch);
    }

    private BigDecimal getFeesAmount(String asin, BigDecimal bbPrice) throws ApiException {
        BigDecimal feesAmount = BigDecimal.ZERO;
        try {
            final GetMyFeesEstimateRequest getMyFeesEstimateRequest = new GetMyFeesEstimateRequest().
                    feesEstimateRequest(new FeesEstimateRequest()
                            .marketplaceId(MARKETPLACE_ID_US)
                            .identifier("ASIN")
                            .priceToEstimateFees(new PriceToEstimateFees().listingPrice(new MoneyType()
                                    .currencyCode("USD")
                                    .amount(bbPrice))));

            final GetMyFeesEstimateResponse myFeesEstimateForASIN = feesApi.getMyFeesEstimateForASIN(getMyFeesEstimateRequest, asin);
            log.debug("Fees estimate {}", myFeesEstimateForASIN);

            final FeesEstimateResult feesEstimateResult = myFeesEstimateForASIN.getPayload().getFeesEstimateResult();
            log.debug("Fees estimate result {}", feesEstimateResult);

            final FeesEstimate feesEstimate = feesEstimateResult.getFeesEstimate();
            log.debug("Fees {}", feesEstimate);

            feesAmount = feesEstimate.getTotalFeesEstimate().getAmount();
        } catch (ApiException e) {
            var message = String.format("Error fetching in [FeesApi] for item %s from SPAPI with error {}", asin, e.getMessage()
            );
            log.error(message);
        }
        return feesAmount;
    }

    private ItemSearchResults getItemSearchResults(List<ProductEntity> productsRequest, List<BatchSummary> batchSummary, BatchSummary batch, List<String> asins) {
        ItemSearchResults itemResponse;
        try {
            itemResponse = catalogApi.searchCatalogItems(MARKETPLACES_IDS, asins, "ASIN",
                    INCLUDE_DATA, null, null, null, null, null, null, null, null);
        } catch (ApiException e) {
            var message = "Error fetching in [CatalogApi] items from SPAPI";
            batch.setFailed(productsRequest.size());
            batch.setSuccess(0);
            batch.setFailedMessage(message);
            batchSummary.add(batch);
            throw new SPAPIException(message, e);
        }
        return itemResponse;
    }

    private GetPricingResponse getGetPricingResponse(List<ProductEntity> productsRequest, List<BatchSummary> batchSummary, BatchSummary batch, List<String> asins) {
        GetPricingResponse getPricingResponse;
        try {
            getPricingResponse = productPricingApi.getCompetitivePricing(MARKETPLACE_ID_US, "Asin",
                    asins, null, null);
        } catch (ApiException e) {
            var message = "Error fetching in [ProductPricingApi] items from SPAPI";
            batch.setFailed(productsRequest.size());
            batch.setSuccess(0);
            batch.setFailedMessage(message);
            batchSummary.add(batch);
            throw new SPAPIException(message, e);
        }
        return getPricingResponse;
    }

    //// @formatter:off
    private ProductEntity productItemToProductEntity(final ProductEntity request, final Item item, final BigDecimal bbPrice, final BigDecimal feesAmount, final Integer sellerCount) {

        final BigDecimal buyCost = request.getBuyCost();
        // Calculations to get the ROI and profit
        BigDecimal netProfit = bbPrice.subtract(feesAmount).subtract(buyCost);
        BigDecimal roi = netProfit.divide(buyCost, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));

        final var productCondition = "new";
        final var amazonLink = String.format("https://www.amazon.com/dp/%s", item.getAsin());
        BigDecimal bsr = BigDecimal.ZERO;
        try {
            bsr = new BigDecimal(item.getSalesRanks().get(0).getDisplayGroupRanks().get(0).getRank());
        } catch (Exception e) {
            log.error("Error getting BSR for item {}", item.getAsin());
        }
        String amazonImage = "";
        try {
            amazonImage = item.getImages().get(0).getImages().get(0).getLink();
        } catch (Exception e) {
            log.error("Error getting image for item {}", item.getAsin());
        }
        Random rand = new Random();
        String supplier = SUPPLIERS.get(rand.nextInt(SUPPLIERS.size()));
        return ProductEntity.builder()
                .asin(item.getAsin())
                .amazonLink(amazonLink)
                .buyCost(buyCost)
                .netProfit(netProfit)
                .category("NA")
                .currentBSR(bsr)
                .currentBBPrice(bbPrice)
                .title(item.getSummaries().get(0).getItemName())
                .roi(roi)
                .fbaSellerCount(sellerCount)
                .additionalCost(BigDecimal.ZERO)
                .tagsId(new ArrayList<>())
                .date(new Date())
                .supplierLink(request.getSupplierLink())
                .supplier(request.getSupplier())
                .image(amazonImage)
                .build();
    }
    // @formatter:on
}
