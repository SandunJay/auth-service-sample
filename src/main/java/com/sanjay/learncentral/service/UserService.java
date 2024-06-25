package com.sanjay.learncentral.service;

import com.sanjay.learncentral.auth.ChangePasswordRequest;
import com.sanjay.learncentral.model.User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface UserService {
    void changePassword(ChangePasswordRequest request, Principal connectedUser);


    List<User> getAllUsers();

    User getUserById(String id);

    User updateUser(String id, User updatedUser);

    void deleteUser(String id);
}
