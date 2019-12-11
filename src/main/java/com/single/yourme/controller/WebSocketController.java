package com.single.yourme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *
 * </p>
 *
 * @author 1999single
 * @since 2019-12-07
 */
@Controller
public class WebSocketController {

//    @Autowired
//    public SimpMessagingTemplate template;

    @GetMapping("test0")
    public String test(String value) {
        return "chat0";
    }

    @GetMapping("test1")
    public String test1(String value) {
        return "chat1";
    }

//    @MessageMapping("/send")
//    @SendTo("/sub/chat")
//    public String sendMsg(String value) {
//        System.out.println(value);
//        return value;
//    }
//
//    @MessageMapping("/sendUser")
//    public void sendToUser(String value) {
//        template.convertAndSendToUser("123", "/queue/customer", "");
//    }

}
