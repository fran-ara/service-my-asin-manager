package com.myasinmanager.service;

import java.util.List;

import com.amazon.SellingPartnerAPIAA.AWSAuthenticationCredentials;
import com.amazon.SellingPartnerAPIAA.LWAAuthorizationCredentials;
import com.myasinmanager.spapi.api.CatalogApi;
import com.myasinmanager.spapi.api.ProductPricingApi;
import com.myasinmanager.spapi.api.SellersApi;
import com.myasinmanager.spapi.client.ApiException;
import com.myasinmanager.spapi.model.catalog.ItemSearchResults;
import com.myasinmanager.spapi.model.product.pricing.GetPricingResponse;
import com.myasinmanager.spapi.model.product.pricing.Price;

public class TestCredentials {

	public static void main(String[] args) {
		// Configure your AWS credentials :

		final String MARKETPLACE_ID_US = "ATVPDKIKX0DER";
		final String MARKETPLACE_ID_MX = "A1AM78C64UM0Y8";
		final String MARKETPLACE_ID_CA = "A2EUQ1WTGCTBG2";

		final List<String> MARKETPLACES_IDS = List.of(MARKETPLACE_ID_US);

		AWSAuthenticationCredentials awsAuthenticationCredentials = AWSAuthenticationCredentials.builder()
				.accessKeyId("AKIA34HUBZZLKJC6O3P6").secretKey("PLk11XxaCGfMVe6i60/GPlK9ygIEknJIKuGX43d2")
				.region("us-east-1").build();

		// Configure your AWS credentials provider :
//		AWSAuthenticationCredentialsProvider awsAuthenticationCredentialsProvider = AWSAuthenticationCredentialsProvider
//				.builder().roleArn("arn:aws:iam::816555609686:role/RoleSPSeller")
//				.roleSessionName(UUID.randomUUID().toString()).build();

		// Configure your LWA credentials :
		LWAAuthorizationCredentials lwaAuthorizationCredentials = LWAAuthorizationCredentials.builder()
				.clientId("amzn1.application-oa2-client.9c7d7d3a2c204a4d9f9129585160c7f6")
				.clientSecret("11e90520bbd03ef438e688adb5d3e502724f6601f07c8cfb25ab8a06bfdc0e00")
				.refreshToken(
						"Atzr|IwEBIEWycS6xnoZje4BouH_gRbYwnKicW0p2dZc-Hg1Y7qB5fKyOQHTPnAn0_BByUDH1eiCNvydVi2m3vHxgS4Vt_AhoBSgAmWHVnRS0v2vYgwmuzBNkQXUZzUs2zmEnNOftCec9hSNDwzPDfXvKdQJlVSKSx0B_S8wzSk4l-uMZW1oRvXHUheJfl8aBmt4l8qb6Ld9qD9vhmGaZ7g_3nNJvddyPPTaWvYUobqF1hze6v94iTUe1KBBmdIlmX5myK6I9nA4iqbaV9KLQD6DtvdtIK1vuaE5-h0PLtu3BB8OnNVdnexOpJUH1QeYXMVkweMZCwPQ")
				.endpoint("https://api.amazon.com/auth/o2/token").build();
		// Create an instance of the Sellers API and call an operation :
		SellersApi sellersApi = new SellersApi.Builder().awsAuthenticationCredentials(awsAuthenticationCredentials)
				.lwaAuthorizationCredentials(lwaAuthorizationCredentials)
//				.awsAuthenticationCredentialsProvider(awsAuthenticationCredentialsProvider)
				.endpoint("https://sellingpartnerapi-na.amazon.com") // use Sandbox URL here if you would like to test
				.build();

		CatalogApi catalogApi = new CatalogApi.Builder().awsAuthenticationCredentials(awsAuthenticationCredentials)
				.lwaAuthorizationCredentials(lwaAuthorizationCredentials)
				.endpoint("https://sellingpartnerapi-na.amazon.com").build();

		// Get competitive pricing
		ProductPricingApi pricingAPI = new ProductPricingApi.Builder()
				.awsAuthenticationCredentials(awsAuthenticationCredentials)
				.lwaAuthorizationCredentials(lwaAuthorizationCredentials)
				.endpoint("https://sellingpartnerapi-na.amazon.com").build();


		try {
//			List<String> INCLUDE_DATA = List.of("attributes", "identifiers", "images", "productTypes", "salesRanks",
//					"summaries");
//
//			ItemSearchResults itemResponse = catalogApi.searchCatalogItems(MARKETPLACES_IDS, List.of("B09B93ZDG4"),
//					"ASIN", INCLUDE_DATA, null, null, null, null, null, null, null, null);
			
			// Get competitive pricing
			GetPricingResponse getPricingResponse = pricingAPI.getCompetitivePricing(MARKETPLACE_ID_US, "Asin",
					List.of("B09QFV3LK3"), null, null);
			List<Price> prices = getPricingResponse.getPayload();

			System.out.println(prices);
		} catch (ApiException e) {
			System.err.println("Exception when calling GetMarketplaceParticipationsResponse#getCompetitivePricing");
			e.printStackTrace();
		}

	}
}
