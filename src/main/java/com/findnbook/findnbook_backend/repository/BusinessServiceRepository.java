package com.findnbook.findnbook_backend.repository;

import com.findnbook.findnbook_backend.model.BusinessService;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BusinessServiceRepository extends MongoRepository<BusinessService, String> {
    List<BusinessService> findByBusinessId(String businessId);
}