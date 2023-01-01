package com.example.customerDatabase1.Entity;


import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder

@Table(value="customer")
public class Customer {
    @PrimaryKey(value="bill_acc_no")
    @NotBlank(message="Bill account number is mandatory")
    private int billNo;

    @NotBlank(message="first name is mandatory")
    @Column(value="first_name")
    private String fname;

    @Column(value="last_name")
    private String lname;

    @NotBlank(message="Address line 1 is mandatory")
    @Column(value="address_line1")
    private String add1;

    @Column(value="address_line2")
    private String add2;

    @NotBlank
    @Size(max = 50)
    @Column(value="city")
    private String city;

    @NotBlank
    @Size(max = 50)
    @Column(value = "state")
    private String state;

    @Column(value="phone")
    @NotBlank(message="mobile number is required")
    @Size(min=10, max=10)
    private String phone;

    @Size(max=5)
    @NotBlank(message="zipcode is mandatory and it has to be integer")
    @Column(value="zip")
    private int zip;

    @Email(message = "Email is not valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    @NotEmpty(message = "Email cannot be empty")
    @Column(value="email")
    private String email;

    @Column(value="conversation_id")
    private String conversation_id;

}
