package com.example.sqsspring.config;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class AWSCredentialsConfig {


    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretKey;

    private BasicSessionCredentials basicSessionCredentials;

    @Autowired
    public AWSCredentialsConfig(@Qualifier("assumeRole") BasicSessionCredentials basicSessionCredentials) {
        this.basicSessionCredentials = basicSessionCredentials;
    }


    @Bean("assumeRoleCredentails")
    @Primary
    public AWSStaticCredentialsProvider getAssumeRoleCredentails(){
        return new AWSStaticCredentialsProvider(basicSessionCredentials);
    }


    @Bean("accessCredentails")
    @Primary
    public AWSStaticCredentialsProvider getAccessCredentails(){
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey,awsSecretKey));
    }


}
