package it.apasca.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

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
    private User sender;
    private Conversation conversation;
    private Date sendTime;
    
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}
