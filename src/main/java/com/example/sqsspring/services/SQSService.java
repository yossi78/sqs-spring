package com.example.sqsspring.services;

import com.example.sqsspring.dto.Notification;
import com.example.sqsspring.dto.User;
import com.example.sqsspring.sqs.SQSTemplateProvider;
import com.example.sqsspring.utils.MapperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;





@Service
public class SQSService {


    private static final Logger logger = LoggerFactory.getLogger(SQSService.class);

    @Value("${cloud.aws.end-point.uri}")
    private String sqsEndPoint;

    private SQSTemplateProvider sqsTemplateProvider;
    private final String MESSAGE="Message";



    @Autowired
    public SQSService(SQSTemplateProvider sqsTemplateProvider) {
        this.sqsTemplateProvider = sqsTemplateProvider;
    }




    public User addUser(User user) throws JsonProcessingException {
        String userStr=MapperUtil.serializeToString(user);
        Notification notification = new Notification();
        notification.setTopicArn(sqsEndPoint);
        notification.setSubject(user.getName());
        notification.setMessage(userStr);
        notification.setTimestamp(String.valueOf(System.currentTimeMillis()));
        notification.setMessageId(user.getName());
        String notificationStr=MapperUtil.serializeToString(notification);
        sendMessage(notificationStr);
        return user;
    }


    public void sendMessage(String payload){
        logger.info("Sending message to SQS : "+payload);
        QueueMessagingTemplate queueMessagingTemplate=this.sqsTemplateProvider.getQueuqMessagingTemplate(sqsEndPoint);
        queueMessagingTemplate.send(sqsEndPoint,MessageBuilder.withPayload(payload).build());
    }



    public JSONObject pollMessageFromPayload() throws ParseException, JsonProcessingException {
        JSONObject payload = pollPayLoad();
        JSONObject messageFromPayload=null;
        if(payload!=null){
            Object object =  payload.get(MESSAGE);
            messageFromPayload=MapperUtil.toJSONObject(object);
        }
        return messageFromPayload;
    }



    public JSONObject pollPayLoad() throws ParseException, JsonProcessingException {
        JSONObject payload=null;
        GenericMessage genericMessage=poll();
        if(genericMessage!=null){
            payload=MapperUtil.toJSONObject(genericMessage.getPayload());
        }
        return  payload;
    }



    public GenericMessage poll()  {
        QueueMessagingTemplate queueMessagingTemplate=this.sqsTemplateProvider.getQueuqMessagingTemplate(sqsEndPoint);
        GenericMessage genericMessage = (GenericMessage)queueMessagingTemplate.receive();
        return genericMessage;
    }







//
//    @SqsListener("${cloud.aws.end-point.ququeName}")
//    public void pollMessages(String message){
//        logger.info("Recieved Message: " + message);
//    }



}
