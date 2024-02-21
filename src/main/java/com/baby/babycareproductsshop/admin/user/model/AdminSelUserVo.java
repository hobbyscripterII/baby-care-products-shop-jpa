package com.baby.babycareproductsshop.admin.user.model;

import com.baby.babycareproductsshop.user.model.UserChildDto;
import com.baby.babycareproductsshop.user.model.UserInsAddressDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class AdminSelUserVo {
    private String nm;
    private LocalDateTime registeredAt;
    private String uid;
    private String phoneNumber;
    private String email;
    private List<UserInsAddressDto> addresses;
    private List<UserChildDto> children;
    private String adminMemo;
}