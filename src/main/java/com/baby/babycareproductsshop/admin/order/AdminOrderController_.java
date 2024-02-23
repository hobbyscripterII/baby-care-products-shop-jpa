package com.baby.babycareproductsshop.admin.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api_v2/admin/order")
@RequiredArgsConstructor
@Tag(name = "(임시)관리자 기능 - 주문 관리 API")
public class AdminOrderController_ {
    private final AdminOrderService_ service;

    }