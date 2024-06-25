package com.sanjay.learncentral.token;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String> {

    @Aggregation("{ $match: { 'user.id': ?0, expired: { $ne: true }, revoked: { $ne: true } } }")
    List<Token> findAllValidTokenByUser(String userId);

    Optional<Token>findByToken(String token);


}
