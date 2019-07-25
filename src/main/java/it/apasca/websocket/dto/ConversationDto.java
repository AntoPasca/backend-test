package it.apasca.websocket.dto;

import it.apasca.websocket.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConversationDto {
	private String id;
    private String title;
    private User creator;
}
