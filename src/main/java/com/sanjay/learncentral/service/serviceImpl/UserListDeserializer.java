package com.sanjay.learncentral.service.serviceImpl;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.sanjay.learncentral.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserListDeserializer extends JsonDeserializer<List<User>> {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public List<User> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        List<String> userIds = jsonParser.readValueAs(List.class);
        List<User> users = new ArrayList<>();
        for (String userId : userIds) {
            // Retrieve the user from the database
            User user = mongoOperations.findById(userId, User.class);
            // Check if the user exists
            if (user != null) {
                // Clear sensitive fields before adding to the list
                user.setPassword(null); // Clear the password field
                // Add the user to the list
                users.add(user);
            }
        }
        return users;
    }
}
