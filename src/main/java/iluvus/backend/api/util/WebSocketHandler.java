package iluvus.backend.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import iluvus.backend.api.dto.ChatMessageDto;
import iluvus.backend.api.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    private final MongoTemplate mongoTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Store sessions in memory for broadcasting (optional)
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public WebSocketHandler(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        logger.info("WebSocket connected: {}", session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            String payload = message.getPayload();

            // Parse payload into DTO
            ChatMessageDto dto = objectMapper.readValue(payload, ChatMessageDto.class);

            // Save to MongoDB
            ChatMessage chatMessage = new ChatMessage(dto);
            mongoTemplate.save(chatMessage);

            logger.info("Saved chat message to MongoDB: {}", chatMessage.getMessage());

            // Optional: broadcast to all sessions (e.g., participants in a room)
            for (WebSocketSession wsSession : sessions) {
                if (wsSession.isOpen() && !wsSession.getId().equals(session.getId())) {
                    wsSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
                }
            }

        } catch (Exception e) {
            logger.error("Error handling WebSocket message", e);
            try {
                session.sendMessage(new TextMessage("{\"error\": \"Invalid message format\"}"));
            } catch (Exception sendError) {
                logger.error("Error sending error response", sendError);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        logger.info("WebSocket disconnected: {}", session.getId());
    }
}
