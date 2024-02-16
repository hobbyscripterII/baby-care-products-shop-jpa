package com.baby.babycareproductsshop.entity.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_user_child")
@Data
public class UserChildEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ichild")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "iuser", referencedColumnName = "iuser")
    private UserEntity user;

    @Column(name = "gender", length = 1, nullable = false)
    private char gender;

    @ManyToOne
    @JoinColumn(name = "ichild_age")
    private ChildAgeEntity childAgeEntity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
