package com.myasinmanager.service;

import java.util.UUID;

import com.amazon.SellingPartnerAPIAA.AWSAuthenticationCredentials;
import com.amazon.SellingPartnerAPIAA.AWSAuthenticationCredentialsProvider;
import com.amazon.SellingPartnerAPIAA.LWAAuthorizationCredentials;
import com.myasinmanager.spapi.api.ProductPricingApi;
import com.myasinmanager.spapi.api.SellersApi;
import com.myasinmanager.spapi.client.ApiException;
import com.myasinmanager.spapi.model.product.pricing.GetPricingResponse;
import com.myasinmanager.spapi.model.seller.GetMarketplaceParticipationsResponse;

public class TestCredentials {

	public static void main(String[] args) {
		// Configure your AWS credentials :
		AWSAuthenticationCredentials awsAuthenticationCredentials = AWSAuthenticationCredentials.builder()
				.accessKeyId("AKIA34HUBZZLKJC6O3P6").secretKey("PLk11XxaCGfMVe6i60/GPlK9ygIEknJIKuGX43d2")
				.region("us-east-1").build();

		// Configure your AWS credentials provider :
		AWSAuthenticationCredentialsProvider awsAuthenticationCredentialsProvider = AWSAuthenticationCredentialsProvider
				.builder().roleArn("arn:aws:iam::816555609686:role/RoleSPSeller")
				.roleSessionName(UUID.randomUUID().toString()).build();

		// Configure your LWA credentials :
		LWAAuthorizationCredentials lwaAuthorizationCredentials = LWAAuthorizationCredentials.builder()
				.clientId("amzn1.application-oa2-client.da2373229a794a19add68c7fe3df976a")
				.clientSecret("131b713bcde43135f123be70d7d7b35575415b31d4bdd150307b60048564b6d9")
				.refreshToken(
						"Atzr|IwEBIAOJrQFpt8UPh900iewhXWct1Uf9QbLoIC5fNDe4iMkboP2L-b_VUSYFxZFfchyk4r997BUePMmA3AwQ-3tKyQHpPO5vm4iw9w8eiGuJABDjBAGXOAR4hgV2UwG97Y31cNH2d_4HER0iX9GGgqIRrwxurO5XGbM07nzvqHv6odzIsTgLGNC9zvjot20NY39M4hsYT94q4Wx4wbEEsn-D40bzH-8ZsxKqYi9XXy7bsTKcIk1C95smDWEDGKbrBmFPCQ1RW5AUHGzLLWFwfrhwevmpmr9j0F2JpytMhECv4iZAbIzNXZ2QPcPQ1hhwW1z0ms8")
				.endpoint("https://api.amazon.com/auth/o2/token").build();
		// Create an instance of the Sellers API and call an operation :
		SellersApi sellersApi = new SellersApi.Builder().awsAuthenticationCredentials(awsAuthenticationCredentials)
				.lwaAuthorizationCredentials(lwaAuthorizationCredentials)
				.awsAuthenticationCredentialsProvider(awsAuthenticationCredentialsProvider)
				.endpoint("https://sellingpartnerapi-na.amazon.com") // use Sandbox URL here if you would like to test
																		// your applications without affecting
																		// production data.
				.build();

		ProductPricingApi productPricingApi = new ProductPricingApi.Builder()
				.awsAuthenticationCredentials(awsAuthenticationCredentials)
				.lwaAuthorizationCredentials(lwaAuthorizationCredentials)
				.awsAuthenticationCredentialsProvider(awsAuthenticationCredentialsProvider)
				.endpoint("https://sellingpartnerapi-na.amazon.com") // use Sandbox URL here if you would like to test
																		// your applications without affecting
																		// production data.
				.build();
		try {
//			GetMarketplaceParticipationsResponse result = sellersApi.getMarketplaceParticipations();
			GetPricingResponse responsePricing = productPricingApi.getCompetitivePricing(null, null, null, null, null);
//			System.out.println(result);
		} catch (ApiException e) {
			System.err.println("Exception when calling GetMarketplaceParticipationsResponse#getCompetitivePricing");
			e.printStackTrace();
		}

	}
}
