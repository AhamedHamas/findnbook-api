package com.findnbook.findnbook_backend.service;

import com.findnbook.findnbook_backend.model.VendorType;
import com.findnbook.findnbook_backend.repository.VendorTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorTypeService {

    private final VendorTypeRepository vendorTypeRepository;


    public VendorTypeService(VendorTypeRepository vendorTypeRepository) {
        this.vendorTypeRepository = vendorTypeRepository;
    }

    public List<VendorType> allVendorTypes() {
        System.out.println("Service: Fetching vendor types");
        List<VendorType> result = vendorTypeRepository.findAll();
        System.out.println("Service: Found " + result.size() + " vendor types");
        return result;
    }
}
