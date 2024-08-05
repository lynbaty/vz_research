package org.example.es_module.controllers;

import org.example.es_module.kafka.KafkaConstants;
import org.example.es_module.kafka.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/notification")
@CrossOrigin
public class FirebaseMessageController {
    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

//    @Autowired
//    private FirebaseMessageService firebaseMessageService;

//    @PostMapping("/push")
//    public Response<String> sendNotification(@Valid @RequestBody PushNotificationRequest pnsRequest) {
//        return firebaseMessageService.pushNotificationForTest(pnsRequest);
//    }

    @PostMapping("/push")
    public void sendMessage(@RequestBody Message message) {
        try {
            //Sending the message to kafka topic queue
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
