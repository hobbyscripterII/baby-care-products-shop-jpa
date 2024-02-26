package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.OrderCommonSearchFilterDto;
import com.baby.babycareproductsshop.admin.order.model.OrderFilterDto;
import com.baby.babycareproductsshop.admin.order.model.OrderMemoListDto;
import com.baby.babycareproductsshop.admin.order.model.OrderSmallFilterDto;
import com.baby.babycareproductsshop.common.ProcessState;
import com.baby.babycareproductsshop.entity.order.OrderEntity;
import com.baby.babycareproductsshop.entity.refund.RefundEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AdminOrderQdlsRepositoryImpl extends AdminOrderQdlsSupportRepositoryImpl implements AdminOrderQdlsRepository {
    public AdminOrderQdlsRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(jpaQueryFactory);
    }

    private List<OrderEntity> commonDtoTasks(OrderCommonSearchFilterDto dto) {
        return jpaQueryFactory
                .select(orderEntity)
                .from(orderEntity)
                .leftJoin(orderDetailsEntity)
                .on(orderEntity.iorder.eq(orderDetailsEntity.orderEntity.iorder))
                .leftJoin(productEntity)
                .on(orderDetailsEntity.productEntity.iproduct.eq(productEntity.iproduct))
                .leftJoin(refundEntity)
                .on(orderDetailsEntity.idetails.eq(refundEntity.orderDetailsEntity.idetails))
                .where(commonSearchFilter(dto))
                .orderBy(orderListSort(dto.getSort()))
                .fetch();
    }

    private OrderCommonSearchFilterDto commonDtoTasks(OrderSmallFilterDto dto) {
        return OrderCommonSearchFilterDto
                .builder()
                .searchCategory(dto.getSearchCategory())
                .keyword(dto.getKeyword())
                .dateFl(dto.getDateFl())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .payCategory(dto.getPayCategory())
                .processState(dto.getProcessState())
                .sort(dto.getSort())
                .build();
    }

    private OrderCommonSearchFilterDto commonDtoTasks(OrderFilterDto dto) {
        return OrderCommonSearchFilterDto
                .builder()
                .searchCategory(dto.getSearchCategory())
                .keyword(dto.getKeyword())
                .dateFl(dto.getDateFl())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .payCategory(dto.getPayCategory())
                .processState(dto.getProcessState())
                .sort(dto.getSort())
                .build();
    }

    public List<OrderEntity> commonListTasks(OrderSmallFilterDto dto) {
        return commonDtoTasks(commonDtoTasks(dto));
    }

    public List<OrderEntity> commonListTasks(OrderFilterDto dto) {
        return commonDtoTasks(commonDtoTasks(dto));
    }

    @Override
    public List<OrderEntity> orderList(OrderFilterDto dto) {
        return commonListTasks(dto);
    }

    @Override
    public List<OrderEntity> orderDetailsList(OrderSmallFilterDto dto) {
        return commonListTasks(dto);
    }

    @Override
    public List<OrderEntity> orderDeleteList(OrderSmallFilterDto dto) {
        dto.setProcessState(ProcessState.ORDER_CANCEL.getProcessStateNum());
        return commonListTasks(dto);
    }

    @Override
    public List<RefundEntity> orderRefundList(OrderSmallFilterDto dto) {
        dto.setProcessState(ProcessState.REFUND.getProcessStateNum());
        OrderCommonSearchFilterDto filter = commonDtoTasks(dto);
        return jpaQueryFactory
                .select(refundEntity)
                .from(refundEntity)
                .leftJoin(orderEntity)
                .on(refundEntity.orderDetailsEntity.orderEntity.iorder.eq(orderDetailsEntity.orderEntity.iorder))
                .leftJoin(productEntity)
                .on(refundEntity.orderDetailsEntity.productEntity.iproduct.eq(productEntity.iproduct))
                .where(commonSearchFilter(filter))
                .orderBy(orderListSort(dto.getSort())).fetch();
    }

    @Override
    public List<OrderEntity> adminMemoList(OrderMemoListDto dto) {
        return null;
    }
}
