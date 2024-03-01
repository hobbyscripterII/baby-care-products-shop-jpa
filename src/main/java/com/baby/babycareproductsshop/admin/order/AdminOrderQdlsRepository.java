package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.*;
import com.baby.babycareproductsshop.entity.order.OrderEntity;
import com.baby.babycareproductsshop.entity.refund.RefundEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminOrderQdlsRepository {
    List<OrderEntity> getOrderList(OrderFilterDto dto, Pageable pageable);
    List<OrderEntity> getUserOrderList(OrderUserFilterDto dto, Pageable pageable);
    List<OrderEntity> getOrderDetailsList(OrderSmallFilterDto dto, Pageable pageable);
    List<OrderEntity> getOrderDeleteList(OrderSmallFilterDto dto, Pageable pageable);
    List<RefundEntity> getOrderRefundList(OrderSmallFilterDto dto, Pageable pageable);
    List<OrderEntity> getOrderAdminMemoList(OrderMemoListDto dto, Pageable pageable);
    List<OrderEntity> getOrderDetails(int iorder);

    //------------------------------------------th
    List<AdminSelOrderSalesVo> selOrderSales(AdminSelOrderStatisticsDto dto);
    List<AdminSelTotalOrderCntVo> selTotalOrderCnt(AdminSelOrderStatisticsDto dto);
}
