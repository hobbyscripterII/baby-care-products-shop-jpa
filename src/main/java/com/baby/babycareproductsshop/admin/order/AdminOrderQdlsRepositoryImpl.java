package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.model.*;
import com.baby.babycareproductsshop.common.Utils;
import com.baby.babycareproductsshop.entity.order.OrderEntity;
import com.baby.babycareproductsshop.entity.order.QOrderEntity;
import com.baby.babycareproductsshop.entity.product.QProductEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class AdminOrderQdlsRepositoryImpl implements AdminOrderQdlsRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QOrderEntity orderEntity = new QOrderEntity("orderEntity");
    private final QProductEntity productEntity = new QProductEntity("productEntity");

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

        // 검색 카테고리 필터
        if (Utils.isNotNull(searchCategory)) {
            jpaQuery.where(targetKeyword(searchCategory, keyword));
        }

        // 기간 검색

        // 시작 날짜, 종료 날짜
        if (Utils.isNotNull(dto.getDateFl())) {
            dto.setStartDate(null);
            dto.setLastDate(null);

//            jpaQuery.where(Expressions.dateTemplate(LocalDate.class, "DATE_FORMAT({0}, {1})"))
        }

        // 기간 선택

        // 결제 방법

        // 주문 상태

        // 정렬

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

    // >>>>> 검색 카테고리
    private BooleanExpression targetKeyword(int searchCategory, String keyword) {
        BooleanExpression booleanExpression = null;

        switch (searchCategory) {
            case 0 -> booleanExpression = iorderLike(Long.valueOf(keyword)); // 회의 필요
            case 1 -> booleanExpression = iproductLike(Long.valueOf(keyword));
            case 2 -> booleanExpression = iuserLike(Long.valueOf(keyword));
            // 3 - 주문자명 4 - 입금자명 5 - 수령자명
            case 3, 4, 5 -> booleanExpression = userNmLike(keyword);
            case 6 -> booleanExpression = phoneNumberLike(keyword);
            default -> {
                return null;
            }
        }
        return booleanExpression;
    }

    private BooleanExpression iorderLike(long iorder) {
        return iorder == 0 ? null : orderEntity.iorder.eq(iorder);
    }

    private BooleanExpression iproductLike(long iproduct) {
        return iproduct == 0 ? null : productEntity.iproduct.eq(iproduct); // * 에러 발생
    }

    private BooleanExpression iuserLike(long iuser) {
        return iuser == 0 ? null : orderEntity.userEntity.iuser.eq(iuser);
    }

    private BooleanExpression userNmLike(String userNm) {
        return !StringUtils.hasText(userNm) ? null : orderEntity.userEntity.nm.eq(userNm); // 추후 수정
    }

    private BooleanExpression phoneNumberLike(String phoneNumber) {
        return !StringUtils.hasText(phoneNumber) ? null : orderEntity.userEntity.phoneNumber.eq(phoneNumber);
    }
}