package com.example.sqsspring.sqs;


import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.example.sqsspring.aws.AWSCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;




@Service
@EnableSqs
public class SQSTemplateProvider {


    @Value("${cloud.aws.region.static}")
    private String region;


    private AWSCredentials awsCredentials;
    private QueueMessagingTemplate queueMessagingTemplate;


    @Autowired
    public SQSTemplateProvider(AWSCredentials awsCredentials) {
        this.awsCredentials = awsCredentials;
    }

    @PostConstruct
    public void init(){
        generateQueuqMessagingTemplate();
    }



    public QueueMessagingTemplate getQueuqMessagingTemplate(String destination){
        queueMessagingTemplate.setDefaultDestinationName(destination);
        return queueMessagingTemplate;
    }


    public void generateQueuqMessagingTemplate(){
        queueMessagingTemplate= new QueueMessagingTemplate(amazonSQSAsync());
    }




    public AmazonSQSAsync amazonSQSAsync(){
        return AmazonSQSAsyncClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(awsCredentials.generateAssumeRoleCredentails())
//                .withCredentials(awsCredentials.generateAccessCredentails())
                .build();
    }



}
