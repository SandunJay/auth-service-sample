package com.sanjay.learncentral.token;

import com.sanjay.learncentral.model.User;
import jakarta.persistence.GeneratedValue;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tokens")
public class Token {
    @Id
    @GeneratedValue
    private String id;
    private String token;
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;

    @DBRef
    private User user;

}
