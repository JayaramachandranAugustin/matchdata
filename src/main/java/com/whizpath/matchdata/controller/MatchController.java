package com.whizpath.matchdata.controller;

import com.whizpath.matchdata.model.Match;
import com.whizpath.matchdata.publisher.KafkaPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MatchController {
    private final KafkaPublisher kafkaPublisher;

    @PostMapping("/match/data")
    public ResponseEntity<String> createOrder(@RequestBody Match match){
        try{
            kafkaPublisher.produceKafkaMessage("game",match);
            return new ResponseEntity<String>("Message Successfully sent to kafka topic", HttpStatus.ACCEPTED);
        }catch (Exception exception){
            log.error("Error sending message to kafka topic, exception = {}",exception.getMessage());
            return new ResponseEntity<String>("Error sending message to kafka topic", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
