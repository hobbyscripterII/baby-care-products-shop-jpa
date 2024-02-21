package com.baby.babycareproductsshop.admin.product.model.Repository_ys;


import com.baby.babycareproductsshop.admin.product.model.OrderRecentSelVo;
import com.baby.babycareproductsshop.admin.product.model.OrderStatusCountVo;
import com.baby.babycareproductsshop.admin.product.model.OrderTotalSelVo;
import com.baby.babycareproductsshop.entity.order.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderTotalRepository extends JpaRepository<OrderEntity,Long> {
    @Query("SELECT new com.baby.babycareproductsshop.admin.product.model.OrderTotalSelVo(SUM(o.totalPrice), COUNT(o)) FROM OrderEntity o")
    OrderTotalSelVo getTotalPriceAndCount();




    @Query("SELECT new com.baby.babycareproductsshop.admin.product.model.OrderRecentSelVo(" +
            "o.iorder, u.nm, o.addressNm, o.phoneNumber, o.processState, o.totalPrice, o.createdAt) " +
            "FROM OrderEntity o JOIN o.userEntity u ORDER BY o.createdAt DESC")
    List<OrderRecentSelVo> findRecentOrders(Pageable pageable);


    @Query("SELECT new com.baby.babycareproductsshop.admin.product.model.OrderStatusCountVo(" +
            "CASE " +
            "   WHEN o.processState = 0 THEN '0' " +
            "   WHEN o.processState = 1 THEN '1' " +
            "   WHEN o.processState = 2 THEN '2' " +
            "   WHEN o.processState = 3 THEN '3' " +
            "   WHEN o.processState = 4 THEN '4' " +
            "END, COUNT(o)) " +
            "FROM OrderEntity o " +
            "GROUP BY o.processState")
    List<OrderStatusCountVo> countByProcessState();

}