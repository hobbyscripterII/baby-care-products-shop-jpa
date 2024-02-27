package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.OrderCommonSearchFilterDto;
import com.baby.babycareproductsshop.admin.order.model.OrderFilterDto;
import com.baby.babycareproductsshop.admin.order.model.OrderMemoListDto;
import com.baby.babycareproductsshop.admin.order.model.OrderSmallFilterDto;
import com.baby.babycareproductsshop.common.ProcessState;
import com.baby.babycareproductsshop.entity.order.OrderEntity;
import com.baby.babycareproductsshop.entity.refund.RefundEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
public class AdminOrderQdlsRepositoryImpl extends AdminOrderQdlsSupportRepositoryImpl implements AdminOrderQdlsRepository {
    public AdminOrderQdlsRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(jpaQueryFactory);
    }

    public List<OrderEntity> commonListTasks(OrderFilterDto dto, Pageable pageable) {
        return commonJpaQuery(commonDtoTasks(dto, pageable));
    }

    public List<OrderEntity> commonListTasks(OrderSmallFilterDto dto, Pageable pageable) {
        return commonJpaQuery(commonDtoTasks(dto, pageable));
    }

    public List<OrderEntity> commonListTasks(OrderMemoListDto dto, Pageable pageable) {
        return commonJpaQuery(commonDtoTasks(dto, pageable));
    }

    private List<OrderEntity> commonJpaQuery(OrderCommonSearchFilterDto dto) {
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
                .offset(dto.getOffSet())
                .limit(dto.getSize())
                .orderBy(orderListSort(dto.getSort()))
                .fetch();
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
    public List<OrderEntity> orderList(OrderFilterDto dto, Pageable pageable) {
        return commonListTasks(dto, pageable);
    }

    @Override
    public List<OrderEntity> orderDetailsList(OrderSmallFilterDto dto, Pageable pageable) {
        return commonListTasks(dto, pageable);
    }

    @Override
    public List<OrderEntity> orderDeleteList(OrderSmallFilterDto dto, Pageable pageable) {
        dto.setProcessState(ProcessState.ORDER_CANCEL.getProcessStateNum());
        return commonListTasks(dto, pageable);
    }

    @Override
    public List<RefundEntity> orderRefundList(OrderSmallFilterDto dto, Pageable pageable) {
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
    public List<OrderEntity> adminMemoList(OrderMemoListDto dto, Pageable pageable) {
        return commonListTasks(dto, pageable);
    }

    @Override
    public List<OrderEntity> orderDetails(int iorder) {
        return jpaQueryFactory
                .select(orderEntity)
                .from(orderEntity)
                .where(orderEntity.iorder.eq((long) iorder))
                .fetch();
    }
}
