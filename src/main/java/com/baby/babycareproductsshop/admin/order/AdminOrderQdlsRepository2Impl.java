package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.AdminSelOrderSalesVo;
import com.baby.babycareproductsshop.admin.order.model.AdminSelOrderStatisticsDto;
import com.baby.babycareproductsshop.admin.order.model.AdminSelTotalOrderCntVo;
import com.baby.babycareproductsshop.common.Utils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.baby.babycareproductsshop.entity.order.QOrderDetailsEntity.orderDetailsEntity;
import static com.baby.babycareproductsshop.entity.order.QOrderEntity.orderEntity;

@Slf4j
@RequiredArgsConstructor
public class AdminOrderQdlsRepository2Impl extends AdminOrderSearchCondition implements AdminOrderQdlsRepository2 {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AdminSelOrderSalesVo> selOrderSales(AdminSelOrderStatisticsDto dto) {
        log.info("AdminSelOrderSalesDto : {}", dto);
        JPAQuery<AdminSelOrderSalesVo> query = jpaQueryFactory.select(Projections.fields(AdminSelOrderSalesVo.class,
                        orderEntity.totalPrice.sum().as("totalSales"), orderEntity.createdAt,
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
            query.having(yearEq(dto.getYear()),
                    processStateNq(1), deleteFlEq(0));
            return query.fetch();
        }

        query.groupBy(transformDate(orderEntity.createdAt));
        query.having(yearEq(dto.getYear()),
                monthEq(dto.getMonth()),
                processStateNq(1), deleteFlEq(0));
        return query.fetch();
    }

    @Override
    public List<AdminSelTotalOrderCntVo> selTotalOrderCnt(AdminSelOrderStatisticsDto dto) {
        log.info("query1");
        JPAQuery<AdminSelTotalOrderCntVo> query1 = jpaQueryFactory.select(Projections.fields(AdminSelTotalOrderCntVo.class,
                        orderDetailsEntity.productCnt.sum().as("totalOrderCnt"),
                        orderDetailsEntity.createdAt,
                        orderDetailsEntity.orderEntity.processState
                ))
                .where(orderDetailsEntity.orderEntity.processState.ne(0))
                .from(orderDetailsEntity)
                .orderBy(orderDetailsEntity.createdAt.asc());
        if (dto.getMonth() == 0 && dto.getYear() == 0) {
            query1.groupBy(orderDetailsEntity.createdAt.year());
        }
        if (dto.getMonth() == 0 && dto.getYear() != 0) {
            query1.groupBy(orderDetailsEntity.createdAt.year(), orderDetailsEntity.createdAt.month());
            query1.having(yearEqFromOrderDetail(dto.getYear()));
        }
        if (dto.getMonth() != 0 && dto.getYear() != 0) {
            query1.groupBy(transformDate(orderDetailsEntity.createdAt));
            query1.having(yearEqFromOrderDetail(dto.getYear()),
                    monthEqFromOrderDetail(dto.getMonth()));
        }
        List<AdminSelTotalOrderCntVo> result1 = query1.fetch();
        if (result1.isEmpty()) {
            return result1;
        }
        Map<String, AdminSelTotalOrderCntVo> map = new HashMap<>();
        for (AdminSelTotalOrderCntVo vo : result1) {
            String key = Utils.getDate(dto.getYear(), dto.getMonth(), vo);
            vo.setDate(key);
            map.put(key, vo);
        }
        log.info("query2");
        JPAQuery<AdminSelTotalOrderCntVo> query2 = jpaQueryFactory.select(Projections.fields(AdminSelTotalOrderCntVo.class,
                        orderDetailsEntity.productCnt.as("recallCnt"),
                        orderDetailsEntity.createdAt,
                        orderDetailsEntity.orderEntity.deleteFl,
                        orderDetailsEntity.refundFl
                ))
                .where(orderDetailsEntity.orderEntity.deleteFl.eq(1).or(orderDetailsEntity.refundFl.eq(1)))
                .from(orderDetailsEntity)
                .orderBy(orderDetailsEntity.createdAt.asc());
        if (dto.getMonth() == 0 && dto.getYear() == 0) {
            query2.groupBy(orderDetailsEntity.createdAt.year());
        }
        if (dto.getMonth() == 0 && dto.getYear() != 0) {
            query2.groupBy(orderDetailsEntity.createdAt.year(), orderDetailsEntity.createdAt.month());
            query2.having(yearEqFromOrderDetail(dto.getYear()));
        }
        if (dto.getMonth() != 0 && dto.getYear() != 0) {
            query2.where(yearEqFromOrderDetail(dto.getYear()),
                    monthEqFromOrderDetail(dto.getMonth()));
        }
        List<AdminSelTotalOrderCntVo> result2 = query2.fetch();
        for (AdminSelTotalOrderCntVo vo : result2) {
            String key = Utils.getDate(dto.getYear(), dto.getMonth(), vo);
            map.get(key).setRecallCnt(vo.getRecallCnt() + map.get(key).getRecallCnt());
        }

        for (AdminSelTotalOrderCntVo vo : result1) {
            vo.setNetOrderCnt(vo.getTotalOrderCnt() - vo.getRecallCnt());
        }
        return result1;
    }

}