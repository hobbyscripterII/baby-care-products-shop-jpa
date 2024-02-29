//package com.baby.babycareproductsshop.admin.order;
//
//import com.baby.babycareproductsshop.admin.order.model.AdminSelOrderSalesVo;
//import com.baby.babycareproductsshop.admin.order.model.AdminSelOrderStatisticsDto;
//import com.baby.babycareproductsshop.admin.order.model.AdminSelTotalOrderCntVo;
//import com.baby.babycareproductsshop.common.Utils;
//import com.baby.babycareproductsshop.response.ApiResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
////@Service
//@RequiredArgsConstructor
//public class AdminOrderService2 {
//    private final AdminOrderRepository2 adminOrderRepository;
//
//    public ApiResponse<?> getSalesStatistics(AdminSelOrderStatisticsDto dto) {
//        List<AdminSelOrderSalesVo> result = adminOrderRepository.selOrderSales(dto);
//        Map<String, AdminSelOrderSalesVo> map = new HashMap<>();
//        for (AdminSelOrderSalesVo vo : result) {
//            vo.setCostPrice((int) (vo.getTotalSales() * 0.5));
//            vo.setEarnings((int) (vo.getTotalSales() * 0.9) - vo.getCostPrice());
//            String key = Utils.getDate(dto.getYear(), dto.getMonth(), vo);
//            vo.setDate(key);
//            map.put(key, vo);
//        }
//        if (dto.getYear() == 0 && dto.getMonth() == 0) {
//            return new ApiResponse<>(result);
//        }
//        int date = Utils.getDaysOrMonths(dto.getYear(), dto.getMonth());
//        for (int i = 1; i <= date; i++) {
//            String key = Utils.getKey(dto.getYear(), dto.getMonth(), i);
//            AdminSelOrderSalesVo vo = map.get(key);
//            if (vo == null) {
//                map.put(key, new AdminSelOrderSalesVo(key));
//            }
//        }
//        log.info("resultMap : {}", map);
//        result = map.values().stream().sorted().toList();
//        return new ApiResponse<>(result);
//    }
//
//    public ApiResponse<?> getOrderCntStatistics(AdminSelOrderStatisticsDto dto) {
//        List<AdminSelTotalOrderCntVo> result = adminOrderRepository.selTotalOrderCnt(dto);
//        Map<String, AdminSelTotalOrderCntVo> resultMap = new HashMap<>();
//        if (dto.getYear() == 0 && dto.getMonth() == 0) {
//            return new ApiResponse<>(result);
//        }
//        for (AdminSelTotalOrderCntVo vo : result) {
//            String key = Utils.getDate(dto.getYear(), dto.getMonth(), vo);
//            resultMap.put(key, vo);
//        }
//        int date = Utils.getDaysOrMonths(dto.getYear(), dto.getMonth());
//        for (int i = 1; i <= date; i++) {
//            String key = Utils.getKey(dto.getYear(), dto.getMonth(), i);
//            AdminSelTotalOrderCntVo vo = resultMap.get(key);
//            if (vo == null) {
//                resultMap.put(key, new AdminSelTotalOrderCntVo(key));
//            }
//        }
//        log.info("resultMap : {}", resultMap);
//        result = resultMap.values().stream().sorted().toList();
//        return new ApiResponse<>(result);
//    }
//}
