package com.baby.babycareproductsshop.admin.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class UserRecentSelVo {
    private String nm;
    private String uid;
    private String email;
    private LocalDateTime createdAt;
}
