package com.findnbook.findnbook_backend.repository;

import com.findnbook.findnbook_backend.model.Business;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository extends MongoRepository<Business, String> {
    List<Business> findByCategory(String category);
    Optional<Business> findByOwnerId(String ownerId);
}