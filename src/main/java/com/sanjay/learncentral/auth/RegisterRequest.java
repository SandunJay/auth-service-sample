package com.sanjay.learncentral.auth;

import com.sanjay.learncentral.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
