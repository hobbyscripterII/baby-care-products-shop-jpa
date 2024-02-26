package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.AdminSelOrderSalesVo;
import com.baby.babycareproductsshop.admin.order.model.AdminSelOrderStatisticsDto;
import com.baby.babycareproductsshop.admin.order.model.AdminSelTotalOrderCntVo;
import com.baby.babycareproductsshop.response.ApiResponse;
import com.baby.babycareproductsshop.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminOrderService2 {
    private final AuthenticationFacade authenticationFacade;
    private final AdminOrderRepository2 orderRepository;

    public ApiResponse<?> getSalesStatistics(AdminSelOrderStatisticsDto dto) {
        List<AdminSelOrderSalesVo> result = orderRepository.selOrderSales(dto);
        Map<String, AdminSelOrderSalesVo> resultMap = new HashMap<>();
        for (AdminSelOrderSalesVo vo : result) {
            vo.setCostPrice((int)(vo.getTotalSales() * 0.5));
            vo.setEarnings((int)(vo.getTotalSales() * 0.9) - vo.getCostPrice());
            resultMap.put(vo.getCreatedAt().toLocalDate().toString(), vo);
        }
        log.info("resultMap : {}", resultMap);
        return new ApiResponse<>(result);
    }

    public ApiResponse<?> getOrderCntStatistics(AdminSelOrderStatisticsDto dto) {
        List<AdminSelTotalOrderCntVo> result = orderRepository.selTotalOrderCnt(dto);

        return new ApiResponse<>(result);
    }
}