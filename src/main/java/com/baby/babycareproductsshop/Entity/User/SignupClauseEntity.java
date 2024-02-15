package com.baby.babycareproductsshop.Entity.User;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_signup_clause")
public class SignupClauseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iclause")
    private Long id;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "contents", columnDefinition = "TEXT", nullable = false)
    private String contents;

    @Column(name = "required", length = 1, nullable = false)
    private char required;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
