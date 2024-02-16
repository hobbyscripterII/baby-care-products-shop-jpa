package com.baby.babycareproductsshop.entity.product_jy;

import com.baby.babycareproductsshop.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
//@IdClass(ProductIds.class) // 실무에선 idClass를 더 권장함
@Table(name = "t_product")
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10, columnDefinition = "BIGINT UNSIGNED", nullable = false, insertable = false, updatable = false)
    private Long iproduct;

    @ManyToOne
    @JoinColumn(name = "imain", columnDefinition = "BIGINT UNSIGNED")
    private ProductMainCategoryEntity productMainCategoryEntity;

    @ManyToOne
    @JoinColumn(name = "imiddle", columnDefinition = "BIGINT UNSIGNED")
    private ProductMiddleCategoryEntity middleCategoryEntity;

    @Column(length = 150, nullable = false)
    private String productNm;

    @Column(length = 1000)
    private String productDetails;

    @Column(length = 4, columnDefinition = "TINYINT UNSIGNED")
    @ColumnDefault("'0'")
    private int recommandAge;

    @Column(length = 10, columnDefinition = "INT UNSIGNED", nullable = false)
    private int price;

    @Column(length = 1000, nullable = false)
    private String repPic;

    @Column(columnDefinition = "INT UNSIGNED", length = 10)
    private int remainedCnt;

    @Column(length = 10, columnDefinition = "INT UNSIGNED")
    @ColumnDefault("'0'")
    private int newFl;

    @Column(length = 10, columnDefinition = "INT UNSIGNED")
    @ColumnDefault("'0'")
    private int rcFl;

    @Column(length = 10, columnDefinition = "INT UNSIGNED")
    @ColumnDefault("'0'")
    private int popFl;
}
