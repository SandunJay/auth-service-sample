package com.sanjay.learncentral.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanjay.learncentral.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty
    private String email;
    @JsonProperty
    private Role role;
}
