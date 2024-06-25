package com.sanjay.learncentral.repository;

import com.sanjay.learncentral.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    List<User> findByFirstNameIgnoreCaseOrLastNameIgnoreCase(String firstname, String lastname);

    User findById(String studentId);


    boolean existsById(String id);

    void deleteById(String id);

    List<User> findAllById(List<String> faculties);

    @Query(value="{ 'email' : ?0 }", fields="{ 'role' : 1 }")
    Optional<String> getRoleByEmail(String email);

}
