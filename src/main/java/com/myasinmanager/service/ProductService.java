package com.myasinmanager.service;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
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
import com.myasinmanager.spapi.model.product.pricing.GetOffersResponse;
import com.myasinmanager.spapi.model.product.pricing.GetPricingResponse;
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
        Page<ProductEntity> productsPaginated = productRepository.findAll(pageable);
        if (Objects.isNull(username) && (Objects.isNull(tags) || tags.length == 0)) {
            return productsPaginated;
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
        // Filter products by username
        List<ProductEntity> productsByUsername = productsPaginated.getContent().stream()
                .filter(product -> (Objects.nonNull(product.getUserId())) && product.getUserId().equals(user.getId()))
                .collect(Collectors.toList());


        if (Objects.nonNull(tags) && tags.length > 0) {
            List<Integer> tagsIds = Stream.of(tags).mapToInt(i -> i).boxed().collect(Collectors.toList());
            List<ProductEntity> productsFilteredByTags = productsByUsername.stream()
                    .filter(p -> p.getTagsId().containsAll(tagsIds)).collect(Collectors.toList());
            log.debug("Response  findAll:{}", productsFilteredByTags);
            return new PageImpl<ProductEntity>(productsFilteredByTags, pageable, productsFilteredByTags.size());
        }
        log.debug("Response  findAll:{}", productsPaginated.getContent());
        return new PageImpl<ProductEntity>(productsByUsername, pageable, productsByUsername.size());
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


    public void createBatch(List<ProductEntity> productsRequest, String username) throws ApiException {
        // Asin from csv productsRequest imported
        List<String> asins = productsRequest.stream().map(p -> p.getAsin()).collect(Collectors.toList());

        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));

        // Fetch items in SPAPI Catalog items
        final ItemSearchResults itemResponse = catalogApi.searchCatalogItems(MARKETPLACES_IDS, asins, "ASIN",
                INCLUDE_DATA, null, null, null, null, null, null, null, null);

        // Get competitive pricing to get the bb price
        GetPricingResponse getPricingResponse = productPricingApi.getCompetitivePricing(MARKETPLACE_ID_US, "Asin",
                asins, null, null);
        List<Price> prices = getPricingResponse.getPayload();

        // Process items
        itemResponse.getItems().stream().forEach(item -> {
            String asin = item.getAsin();
            // Price item
            Price priceItem = prices.stream().filter(p -> p.getASIN().equals(asin)).findFirst().get();
            // Get the lowest price
            Optional<CompetitivePriceType> pricing = priceItem.getProduct().getCompetitivePricing().getCompetitivePrices().stream().filter(cp -> cp.getCompetitivePriceId().equals("1")).findFirst();

            BigDecimal bbPrice = BigDecimal.ZERO;
            if (pricing.isPresent()) {
                bbPrice = pricing.get().getPrice().getListingPrice().getAmount();
            }
            GetOffersResponse getOfferResponse = null;
            BigDecimal feesAmount = BigDecimal.ZERO;
            try {
//                getOfferResponse = productPricingApi.getItemOffers(MARKETPLACE_ID_US, "Collectible", asin,
//                        null);
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

                log.error("Error in SP API call", e);
            }
            ProductEntity request = productsRequest.stream().filter(p -> p.getAsin().equals(item.getAsin())).findFirst().get();
            ProductEntity productRequest = productItemToProductEntity(request, item, bbPrice, feesAmount);
            productRequest.setUserId(user.getId());

            Optional<ProductEntity> productDBOpt = productRepository.findByAsinAndUserId(productRequest.getAsin(), user.getId());

            if (productDBOpt.isPresent()) {
                ProductEntity productDB = productDBOpt.get();

                // Update product only if supplier link, supplier or buy cost are different
                if (!request.getSupplier().equals(productDB.getSupplier()) && !request.getSupplierLink().equals(productDB.getSupplierLink())) {
                    log.info("Supplier changed from {} to {}", request.getSupplier(), productRequest.getSupplier());
                    this.create(productRequest);
                } else {
                    log.info("Supplier not changed");
                }
            } else {
                this.create(productRequest);
            }
        });
    }

    //// @formatter:off
    private ProductEntity productItemToProductEntity(final ProductEntity request, final Item item, final BigDecimal bbPrice, final BigDecimal feesAmount) {

        final BigDecimal buyCost = request.getBuyCost();
        // Calculations to get the ROI and profit
        BigDecimal netProfit = bbPrice.subtract(feesAmount).subtract(buyCost);
        BigDecimal roi = netProfit.divide(buyCost, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));

        final var productCondition = "new";
//		BigDecimal buyPrice = getOfferResponse.getPayload().getSummary().getLowestPrices().stream().filter(lp->lp.getCondition().equals(productCondition)).findFirst().get().getLandedPrice().getAmount();

        Random rand = new Random();
        String supplier = SUPPLIERS.get(rand.nextInt(SUPPLIERS.size()));
        return ProductEntity.builder()
                .asin(item.getAsin())
                .amazonLink(item.getImages().get(0).getImages().get(0).getLink())
                .buyCost(buyCost)
                .netProfit(netProfit)
                .category(Objects.nonNull(item.getSalesRanks().isEmpty()) ? "NA" : item.getSalesRanks().get(0).getClassificationRanks().get(0).getTitle())
                .currentBSR(new BigDecimal(item.getSalesRanks().get(0).getClassificationRanks().get(0).getRank()))
                .currentBBPrice(bbPrice)
                .title(item.getSummaries().get(0).getItemName())
                .roi(roi)
                .fbaSellerCount(item.getSalesRanks().get(0).getClassificationRanks().get(0).getRank())
                .additionalCost(BigDecimal.ZERO)
                .tagsId(new ArrayList<>())
                .date(new Date())
                .supplierLink(request.getSupplierLink())
                .supplier(request.getSupplier())
                .image(item.getImages().get(0).getImages().get(0).getLink())
                .build();
    }
    // @formatter:on

}
