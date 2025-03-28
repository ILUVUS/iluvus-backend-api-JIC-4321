package iluvus.backend.api.util;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import iluvus.backend.api.model.ChatMessage;


//this whole thing is breaking - not sure how to handle websocket here
@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    private final MongoTemplate mongoTemplate;

    public MyWebSocketHandler(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    //not sure whats happening here

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        // Process the message and interact with MongoDB as needed
        // Example: Save message to MongoDB
        ChatMessage chatMessage = new ChatMessage(payload);
        mongoTemplate.save(chatMessage);
    }
}

