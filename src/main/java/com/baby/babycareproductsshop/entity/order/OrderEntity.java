package com.baby.babycareproductsshop.entity.order;

import com.baby.babycareproductsshop.entity.BaseEntity;
import com.baby.babycareproductsshop.entity.user.UserAddressEntity;
import com.baby.babycareproductsshop.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_order")
public class OrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long iorder;

    @ManyToOne
    @JoinColumn(name = "iuser", nullable = false)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iaddress", nullable = false)
    private UserAddressEntity userAddressEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ipayment_option", nullable = false)
    @ColumnDefault("'1'")
    private OrderPaymentOptionEntity orderPaymentOptionEntity;

    @Column(length = 20, name = "address_nm")
    private String addressNm;

    @Column(length = 13)
    private String phoneNumber;

    @Column(length = 50)
    private String email;

    @Column(columnDefinition = "TINYINT UNSIGNED", length = 20, nullable = false)
    private int processState;

    @Column(columnDefinition = "INT UNSIGNED")
    @ColumnDefault("0")
    private int totalPrice;

    @Column(length = 100)
    private String deliveryRequest;

    @Column(length = 11, columnDefinition = "INT UNSIGNED")
    @ColumnDefault("0")
    private int deleteFl;

    private LocalDateTime depositedAt; // 입금 완료 일자
    private LocalDateTime deliveryCompletedAt; // 배송 완료 일자
}
