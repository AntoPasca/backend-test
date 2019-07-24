package it.apasca.websocket.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;

}
