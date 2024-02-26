package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.OrderFilterDto;
import com.baby.babycareproductsshop.admin.order.model.OrderMemoListDto;
import com.baby.babycareproductsshop.admin.order.model.OrderSmallFilterDto;
import com.baby.babycareproductsshop.entity.order.OrderEntity;
import com.baby.babycareproductsshop.entity.refund.RefundEntity;

import java.util.List;

public interface AdminOrderQdlsRepository {
    List<OrderEntity> orderList(OrderFilterDto dto);
    List<OrderEntity> orderDetailsList(OrderSmallFilterDto dto);
    List<OrderEntity> orderDeleteList(OrderSmallFilterDto dto);
    List<RefundEntity> orderRefundList(OrderSmallFilterDto dto);
    List<OrderEntity> adminMemoList(OrderMemoListDto dto);
}
