package com.example.sqsspring.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "/v1/aws")
public class AWSController {


    private static final Logger logger = LoggerFactory.getLogger(AWSController.class);


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
