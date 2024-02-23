package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.AdminSelOrderSalesDto;
import com.baby.babycareproductsshop.entity.order.OrderEntity;

import java.util.List;

public interface AdminOrderQdlsRepository_ {
    List<OrderEntity> selOrderSales(AdminSelOrderSalesDto dto);
}
