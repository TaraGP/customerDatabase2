package com.example.customerDatabase1.Entity;


import lombok.*;

import javax.validation.constraints.*;

import javax.persistence.*;


@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
@Table(name="customer")
@Entity
public class Customer {
    @Id
    @Column(name="bill_acc_no")
    private int billNo;

    @Column(name="first_name")
    private String fname;

    @Column(name="last_name")
    private String lname;

    @Column(name="address_line1")
    private String add1;

    @Column(name="address_line2")
    private String add2;

    @NotBlank
    @Size(max = 50)
    @Column(name="city")
    private String city;

    @NotBlank
    @Size(max = 50)
    @Column(name = "state")
    private String state;

    @Column(name="phone")
    @Size(min=10, max=10)
    private String phone;

    @Column(name="zip")
    private int zip;

    @Email(message = "Email is not valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    @Column(name="email")
    private String email;

    @Column(name="conversation_id")
    private String conversation_id;

}
