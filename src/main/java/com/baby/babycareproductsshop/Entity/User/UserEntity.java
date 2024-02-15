package com.baby.babycareproductsshop.Entity.User;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iuser")
    private Long id;

    @Column(name = "uid", unique = true, length = 20)
    private String userId;

    @Column(name = "upw", length = 2100)
    private String password;

    @Column(name = "nm", length = 20)
    private String name;

    @Column(name = "phone_number", length = 13)
    private String phoneNumber;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "unregister_fl")
    private int unregisterFlag;

    @Column(name = "role")
    private int role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
