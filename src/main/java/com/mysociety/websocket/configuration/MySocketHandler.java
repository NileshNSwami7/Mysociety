package com.mysociety.websocket.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MySocketHandler extends TextWebSocketHandler {

	 @Override
	    public void afterConnectionEstablished(WebSocketSession session) {
	        System.out.println("WebSocket Connected: " + session.getId());
	    }

	    @Override
	    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	        System.out.println("Received: " + message.getPayload());
	        session.sendMessage(new TextMessage("Server Reply: " + message.getPayload()));
	    }
}
