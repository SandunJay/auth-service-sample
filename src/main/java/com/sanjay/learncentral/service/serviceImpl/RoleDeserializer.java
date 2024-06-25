package com.sanjay.learncentral.service.serviceImpl;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.sanjay.learncentral.model.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RoleDeserializer extends StdDeserializer<Role> {

    public RoleDeserializer() {
        this(null);
    }

    protected RoleDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Role deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        // Parse authorities from JSON
        JsonNode authoritiesNode = node.get("authorities");
        if (authoritiesNode != null && authoritiesNode.isArray()) {
            for (JsonNode authorityNode : authoritiesNode) {
                authorities.add(new SimpleGrantedAuthority(authorityNode.asText()));
            }
        }

        // Determine the appropriate Role enum based on the JSON data
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return Role.ADMIN;
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_MANAGER"))) {
            return Role.MANAGER;
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            return Role.USER;
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
            return Role.STUDENT;
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_FACULTY"))) {
            return Role.FACULTY;
        } else {
            throw new IllegalArgumentException("Unknown role: " + authorities);
        }
    }
}
