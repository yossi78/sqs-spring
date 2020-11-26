package com.example.sqsspring.aws;


import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;




@Service
public class AWSAssumeRole {


    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${aws-assume-role.credsDurationTime}")
    private int credsDurationTime;

    @Value("${aws-assume-role.roleArn}")
    private String roleARN;

    @Value("${aws-assume-role.roleSessionName}")
    private String roleSessionName;

    private static final Logger logger = LoggerFactory.getLogger(AWSAssumeRole.class);



    public BasicSessionCredentials getBasicSessionCredentials(){
        AWSSecurityTokenService stsClient = createStsClient();
        AssumeRoleResult assumeRoleResult = assumeRole(stsClient);
        BasicSessionCredentials temporaryCredentials = generateTempCredentials(assumeRoleResult);
        return temporaryCredentials;
    }


    private AWSSecurityTokenService createStsClient() {
        AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(region)
                .build();
        return stsClient;
    }


    private AssumeRoleResult assumeRole(AWSSecurityTokenService stsClient) {
        AssumeRoleRequest roleRequest = createAssumeRoleRequest();
        AssumeRoleResult assumeRoleResult = stsClient.assumeRole(roleRequest);
        logger.info(assumeRoleResult != null ? "Assume role success" : "Assume role failure");
        return assumeRoleResult;
    }

    private AssumeRoleRequest createAssumeRoleRequest() {
        AssumeRoleRequest roleRequest = new AssumeRoleRequest()
                .withRoleArn(roleARN)
                .withDurationSeconds(credsDurationTime) // Create those credentials by the durationTime property from the config service
                .withRoleSessionName(roleSessionName);
        return roleRequest;
    }

    private BasicSessionCredentials generateTempCredentials(AssumeRoleResult assumeRoleResult) {
        return new BasicSessionCredentials(
                assumeRoleResult.getCredentials().getAccessKeyId(),
                assumeRoleResult.getCredentials().getSecretAccessKey(),
                assumeRoleResult.getCredentials().getSessionToken());
    }




}
