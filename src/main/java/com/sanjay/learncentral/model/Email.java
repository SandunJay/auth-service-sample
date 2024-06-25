package com.sanjay.learncentral.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Builder
public class Email {
    private String from;
    private String to;
    private String cc = "admin@lc.com";
    private String subject;
    private String msg;
    private Map<String, Object> templateVariables;
    private char reason;
}
