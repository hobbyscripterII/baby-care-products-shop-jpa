package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.CommonSearchCondition;
import com.baby.babycareproductsshop.admin.model.OrderFilterDto;
import com.baby.babycareproductsshop.admin.model.OrderMemoListDto;
import com.baby.babycareproductsshop.admin.model.OrderSmallFilterDto;
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
public class AdminOrderQdlsRepositoryImpl_ extends CommonSearchCondition implements AdminOrderQdlsRepository_ {
    private final JPAQueryFactory jpaQueryFactory;


}