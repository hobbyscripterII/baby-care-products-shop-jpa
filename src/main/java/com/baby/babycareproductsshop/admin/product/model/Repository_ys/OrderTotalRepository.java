package com.baby.babycareproductsshop.admin.product.model.Repository_ys;


import com.baby.babycareproductsshop.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderTotalRepository extends JpaRepository<OrderEntity,Long> {
//    @Query("SELECT new com.baby.babycareproductsshop.admin.product.model.OrderTotalSelVo(SUM(o.totalPrice), COUNT(o)) FROM OrderEntity o")
//    OrderTotalSelVo getTotalPriceAndCount();


//    @Query("SELECT new com.baby.babycareproductsshop.admin.product.model.OrderRecentSelVo(" +
//            "o.iorder, u.nm, o.addressNm, o.phoneNumber, o.processState, o.totalPrice, o.createdAt) " +
//            "FROM OrderEntity o JOIN o.userEntity u ORDER BY o.createdAt DESC")
//    List<OrderRecentSelVo> findRecentOrders(Pageable pageable);


    int countByDeleteFl(int deleteFl);
    Long countByProcessState(Integer processState);



}
