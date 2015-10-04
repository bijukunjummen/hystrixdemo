package org.bk.consumer.controller;

import org.bk.consumer.domain.Message;
import org.bk.consumer.domain.MessageAcknowledgement;
import org.bk.consumer.feign.PongClient;
import org.bk.consumer.service.HelloCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @Autowired
    @Qualifier("hystrixPongClient")
    private PongClient pongClient;

    @RequestMapping("/dispatch")
    public MessageAcknowledgement sendMessage(@RequestBody Message message) {
        HelloCommand helloCommand = new HelloCommand("World");
        return new MessageAcknowledgement("test", "test", helloCommand.execute());
//        return this.pongClient.sendMessage(message);
    }
}
