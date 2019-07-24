package it.apasca.websocket.model;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "message")
public class ChatMessage {
	@Id
	private String id;
    private MessageType type;
    private String content;
    private String senderId;
    private String conversationId;
    private Date sendTime;
    
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}
