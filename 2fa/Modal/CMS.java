package com.example.BOWO.Modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CMS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Lob // Indicates that this field should use a large object type in the database
    @Column(columnDefinition = "TEXT")
    private String cms;
}
