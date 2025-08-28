package com.findnbook.findnbook_backend.repository;


import com.findnbook.findnbook_backend.model.VendorType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorTypeRepository extends MongoRepository<VendorType,String> {
}
