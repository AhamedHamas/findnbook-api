package com.findnbook.findnbook_backend.controller;

import com.findnbook.findnbook_backend.model.VendorType;
import com.findnbook.findnbook_backend.service.VendorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/vendor-types")
@CrossOrigin(origins = "*")
public class VendorTypeController {

    @Autowired
    private VendorTypeService vendorTypeService;

    @GetMapping
    public ResponseEntity<List<VendorType>> getAllVendorTypes() {
        List<VendorType> vendorTypes = vendorTypeService.allVendorTypes();
        System.out.println("Controller: " + vendorTypes);
        return new ResponseEntity<>(vendorTypes, HttpStatus.OK);
    }
}

