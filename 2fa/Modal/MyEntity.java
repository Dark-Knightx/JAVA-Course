package com.example.BOWO.Modal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "image_data",columnDefinition = "LONGBLOB")
    private byte[] imageData;
    

}

