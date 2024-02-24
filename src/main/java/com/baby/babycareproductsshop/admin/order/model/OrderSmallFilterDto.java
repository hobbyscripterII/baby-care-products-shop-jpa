package com.baby.babycareproductsshop.admin.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderSmallFilterDto {
    @JsonIgnore
    private int processState; // 주문 처리 상태
    private int searchCategory; // 검색 카테고리
    private String keyword; // 검색어
    private int dateSearchFl; // 기간 검색 : 전체 - 0 | 입금 완료일 - 1 | 배송 완료일 - 2 | 주문 취소일 - 3 | 상품 반품일 - 4
    private String startDate; // 시작 날짜
    private String lastDate; // 종료 날짜
    private int dateFl; // 기간 선택
    private long payCategory; // 결제 방법
    private int sort; // 정렬
}
