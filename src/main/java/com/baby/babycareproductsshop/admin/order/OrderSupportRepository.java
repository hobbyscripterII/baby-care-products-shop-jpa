package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.entity.order.QOrderEntity;
import com.baby.babycareproductsshop.entity.product.QProductEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor
public class OrderSupportRepository {
    protected final JPAQueryFactory jpaQueryFactory;
    protected final QOrderEntity orderEntity = new QOrderEntity("orderEntity");
    protected final QProductEntity productEntity = new QProductEntity("productEntity");

    // [참고] 요구사항 명세서 참고사항에 작성된 순서대로 작성
    // 검색 카테고리
    protected BooleanExpression targetKeyword(int searchCategory, String keyword) {
        BooleanExpression booleanExpression = null;

        switch (searchCategory) {
            case 1 -> booleanExpression = iorderLike(Long.valueOf(keyword)); // pk
            case 2 -> booleanExpression = iproductLike(Long.valueOf(keyword)); // pk
            case 3 -> booleanExpression = iuserLike(Long.valueOf(keyword)); // pk
            // 4 - 주문자명 5 - 입금자명 6 - 수령자명
            case 4, 5, 6 -> booleanExpression = userNmLike(keyword);
            case 7 -> booleanExpression = phoneNumberLike(keyword);
            default -> {
                return null;
            }
        }
        return booleanExpression;
    }

    // 기간 검색(3번과 연계)

    // 시작 날짜, 종료 날짜
    protected BooleanExpression dateLike(String start, String end) {
        if (start == null & end == null) {
            return null;
        } else {
            LocalDateTime startDate = LocalDate.parse(start).atTime(LocalTime.MIN);
            LocalDateTime endDate = LocalDate.parse(end).atTime(LocalTime.MAX);
            return orderEntity.createdAt.between(startDate, endDate);
        }
    }

    // 기간 선택

    // 결제 방법

    // 주문 상태

    // 정렬

    // ------------------------------------------------------------------------------------------
    // 아래는 '검색 카테고리'에 해당되는 컬럼들 모음
    protected BooleanExpression iorderLike(long iorder) {
        return iorder == 0 ? null : orderEntity.iorder.eq(iorder);
    }

    protected BooleanExpression iproductLike(long iproduct) {
        return iproduct == 0 ? null : productEntity.iproduct.eq(iproduct); // * 에러 발생
    }

    protected BooleanExpression iuserLike(long iuser) {
        return iuser == 0 ? null : orderEntity.userEntity.iuser.eq(iuser);
    }

    protected BooleanExpression userNmLike(String userNm) {
        return !StringUtils.hasText(userNm) ? null : orderEntity.userEntity.nm.eq(userNm);
    }

    protected BooleanExpression phoneNumberLike(String phoneNumber) {
        return !StringUtils.hasText(phoneNumber) ? null : orderEntity.userEntity.phoneNumber.eq(phoneNumber);
    }
}
