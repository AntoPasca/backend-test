package it.apasca.websocket.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "partecipants")
public class Partecipants {
    @Id
    private String id;
    private String conversationId;
    private List<String> userIds;

}
