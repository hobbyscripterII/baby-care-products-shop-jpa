package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.AdminSelOrderStatisticsDto;
import com.baby.babycareproductsshop.exception.CommonErrorCode;
import com.baby.babycareproductsshop.exception.RestApiException;
import com.baby.babycareproductsshop.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api_v2/admin/order")
@RequiredArgsConstructor
@Tag(name = "(임시)관리자 기능 - 주문 관리 API")
public class AdminOrderController2 {
    private final AdminOrderService2 service;

    @Operation(summary = "매출 및 주문 통계 조회", description = """
            type = sales : 매출 통계<br>
            type = orderCnt : 주문 통계
            """)
    @GetMapping("/{type}")
    public ApiResponse<?> getOrderStatistics(@PathVariable String type, AdminSelOrderStatisticsDto dto) {
        log.info(type);
        return switch (type) {
            case "sales" -> service.getSalesStatistics(dto);
            case "orderCnt" -> service.getOrderCntStatistics(dto);
            default ->  throw new RestApiException(CommonErrorCode.INVALID_PARAMETER);
        };
    }
}