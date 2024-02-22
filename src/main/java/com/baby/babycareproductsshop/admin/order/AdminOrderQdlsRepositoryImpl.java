package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.model.*;
import com.baby.babycareproductsshop.common.Utils;
import com.baby.babycareproductsshop.entity.order.OrderEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.baby.babycareproductsshop.entity.order.QOrderEntity.orderEntity;
import static com.baby.babycareproductsshop.entity.product.QProductEntity.productEntity;

@Slf4j
@RequiredArgsConstructor
public class AdminOrderQdlsRepositoryImpl implements AdminOrderQdlsRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OrderEntity> orderList(OrderFilterDto dto) {
        return null;
    }

    @Override
    public List<OrderEntity> orderDetailsList(OrderSmallFilterDto dto) {
        String keyword = dto.getKeyword();
        int searchCategory = dto.getSearchCategory();

        JPAQuery<OrderEntity> jpaQuery = jpaQueryFactory
                .select(orderEntity)
                .from(orderEntity);

        if (searchCategory <= 6 && searchCategory >= 0) {
            jpaQuery.where(targetKeyword(searchCategory, keyword));
        }

        return jpaQuery.fetch();
    }

    @Override
    public List<OrderEntity> orderDeleteList(OrderSmallFilterDto dto) {
        return null;
    }

    @Override
    public List<OrderEntity> orderRefundList(OrderSmallFilterDto dto) {
        return null;
    }

    @Override
    public List<OrderEntity> adminMemoList(OrderMemoListDto dto) {
        return null;
    }

    private BooleanExpression targetKeyword(int searchCategory, String keyword) {
        BooleanExpression booleanExpression = null;

        switch (searchCategory) {
            case 0 -> booleanExpression = orderEntity.iorder.eq(Long.valueOf(keyword));
            case 1 -> booleanExpression = productEntity.iproduct.eq(Long.valueOf(keyword)); // * 에러 발생
            case 2 -> booleanExpression = orderEntity.userEntity.iuser.eq(Long.valueOf(keyword));
            case 3, 4, 5 -> // 3 - 주문자명 4 - 입금자명 5 - 수령자명
                    booleanExpression = orderEntity.userEntity.nm.eq(keyword); // 추후 수정
            case 6 -> booleanExpression = orderEntity.userEntity.phoneNumber.eq(keyword);
            default -> {
                return null;
            }
        }
        return booleanExpression;
    }
}
