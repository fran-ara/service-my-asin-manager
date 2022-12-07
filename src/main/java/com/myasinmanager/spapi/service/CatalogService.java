package com.myasinmanager.spapi.service;

import com.myasinmanager.model.ProductEntity;
import com.myasinmanager.service.ProductService;
import com.myasinmanager.spapi.api.CatalogApi;
import com.myasinmanager.spapi.api.ProductPricingApi;
import com.myasinmanager.spapi.client.ApiException;
import com.myasinmanager.spapi.model.catalog.Item;
import com.myasinmanager.spapi.model.catalog.ItemSearchResults;
import com.myasinmanager.spapi.model.product.pricing.GetOffersResponse;
import com.myasinmanager.spapi.model.product.pricing.GetPricingResponse;
import com.myasinmanager.spapi.model.product.pricing.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class CatalogService {

	private static final String MARKETPLACE_ID_US = "ATVPDKIKX0DER";

	private static final List<String> MARKETPLACES_IDS = List.of(MARKETPLACE_ID_US);

	private static final List<String> INCLUDE_DATA = List.of("attributes", "identifiers", "images", "productTypes",
			"salesRanks", "summaries");

	private static final List<String> SUPPLIERS = List.of("Walmart", "Chedraui", "Etsy", "Ebay");

	@Autowired
	private CatalogApi catalogApi;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductPricingApi productPricingApi;

	public Item getProductsByAsin(String asin) throws ApiException {
		Item itemResponse = catalogApi.getCatalogItem(asin, MARKETPLACES_IDS, INCLUDE_DATA, null);
		log.debug("Item retrieved from SPAPI with asin {}: {}", asin, itemResponse);
		return itemResponse;
	}

	public void processItemsByAsin(List<String> asins) throws ApiException {
		// Fetch items in SPAPI Catalog items
		final ItemSearchResults itemResponse = catalogApi.searchCatalogItems(MARKETPLACES_IDS, asins, "ASIN",
				INCLUDE_DATA, null, null, null, null, null, null, null, null);

		// Get competitive pricing to get the bb price
		GetPricingResponse getPricingResponse = productPricingApi.getCompetitivePricing(MARKETPLACE_ID_US, "Asin",
				asins, null, null);
		List<Price> prices = getPricingResponse.getPayload();

		// Store items in database
		itemResponse.getItems().stream().forEach(item -> {
			Price priceItem = prices.stream().filter(p -> p.getASIN().equals(item.getAsin())).findFirst().get();

			// Get buy price
			// Replace with batch call for all products
			GetOffersResponse getOfferResponse = null;
			try {
				getOfferResponse = productPricingApi.getItemOffers(MARKETPLACE_ID_US, "Collectible", item.getAsin(),
						null);
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				log.error("Error calling getItemOffers");
			}

			ProductEntity productEntity = productItemToProductEntity(item, priceItem, getOfferResponse);

			this.productService.create(productEntity);

		});

		log.debug("Items stored in database");
	}

	//// @formatter:off
 
	private ProductEntity productItemToProductEntity(final Item item, final Price priceItem, final GetOffersResponse getOfferResponse) {
		BigDecimal bbPrice = priceItem.getProduct().getCompetitivePricing().getCompetitivePrices().stream().filter(cp-> cp.getCompetitivePriceId().equals("1")).findFirst().get().getPrice().getListingPrice().getAmount();
		
		final var productCondition = "new";
//		BigDecimal buyPrice = getOfferResponse.getPayload().getSummary().getLowestPrices().stream().filter(lp->lp.getCondition().equals(productCondition)).findFirst().get().getLandedPrice().getAmount();

		Random rand = new Random();
		    String supplier = SUPPLIERS.get(rand.nextInt(SUPPLIERS.size()));
		return ProductEntity.builder()
				.asin(item.getAsin())
				.amazonLink(item.getImages().get(0).getImages().get(0).getLink())
				.buyCost(bbPrice)
				.netProfit(new BigDecimal(item.getAttributes().getListPrice().get(0).getValue()))
				.category(item.getSalesRanks().get(0).getClassificationRanks().get(0).getTitle())
				.currentBSR(new BigDecimal(item.getSalesRanks().get(0).getClassificationRanks().get(0).getRank()))
				.currentBBPrice(bbPrice)
				.title(item.getSummaries().get(0).getItemName())
				.roi(new BigDecimal(item.getAttributes().getListPrice().get(0).getValue()))
				.fbaSellerCount(item.getSalesRanks().get(0).getClassificationRanks().get(0).getRank())
				.additionalCost(BigDecimal.ZERO)
				.tagsId(new ArrayList<>())
				.date(new Date())
				.supplierLink(item.getImages().get(0).getImages().get(0).getLink())
				.supplier(supplier)
				.image(item.getImages().get(0).getImages().get(0).getLink())
				.build();
	}
	// @formatter:on

}
