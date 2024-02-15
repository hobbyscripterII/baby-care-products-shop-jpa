package com.baby.babycareproductsshop.Entity.User;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_user_agreements")
@Data
public class UserAgreementEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "iclause", referencedColumnName = "iclause")
    private SignupClauseEntity signupClause;

    @Id
    @ManyToOne
    @JoinColumn(name = "iuser", referencedColumnName = "iuser")
    private UserEntity user;

    @Column(name = "agreement", length = 1, nullable = false)
    private char agreement;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}
