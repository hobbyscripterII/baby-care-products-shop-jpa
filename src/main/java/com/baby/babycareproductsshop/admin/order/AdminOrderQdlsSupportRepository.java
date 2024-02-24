package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.entity.order.QOrderEntity;
import com.baby.babycareproductsshop.entity.product.QProductEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;

@Slf4j
@RequiredArgsConstructor
public class AdminOrderQdlsSupportRepository {
    protected final JPAQueryFactory jpaQueryFactory;
    protected final QOrderEntity orderEntity = new QOrderEntity("orderEntity");
    protected final QProductEntity productEntity = new QProductEntity("productEntity");

    // [참고] 요구사항 명세서 참고사항에 작성된 순서대로 작성
    // 검색 카테고리
    protected BooleanExpression targetKeyword(int searchCategory, String keyword) {
        BooleanExpression booleanExpression = null;
        long pk = Long.parseLong(keyword);

        switch (searchCategory) {
            // 1 - 주문 번호 2 - 상품 번호 3 - 유저 번호
            case 1 -> booleanExpression = iorderLike(pk);
            case 2 -> booleanExpression = iproductLike(pk);
            case 3 -> booleanExpression = iuserLike(pk);
            // 4 - 주문자명 5 - 입금자명 6 - 수령자명
            case 4, 5, 6 -> booleanExpression = userNmLike(keyword);
            // 7 - 수령자 전화번호
            case 7 -> booleanExpression = phoneNumberLike(keyword);
            default -> {
                return null;
            }
        }
        return booleanExpression;
    }

    // 기간 선택
    // 전체 - 0 | 오늘 - 1 | 어제 - 2 | 일주일 - 3 | (지난 달 - 4 1개월 - 5) - 4 | 3개월 - 5 | 전체 - 6
    protected BooleanExpression dateSelectSearch(int dateFl) {
        BooleanExpression booleanExpression = null;

        switch (dateFl) {
            case 1 -> booleanExpression = orderEntity.createdAt.between(todayStartTime(), todayEndTime());
            case 2 -> booleanExpression = orderEntity.createdAt.between(yesterdayStartTime(), yesterdayEndTime());
            case 3 -> booleanExpression = orderEntity.createdAt.between(todayStartTime().minusDays(7), todayEndTime());
            case 4, 5 -> booleanExpression = orderEntity.createdAt.between(monthStartDay(), monthEndDay());
            case 6 -> booleanExpression = orderEntity.createdAt.between(monthStartDay().minusMonths(3), monthEndDay().minusMonths(3));
            default -> {
                return null;
            }
        }
        return booleanExpression;
    }

    // 기간 검색(3번과 연계)

    // 시작 날짜, 종료 날짜
    protected BooleanExpression dateRangeSearch(String start, String end) {
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

    // ------------------------------------------------------------------------------------------
    // 아래는 어제, 오늘 시작 시간과 끝 시간 조건문 모음
    private LocalDateTime todayStartTime() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    private LocalDateTime todayEndTime() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }

    private LocalDateTime yesterdayStartTime() {
        return LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN);
    }

    private LocalDateTime yesterdayEndTime() {
        return LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);
    }

    private LocalDateTime monthStartDay() {
        return LocalDateTime.of(YearMonth.now().minusMonths(1).atDay(1), LocalTime.MIN);
    }

    private LocalDateTime monthEndDay() {
        // 30, 31일은..^-^
        // 2월은..^-^

        return LocalDateTime.of(YearMonth.now().minusMonths(1).atDay(31), LocalTime.MAX);
    }
}
