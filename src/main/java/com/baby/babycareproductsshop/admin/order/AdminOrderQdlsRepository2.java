package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.AdminSelOrderStatisticsDto;
import com.baby.babycareproductsshop.admin.order.model.AdminSelOrderSalesVo;

import java.util.List;

public interface AdminOrderQdlsRepository2 {
    List<AdminSelOrderSalesVo> selOrderSales(AdminSelOrderStatisticsDto dto);
}
