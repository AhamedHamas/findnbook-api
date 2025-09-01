package com.findnbook.findnbook_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor      // generates no-args constructor
@AllArgsConstructor     // generates all-args constructor
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
}
