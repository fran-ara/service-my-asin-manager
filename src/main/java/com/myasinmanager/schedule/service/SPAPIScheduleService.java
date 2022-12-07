package com.myasinmanager.schedule.service;

import com.myasinmanager.model.ProductEntity;
import com.myasinmanager.service.ProductService;
import com.myasinmanager.service.TagService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SPAPIScheduleService {

    private static final String MARKETPLACE_ID_US = "ATVPDKIKX0DER";

    private static final List<String> MARKETPLACES_IDS = List.of(MARKETPLACE_ID_US);

    private static final List<String> INCLUDE_DATA = List.of("attributes", "identifiers", "images", "productTypes", "salesRanks", "summaries");

    private static final List<String> SUPPLIERS = List.of("Walmart", "Chedraui", "Etsy", "Ebay");

    @Autowired
    private CatalogApi catalogApi;

    @Autowired
    private ProductService productService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ProductPricingApi productPricingApi;

    @Autowired
    private FeesApi feesApi;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    public void updateCatalogFromSPAPI() throws ApiException {
        log.debug("Starting fetching in SPAPI");
        // Get all products from database
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        List<ProductEntity> products = productService.findAll(pageable, null,null).getContent();

        if (products.isEmpty()) {
            log.debug("No elements to be updated");
            return;
        }
        log.debug("Total products to be updated {}", products.size());
        processItemsByAsin(products);
    }

    public void processItemsByAsin(List<ProductEntity> productsDB) throws ApiException {

        List<String> asins = productsDB.stream().map(p -> p.getAsin()).collect(Collectors.toList());

        // Fetch items in SPAPI Catalog items
        final ItemSearchResults itemResponse = catalogApi.searchCatalogItems(MARKETPLACES_IDS, asins, "ASIN", INCLUDE_DATA, null, null, null, null, null, null, null, null);

        // Get competitive pricing to get the bb price
        GetPricingResponse getPricingResponse = productPricingApi.getCompetitivePricing(MARKETPLACE_ID_US, "Asin", asins, null, null);
        List<Price> prices = getPricingResponse.getPayload();

        // Store items in database
        itemResponse.getItems().stream().forEach(item -> {
            String asin = item.getAsin();
            log.debug("Updating product with asin {}", asin);

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
//                getOfferResponse = productPricingApi.getItemOffers(MARKETPLACE_ID_US, "Collectible", item.getAsin(), null);
                final GetMyFeesEstimateRequest getMyFeesEstimateRequest = new GetMyFeesEstimateRequest().
                        feesEstimateRequest(new FeesEstimateRequest()
                                .marketplaceId(MARKETPLACE_ID_US)
                                .priceToEstimateFees(new PriceToEstimateFees().listingPrice(new MoneyType()
                                        .currencyCode("USD")
                                        .amount(bbPrice))));
                final GetMyFeesEstimateResponse myFeesEstimateForASIN = feesApi.getMyFeesEstimateForASIN(getMyFeesEstimateRequest, asin);
                log.debug("Fees estimate {}", myFeesEstimateForASIN);

                final FeesEstimateResult feesEstimateResult = myFeesEstimateForASIN.getPayload().getFeesEstimateResult();
                log.debug("Fees estimate result {}", feesEstimateResult);

                final FeesEstimate feesEstimate = feesEstimateResult.getFeesEstimate();
                log.debug("Fees {}", feesEstimate);
            } catch (ApiException e) {
                    log.error("Error calling SPAPI ",e);
            }
            ProductEntity productEntity = productsDB.stream().filter(p -> p.getAsin().equals(asin)).findFirst().get();
            ProductEntity productUpdated = productItemToProductEntity(productEntity, item, bbPrice, feesAmount);
            // Update only if currentBBPrice, currentBSR and fbaSellerCount are different
            if (productEntity.getCurrentBBPrice().compareTo(productUpdated.getCurrentBBPrice()) == 0 && productEntity.getCurrentBSR().compareTo(productUpdated.getCurrentBSR()) == 0 && productEntity.getFbaSellerCount().compareTo(productUpdated.getFbaSellerCount()) == 0) {
                log.debug("Product {} has not changed", asin);
            } else {
                log.debug("Product {} has changed", asin);
                productService.create(productUpdated);
            }

        });

        log.debug("Items updated");
    }

    //// @formatter:off

    private ProductEntity productItemToProductEntity(ProductEntity productEntity, final Item item, final BigDecimal bbPrice, final BigDecimal fees) {
        final BigDecimal buyCost = productEntity.getBuyCost();
        // Calculations to get the ROI and profit
        BigDecimal netProfit = bbPrice.subtract(fees).subtract(buyCost);
        BigDecimal roi = netProfit.divide(buyCost, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));

        final var productCondition = "new";
//		BigDecimal buyPrice = getOfferResponse.getPayload().getSummary().getLowestPrices().stream().filter(lp->lp.getCondition().equals(productCondition)).findFirst().get().getLandedPrice().getAmount();
        return ProductEntity.builder()
                .id(productEntity.getId())
                .asin(item.getAsin())
                .amazonLink(item.getImages().get(0).getImages().get(0).getLink())
                .buyCost(buyCost)
                .netProfit(netProfit)
                .roi(roi)
                .category(Objects.nonNull(item.getSalesRanks().isEmpty()) ? "NA" : item.getSalesRanks().get(0).getClassificationRanks().get(0).getTitle())
                .currentBSR(new BigDecimal(item.getSalesRanks().get(0).getClassificationRanks().get(0).getRank()))
                .currentBBPrice(bbPrice)
                .title(item.getSummaries().get(0).getItemName()).roi(BigDecimal.valueOf(item.getAttributes().getListPrice().get(0).getValue()))
                .fbaSellerCount(item.getSalesRanks().get(0).getClassificationRanks().get(0).getRank()).additionalCost(BigDecimal.ZERO)
                .tags(productEntity.getTags()).date(productEntity.getDate()).supplierLink(productEntity.getSupplierLink()).supplier(productEntity.getSupplier())
                .image(item.getImages().get(0).getImages().get(0).getLink())
                .date(new Date())
                .build();
    }
    // @formatter:on

}
