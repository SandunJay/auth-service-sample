package com.sanjay.learncentral.service.serviceImpl;

import com.sanjay.learncentral.auth.ChangePasswordRequest;
import com.sanjay.learncentral.model.User;
import com.sanjay.learncentral.repository.UserRepository;
import com.sanjay.learncentral.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

//    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = ((User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong Password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
//        logger.info("Password changed for user" + user.getEmail());
        userRepository.save(user);
        emailService.send(
                user.getEmail(),
                user.getFirstName(),
                "passwordChange",
               null
        );
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(String id, User updatedUser) {
        User existingUser = userRepository.findById(id);
        if (existingUser != null) {
            // Copy non-password fields from updatedUser to existingUser
            existingUser.setUserName(updatedUser.getUsername());
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setTokens(updatedUser.getTokens());
            return userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
