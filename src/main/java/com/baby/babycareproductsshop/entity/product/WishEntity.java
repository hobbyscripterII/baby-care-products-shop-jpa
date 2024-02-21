package com.baby.babycareproductsshop.entity.product;

import com.baby.babycareproductsshop.entity.CreatedAtEntity;
import com.baby.babycareproductsshop.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_wish_list")
public class WishEntity extends CreatedAtEntity {

    @EmbeddedId
    private WishIds ids;

    @ManyToOne
    @MapsId("iuser")
    @JoinColumn(name = "iuser", columnDefinition = "BIGINT UNSIGNED")
    private UserEntity entity;

    @ManyToOne
    @MapsId("iproduct")
    @JoinColumn(name = "iproduct", columnDefinition = "BIGINT UNSIGNED")
    private ProductEntity productEntity;


}
