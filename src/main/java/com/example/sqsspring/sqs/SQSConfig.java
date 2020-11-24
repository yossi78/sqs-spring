package com.example.sqsspring.sqs;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;




@Configuration
@EnableSqs
public class SQSConfig {


    @Value("${cloud.aws.region.static}")
    private String region;


    private AWSStaticCredentialsProvider awsStaticCredentialsProvider;


    @Autowired
    public SQSConfig(@Qualifier("accessCredentails") AWSStaticCredentialsProvider awsStaticCredentialsProvider) {
        this.awsStaticCredentialsProvider = awsStaticCredentialsProvider;
    }

    @Bean
    public QueueMessagingTemplate queuqMessagingTemplate(){
        return  new QueueMessagingTemplate(amazonSQSAsync());
    }


    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync(){
        return AmazonSQSAsyncClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(awsStaticCredentialsProvider)
                .build();
    }



}
