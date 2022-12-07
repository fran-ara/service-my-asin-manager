package com.myasinmanager.spapi.config;

import com.amazon.SellingPartnerAPIAA.AWSAuthenticationCredentials;
import com.amazon.SellingPartnerAPIAA.LWAAuthorizationCredentials;
import com.myasinmanager.spapi.api.CatalogApi;
import com.myasinmanager.spapi.api.FeesApi;
import com.myasinmanager.spapi.api.ProductPricingApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SPAPIConfiguration {

    private final String REFRESH_TOKEN = "Atzr|IwEBIEWycS6xnoZje4BouH_gRbYwnKicW0p2dZc-Hg1Y7qB5fKyOQHTPnAn0_BByUDH1eiCNvydVi2m3vHxgS4Vt_AhoBSgAmWHVnRS0v2vYgwmuzBNkQXUZzUs2zmEnNOftCec9hSNDwzPDfXvKdQJlVSKSx0B_S8wzSk4l-uMZW1oRvXHUheJfl8aBmt4l8qb6Ld9qD9vhmGaZ7g_3nNJvddyPPTaWvYUobqF1hze6v94iTUe1KBBmdIlmX5myK6I9nA4iqbaV9KLQD6DtvdtIK1vuaE5-h0PLtu3BB8OnNVdnexOpJUH1QeYXMVkweMZCwPQ";
    private final String END_POINT_AUTH = "https://api.amazon.com/auth/o2/token";
    private final String END_POINT_SELLING_PARTNER = "https://sellingpartnerapi-na.amazon.com";
    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;
    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;
    @Value("${amazon.aws.region}")
    private String amazonRegion;
    @Value("${amazon.aws.spapi.client.id}")
    private String clientId;
    @Value("${amazon.aws.spapi.client.secret}")
    private String clientSecret;

    //// @formatter:off
    @Bean
    public AWSAuthenticationCredentials awsAuthenticationCredentials() {
        log.debug("AWS Credentials acccesKey {} secret {}", amazonAWSAccessKey, amazonAWSSecretKey);
        return AWSAuthenticationCredentials.builder()
                .accessKeyId("AKIA34HUBZZLKJC6O3P6")
                .secretKey("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")
                .region("us-east-1").build();
    }

    @Bean
    public LWAAuthorizationCredentials lwaAuthorizationCredentials() {
        log.debug("SPAPI credentials cliendId [{}] secret [{}] endpoint [{}]", clientId, clientSecret, END_POINT_AUTH);
        return LWAAuthorizationCredentials.builder()
                .clientId("amzn1.application-oa2-client.9c7d7d3a2c204a4d9f9129585160c7f6")
                .clientSecret("11e90520bbd03ef438e688adb5d3e502724f6601f07c8cfb25ab8a06bfdc0e00")
                .refreshToken(
                        "Atzr|IwEBIEWycS6xnoZje4BouH_gRbYwnKicW0p2dZc-Hg1Y7qB5fKyOQHTPnAn0_BByUDH1eiCNvydVi2m3vHxgS4Vt_AhoBSgAmWHVnRS0v2vYgwmuzBNkQXUZzUs2zmEnNOftCec9hSNDwzPDfXvKdQJlVSKSx0B_S8wzSk4l-uMZW1oRvXHUheJfl8aBmt4l8qb6Ld9qD9vhmGaZ7g_3nNJvddyPPTaWvYUobqF1hze6v94iTUe1KBBmdIlmX5myK6I9nA4iqbaV9KLQD6DtvdtIK1vuaE5-h0PLtu3BB8OnNVdnexOpJUH1QeYXMVkweMZCwPQ")
                .endpoint("https://api.amazon.com/auth/o2/token").build();
    }

    @Bean
    public CatalogApi catalogApi(AWSAuthenticationCredentials awsAuthenticationCredentials, LWAAuthorizationCredentials lwaAuthorizationCredentials) {
        log.debug("Endpoint selling partner [{}]", END_POINT_SELLING_PARTNER);
        return new CatalogApi.Builder()
                .awsAuthenticationCredentials(awsAuthenticationCredentials)
                .lwaAuthorizationCredentials(lwaAuthorizationCredentials)
                .endpoint("https://sellingpartnerapi-na.amazon.com")
                .build();
    }

    @Bean
    public ProductPricingApi productPricingApi(AWSAuthenticationCredentials awsAuthenticationCredentials, LWAAuthorizationCredentials lwaAuthorizationCredentials) {
        log.debug("Endpoint selling partner [{}]", END_POINT_SELLING_PARTNER);
        return new ProductPricingApi.Builder()
                .awsAuthenticationCredentials(awsAuthenticationCredentials)
                .lwaAuthorizationCredentials(lwaAuthorizationCredentials)
                .endpoint("https://sellingpartnerapi-na.amazon.com")
                .build();
    }

    @Bean
    public FeesApi feesApi(AWSAuthenticationCredentials awsAuthenticationCredentials, LWAAuthorizationCredentials lwaAuthorizationCredentials) {
        log.debug("Endpoint selling partner [{}]", END_POINT_SELLING_PARTNER);
        return new FeesApi.Builder()
                .awsAuthenticationCredentials(awsAuthenticationCredentials)
                .lwaAuthorizationCredentials(lwaAuthorizationCredentials)
                .endpoint("https://sellingpartnerapi-na.amazon.com")
                .build();
    }
    // @formatter:on
}