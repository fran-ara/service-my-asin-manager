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

    private final String REFRESH_TOKEN = "Atzr|IwEBINgj7eBywqiZqwr7UZK8ugyURWCBL45_G5JGRnDzzEtuq8GjEoGYRMMBBkI4PtGVO5R4_Q7GPmv3rDRSGCU0sE231FfVgJ_isolFzchkRf1wIEwHm95Xb3SH5oPLzMRWuEVDF5r5rxeQ7d5UeAIuTC78HckzDXvauTQjHOLLSPEAby28Tj3QOwPIPPPYLy6Fw497wxg-uhH4AAHb3ktBlcGMjrpVnIbP9Q50sFxCnyizo73p8CeCoC824xACC5Ez0RZgJFDxUVI0LBJYVI72YLTGfBgazYGASrvmuWqeKaUSOLjLW9OuoX4EP-2q-gxLzwY";
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
                .accessKeyId("AKIATDXR5OPIN4XP7O7W")
                .secretKey("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")
                .region("us-east-1").build();
    }

    @Bean
    public LWAAuthorizationCredentials lwaAuthorizationCredentials() {
        log.debug("SPAPI credentials cliendId [{}] secret [{}] endpoint [{}]", clientId, clientSecret, END_POINT_AUTH);
        return LWAAuthorizationCredentials.builder()
                .clientId("amzn1.application-oa2-client.9c7d7d3a2c204a4d9f9129585160c7f6")
                .clientSecret("f36b5b3d2bc1dbe423387c8e10022a7be65df2ccf33f5e9b8aff7e8cf1d3e5e1")
                .refreshToken(
                        "Atzr|IwEBIE1f9PaJ_TZLVkaeV9TpyyQImemb27Tn_Rgs_WLnwh-51C-NeuX-hqIpuw1pO21L9t-HtEXiILIw9911M8RflkUuhrCEvbhKAzabRWRMbyHlqspfOt4Kw5vpkSggOTd7PB6C4X7WkWkUVHwH1TBIcLTbitwFY2mxRUfW4mtOGFGe9C16AR1ErP4r_j3HISPXIyJkGR0RMNwZA4b4jAyuwdNGjXTDSGx17lykbEZSbo2M_JGdAomQVURXmYyOJbcM3nsFO3AKuQdi6f3Yh4nYYIP53fp72M5hkaYN4DjjwmPLEsZBB78y8j7EOGwblsORDic")
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