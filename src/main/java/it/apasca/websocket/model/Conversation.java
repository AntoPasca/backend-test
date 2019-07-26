package it.apasca.websocket.model;


import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "conversation")
public class Conversation {

    @Id
    private String id;
    private String title;
    private User creator;
    private Date createdAt;
    private Date updatedAt;

}
