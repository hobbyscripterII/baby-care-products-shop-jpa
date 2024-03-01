package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.*;
import com.baby.babycareproductsshop.common.ProcessState;
import com.baby.babycareproductsshop.common.Utils;
import com.baby.babycareproductsshop.entity.order.OrderEntity;
import com.baby.babycareproductsshop.entity.refund.RefundEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.baby.babycareproductsshop.entity.order.QOrderDetailsEntity.orderDetailsEntity;
import static com.baby.babycareproductsshop.entity.order.QOrderEntity.orderEntity;

@Slf4j
public class AdminOrderQdlsRepositoryImpl extends AdminOrderQdlsSupportRepositoryImpl implements AdminOrderQdlsRepository {
    public AdminOrderQdlsRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(jpaQueryFactory);
    }

    private List<OrderEntity> commonJpaQuery(OrderCommonSearchFilterDto dto) {
        return jpaQueryFactory
                .select(orderEntity)
                .from(orderEntity)
                .join(orderDetailsEntity)
                .on(orderEntity.iorder.eq(orderDetailsEntity.orderEntity.iorder))
                .leftJoin(productEntity)
                .on(orderDetailsEntity.productEntity.iproduct.eq(productEntity.iproduct))
                .leftJoin(refundEntity)
                .on(orderDetailsEntity.idetails.eq(refundEntity.orderDetailsEntity.idetails))
                .where(commonSearchFilter(dto))
                .offset(dto.getOffSet())
                .limit(dto.getSize())
                .groupBy(orderEntity.iorder)
                .orderBy(orderListSort(dto.getSort()))
                .fetch();
    }

    public List<OrderEntity> commonListTasks(OrderFilterDto dto, Pageable pageable) {
        return commonJpaQuery(commonDtoTasks(dto, pageable));
    }

    public List<OrderEntity> commonListTasks(OrderUserFilterDto dto, Pageable pageable) {
        return commonJpaQuery(commonDtoTasks(dto, pageable));
    }

    public List<OrderEntity> commonListTasks(OrderSmallFilterDto dto, Pageable pageable) {
        return commonJpaQuery(commonDtoTasks(dto, pageable));
    }

    public List<OrderEntity> commonListTasks(OrderMemoListDto dto, Pageable pageable) {
        return commonJpaQuery(commonDtoTasks(dto, pageable));
    }

    private OrderCommonSearchFilterDto commonDtoTasks(OrderFilterDto dto, Pageable pageable) {
        return OrderCommonSearchFilterDto
                .builder()
                .searchCategory(dto.getSearchCategory())
                .keyword(dto.getKeyword())
                .dateFl(dto.getDateFl())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .payCategory(dto.getPayCategory())
                .processState(dto.getProcessState())
                .offSet(pageable.getOffset())
                .size(pageable.getPageSize())
                .sort(dto.getSort())
                .build();
    }

    private OrderCommonSearchFilterDto commonDtoTasks(OrderUserFilterDto dto, Pageable pageable) {
        return OrderCommonSearchFilterDto
                .builder()
                .iuser(dto.getIuser())
                .dateFl(dto.getDateFl())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .processState(dto.getProcessState())
                .offSet(pageable.getOffset())
                .size(pageable.getPageSize())
                .sort(dto.getSort())
                .build();
    }

    private OrderCommonSearchFilterDto commonDtoTasks(OrderSmallFilterDto dto, Pageable pageable) {
        return OrderCommonSearchFilterDto
                .builder()
                .searchCategory(dto.getSearchCategory())
                .keyword(dto.getKeyword())
                .dateFl(dto.getDateFl())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .payCategory(dto.getPayCategory())
                .processState(dto.getProcessState())
                .offSet(pageable.getOffset())
                .size(pageable.getPageSize())
                .sort(dto.getSort())
                .build();
    }

    private OrderCommonSearchFilterDto commonDtoTasks(OrderMemoListDto dto, Pageable pageable) {
        return OrderCommonSearchFilterDto
                .builder()
                .searchCategory(dto.getSearchCategory())
                .keyword(dto.getKeyword())
                .dateFl(dto.getDateFl())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .offSet(pageable.getOffset())
                .size(pageable.getPageSize())
                .sort(dto.getSort())
                .build();
    }

    @Override
    public List<OrderEntity> getOrderList(OrderFilterDto dto, Pageable pageable) {
        return commonListTasks(dto, pageable);
    }


    @Override
    public List<OrderEntity> getUserOrderList(OrderUserFilterDto dto, Pageable pageable) {
        return commonListTasks(dto, pageable);
    }

    @Override
    public List<OrderEntity> getOrderDetailsList(OrderSmallFilterDto dto, Pageable pageable) {
        return commonListTasks(dto, pageable);
    }

    @Override
    public List<OrderEntity> getOrderDeleteList(OrderSmallFilterDto dto, Pageable pageable) {
        dto.setProcessState(ProcessState.ORDER_CANCEL.getProcessStateNum());
        OrderCommonSearchFilterDto filter = commonDtoTasks(dto, pageable);
        int sort = dto.getSort();

        return jpaQueryFactory
                .select(orderEntity)
                .from(orderEntity)
                .leftJoin(orderEntity)
                .on(refundEntity.orderDetailsEntity.orderEntity.iorder.eq(orderDetailsEntity.orderEntity.iorder))
                .where(commonSearchFilter(filter))
                .orderBy(orderListSort(sort))
                .fetch();
    }

    @Override
    public List<RefundEntity> getOrderRefundList(OrderSmallFilterDto dto, Pageable pageable) {
        dto.setProcessState(ProcessState.REFUND.getProcessStateNum());
        OrderCommonSearchFilterDto filter = commonDtoTasks(dto, pageable);
        int sort = dto.getSort();

        return jpaQueryFactory
                .select(refundEntity)
                .from(refundEntity)
                .leftJoin(orderEntity)
                .on(refundEntity.orderDetailsEntity.orderEntity.iorder.eq(orderDetailsEntity.orderEntity.iorder))
                .where(commonSearchFilter(filter))
                .orderBy(orderListSort(sort))
                .fetch();
    }

    @Override
    public List<OrderEntity> getOrderAdminMemoList(OrderMemoListDto dto, Pageable pageable) {
        return commonListTasks(dto, pageable);
    }

    @Override
    public List<OrderEntity> getOrderDetails(int iorder) {
        return jpaQueryFactory
                .select(orderEntity)
                .from(orderEntity)
                .where(orderEntity.iorder.eq((long) iorder))
                .fetch();
    }

    //------------------------------------------th
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
