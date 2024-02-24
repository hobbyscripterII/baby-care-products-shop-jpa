package com.baby.babycareproductsshop.admin.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderSmallFilterDto {
    @JsonIgnore
    private int processState; // 주문 처리 상태
    private int searchCategory; // 검색 카테고리
    private String keyword; // 검색어
    private String startDate; // 시작 날짜
    private String lastDate; // 종료 날짜
    private int dateFl; // 기간 선택
    private long payCategory; // 결제 방법
    private int sort; // 정렬
}
