package it.apasca.websocket.dto;

import java.util.Date;

import it.apasca.websocket.model.ChatMessage.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
	private String id;
	private MessageType type;
    private String content;
    private UserDto sender;
    private ConversationDto conversation;
    private Date sendTime;

}
