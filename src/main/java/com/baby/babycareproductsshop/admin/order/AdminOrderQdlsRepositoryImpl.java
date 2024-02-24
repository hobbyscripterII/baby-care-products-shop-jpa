package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.OrderFilterDto;
import com.baby.babycareproductsshop.admin.order.model.OrderMemoListDto;
import com.baby.babycareproductsshop.admin.order.model.OrderSmallFilterDto;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;

@Slf4j
public class AdminOrderQdlsRepositoryImpl extends OrderSupportRepository implements AdminOrderQdlsRepository {

    public AdminOrderQdlsRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(jpaQueryFactory);
    }

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

        // * [완료] 검색 카테고리 - 완료
        if (Utils.isNotNull(searchCategory)) {
            // searchCategory - 검색 카테고리
            // keyword - 검색어
            jpaQuery.where(targetKeyword(searchCategory, keyword));
        }

        // * 기간 선택
        // 오늘 - 0 어제 - 1 일주일 - 2 지난 달 - 3 1개월 - 4 3개월 - 5 전체 - 6

        // * 기간 검색(3-2와 연계)
        // 3-1. '기간 검색' select box 선택 및 기간 입력 x 우측 버튼만 클릭했을 경우
        // 버튼이 우선순위가 더 높기 때문에 만약 기간을 입력했을 경우 null 처리
        if (Utils.isNotNull(dto.getDateFl())) {
            dto.setStartDate(null);
            dto.setLastDate(null);

            // 전체 - 0 입금 완료일 - 1 배송 완료일 - 2 상품 취소일 - 3 상품 반품일 - 4
            //

        }

        // * [완료] 3-2. '기간 검색' select box 선택 후 기간 입력 시
        if (Utils.isNotNull(dto.getStartDate()) && Utils.isNotNull(dto.getLastDate())) {
            jpaQuery.where(dateLike(dto.getStartDate(), dto.getLastDate()));
        }

        // * [완료] 4. 결제 방법
        // 전체 - 0 무통장 입금 - 1 카드 - 2
        if (dto.getPayCategory() > 0) {
            jpaQuery.where(orderEntity.orderPaymentOptionEntity.ipaymentOption.eq(dto.getPayCategory()));
        }

        // * 주문 상태
        // 전체 - 0 입금 대기 - 1 배송 준비중 - 2 배송중 - 3 배송 완료 - 4

        // * 정렬

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
}
