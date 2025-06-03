package com.mysociety.controller.webscoket.messagecontroller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
	
	
	@MessageMapping("/hello")
	@SendTo("/topic/")
	public String grerting(String msg) {
		return "Hello : please check :" +msg ;
	}
}
