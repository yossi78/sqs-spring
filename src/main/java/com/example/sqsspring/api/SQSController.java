package com.example.sqsspring.api;

import com.example.sqsspring.dto.User;
import com.example.sqsspring.sqs.SQSService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;





@RestController
@RequestMapping(value = "/v1/sqs")
public class SQSController {


    private static final Logger logger = LoggerFactory.getLogger(SQSController.class);
    private SQSService sqsService;


    @Autowired
    public SQSController(SQSService sqsService) {
        this.sqsService = sqsService;
    }



    @PostMapping
    public ResponseEntity addMessage(@RequestBody User user) {
        try {
            sqsService.addUser(user);
            logger.info("User has been added successfully");
            return new ResponseEntity(user,HttpStatus.CREATED);
        }catch (Exception e){
            logger.error("Failed to add new User",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping(value = "/payload")
    public ResponseEntity pollPayload() {
        try {
            JSONObject jsonObject =  sqsService.pollPayLoad();
            if(jsonObject==null){
                return new ResponseEntity("", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(jsonObject, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Failed to poll payload",e);
            return new ResponseEntity<>(e.getCause(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/message")
    public ResponseEntity pollMessage() {
        try {
            String messageFromPayload =  sqsService.pollMessageFromPayload();
            if(messageFromPayload==null){
                return new ResponseEntity("", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(messageFromPayload, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Failed to poll message",e);
            return new ResponseEntity<>(e.getCause(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @GetMapping(value = "/health")
    public ResponseEntity healthCheck() {
        try {
            return new ResponseEntity("SQS Service Health is OK", HttpStatus.OK);
        }catch (Exception e){
            logger.error("Failed run health check",e);
            return new ResponseEntity<>(e.getCause(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
