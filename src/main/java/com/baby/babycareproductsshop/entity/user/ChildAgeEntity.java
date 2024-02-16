package com.baby.babycareproductsshop.entity.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "t_child_age")
public class ChildAgeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ichild_age")
    private Long id;

    @Column(name = "child_age", length = 20, nullable = false)
    private String childAge;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
