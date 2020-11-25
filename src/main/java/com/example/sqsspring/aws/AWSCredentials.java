package com.example.sqsspring.aws;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


@Service
public class AWSCredentials {


    @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretKey;


    private AWSAssumeRole awsAssumeRole;


    @Autowired
    public AWSCredentials(AWSAssumeRole awsAssumeRole) {
        this.awsAssumeRole = awsAssumeRole;
    }

    public AWSStaticCredentialsProvider generateAccessCredentails(){
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey,awsSecretKey));
    }

    public AWSStaticCredentialsProvider generateAssumeRoleCredentails(){
        return new AWSStaticCredentialsProvider(awsAssumeRole.getBasicSessionCredentials());
    }


}
