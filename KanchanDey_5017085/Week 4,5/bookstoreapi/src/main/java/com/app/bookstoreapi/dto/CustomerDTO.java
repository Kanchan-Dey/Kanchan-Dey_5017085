package com.app.bookstoreapi.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Integer age;
    private Long number; 
}
