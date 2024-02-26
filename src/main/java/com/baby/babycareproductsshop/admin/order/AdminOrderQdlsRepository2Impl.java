package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.CommonSearchCondition;
import com.baby.babycareproductsshop.admin.order.model.AdminSelOrderStatisticsDto;
import com.baby.babycareproductsshop.admin.order.model.AdminSelOrderSalesVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.baby.babycareproductsshop.entity.order.QOrderEntity.orderEntity;

@Slf4j
@RequiredArgsConstructor
public class AdminOrderQdlsRepository2Impl extends CommonSearchCondition implements AdminOrderQdlsRepository2 {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AdminSelOrderSalesVo> selOrderSales(AdminSelOrderStatisticsDto dto) {
        log.info("AdminSelOrderSalesDto : {}", dto);
        JPAQuery<AdminSelOrderSalesVo> query = jpaQueryFactory.select(Projections.fields(AdminSelOrderSalesVo.class,
                        orderEntity.totalPrice.sum().as("totalPrice"), orderEntity.createdAt,
                        orderEntity.processState, orderEntity.deleteFl
                ))
                .from(orderEntity)
                .orderBy(orderEntity.createdAt.asc());

        if (dto.getMonth() == 0 && dto.getYear() == 0) {
            query.groupBy(orderEntity.createdAt.year());
            query.having(processStateNq(1), deleteFlEq(0));
            return query.fetch();
        }

        if (dto.getMonth() == 0) {
            query.groupBy(orderEntity.createdAt.year(), orderEntity.createdAt.month());
            query.having(whereYear(dto.getYear()), processStateNq(1), deleteFlEq(0));
            return query.fetch();
        }

        query.groupBy(transformDate(orderEntity.createdAt));
        query.having(whereYear(dto.getYear()), whereMonth(dto.getMonth()), processStateNq(1), deleteFlEq(0));
        return query.fetch();
    }
}