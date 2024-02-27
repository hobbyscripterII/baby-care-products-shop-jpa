package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.OrderFilterDto;
import com.baby.babycareproductsshop.admin.order.model.OrderMemoListDto;
import com.baby.babycareproductsshop.admin.order.model.OrderSmallFilterDto;
import com.baby.babycareproductsshop.entity.order.OrderEntity;
import com.baby.babycareproductsshop.entity.refund.RefundEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminOrderQdlsRepository {
    List<OrderEntity> orderList(OrderFilterDto dto, Pageable pageable);
    List<OrderEntity> orderDetailsList(OrderSmallFilterDto dto, Pageable pageable);
    List<OrderEntity> orderDeleteList(OrderSmallFilterDto dto, Pageable pageable);
    List<RefundEntity> orderRefundList(OrderSmallFilterDto dto, Pageable pageable);
    List<OrderEntity> adminMemoList(OrderMemoListDto dto, Pageable pageable);
    List<OrderEntity> orderDetails(int iorder);
}
