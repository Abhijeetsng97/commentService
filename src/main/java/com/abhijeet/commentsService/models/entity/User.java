package com.abhijeet.commentsService.models.entity;

import jakarta.persistence.*;
import lombok.Data;

import static com.abhijeet.commentsService.constant.AppConstants.DELETED_DATA;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
}
