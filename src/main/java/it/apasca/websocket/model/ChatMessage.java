package it.apasca.websocket.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import it.apasca.websocket.dto.LinkPage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String senderID;
    private String roomID;
    private Date sendTime;
    private LinkPage linkPage;
    
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}
