package com.baby.babycareproductsshop.entity.product_jy;

import com.baby.babycareproductsshop.entity.CreatedAtEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_middle_category")
public class ProductMiddleCategoryEntity extends CreatedAtEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10, columnDefinition = "BIGINT UNSIGNED")
    private Long imiddle;

    @Column(length = 20)
    private String middleCategory;
}
