package it.apasca.websocket.dto;

import it.apasca.websocket.model.ChatMessage.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
	private String id;
	private MessageType type;
    private String content;
    private UserDto sender;
    private RoomDto room;
    private Date sendTime;

}
