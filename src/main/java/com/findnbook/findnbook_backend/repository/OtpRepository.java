package com.findnbook.findnbook_backend.repository;

import com.findnbook.findnbook_backend.model.OtpEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OtpRepository extends MongoRepository<OtpEntry, String> {

    Optional<OtpEntry> findByEmail(String email);

    void deleteByEmail(String email);
}