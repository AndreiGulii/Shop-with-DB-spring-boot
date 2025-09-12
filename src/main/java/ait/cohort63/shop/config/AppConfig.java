package ait.cohort63.shop.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public AmazonS3 doClient(DoProperties doProperties) {

        //Obiekt soderjashii kliuchi dostupa
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                doProperties.getAccessKey(),
                doProperties.getSecretKey()
        );

        // Objekt s informatsiiei o podkliuchenii

        AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(
                        doProperties.getUrl(),
                        doProperties.getRegion()
                );

        // Sborka clienta dlea podkliuchenija
        AmazonS3ClientBuilder clientBuilder = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withEndpointConfiguration(endpointConfiguration);

        return clientBuilder.build();
    }

}
