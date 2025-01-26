package com.example.BOWO.Modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Column(nullable = false)
    private String username;

//    @Column(nullable = false)
    private String password;

    private String email;

    private String businessId;
    private String brandName;
    private String category;
    private String description;

    private String businessIdVerification;
    private String brandNameVerification;
    private String categoryVerification;
    private String descriptionVerification;


    private String kycVerification = "Not Verified";
    private String comments;

    @Lob
    @Column(name = "image_data",columnDefinition = "LONGBLOB")
    private byte[] imageData;

//    @Column(nullable = false)
    private Boolean isComplete = false;


}
