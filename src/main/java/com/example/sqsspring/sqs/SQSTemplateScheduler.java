package com.example.sqsspring.sqs;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;




@Service
@EnableScheduling
@EnableRetry
public class SQSTemplateScheduler {

    private static final Logger logger = LoggerFactory.getLogger(SQSTemplateScheduler.class);
    private static final String MAX_ATTEMPS ="${sqs.template.generator.max.attemps:7}";
    private static final String DELAY_EXPRESSION ="${sqs.template.generator.delay.between.attempts:5000}";
    private SQSTemplateProvider sqsTemplateProvider;


    @Autowired
    public SQSTemplateScheduler(SQSTemplateProvider sqsTemplateProvider) {
        this.sqsTemplateProvider = sqsTemplateProvider;
    }



    @Scheduled(fixedDelayString = "${sqs.template.generator.interval:300000}")
    @Retryable(value = {Exception.class},maxAttemptsExpression = MAX_ATTEMPS,backoff=@Backoff(delayExpression = DELAY_EXPRESSION) )
    public void scheduleRefreshRdsToken()  {
        logger.info("Start generate SQS Template");
        sqsTemplateProvider.generateQueuqMessagingTemplate();
        logger.info("Finish generate SQS Template");
    }


    @Recover
    public void recover(Exception e){
        logger.error("Failed to generate SQS Template",e);
    }


}
