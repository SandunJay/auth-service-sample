package com.sanjay.learncentral.auth;


import com.sanjay.learncentral.config.JwtService;
import com.sanjay.learncentral.model.User;
import com.sanjay.learncentral.repository.UserRepository;
import com.sanjay.learncentral.service.serviceImpl.EmailService;
import com.sanjay.learncentral.token.Token;
import com.sanjay.learncentral.token.TokenRepository;
import com.sanjay.learncentral.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final String CONFIRMATION_URL = "http://localhost:8060/auth/api/v1/auth/confirm?token=%s";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    public String register(RegisterRequest request) {
        Optional<User> userExists = userRepository.findByEmail(request.getEmail());

        if (userExists.isPresent()){
            throw new IllegalStateException("User already exists with the same email");
        }
        var user = User.builder()
                .userName(request.getUserName())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        emailService.send(
                request.getEmail(),
                request.getFirstName(),
                "confirm-email",
                String.format(CONFIRMATION_URL,jwtToken)
        );

        return "User has been successfully registered";
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials"));

        if (!user.isAccountVerified()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Account is not verified");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeUserTokens(user);
        saveUserToken(user, jwtToken);

//        String role = userRepository.getRoleByEmail(request.getEmail())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Role not found"));

//        user.getRole();

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    private void revokeUserTokens(User user){
        var validTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if(validTokens.isEmpty())
            return;
        validTokens.forEach(t ->{
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                String jsonResponse = new ObjectMapper().writeValueAsString(authResponse);
                response.setContentType("application/json");
                response.getWriter().write(jsonResponse);
            }
        }
    }

    public String confirm(String token) {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));
        if (savedToken.isExpired()){
            var user = savedToken.getUser();
            var newToken = jwtService.generateToken(user);
            return "Token expired. New token has been sent to your email";
        } else if (!savedToken.isExpired()) {
            User user = savedToken.getUser();
            user.setAccountVerified(true);
            userRepository.save(user);
            return "User has been successfully confirmed";
        }
        return null;
    }

    public void saveLoginHistory(String username, String token){
        //save the login history

    }

}
