package com.baby.babycareproductsshop.admin.order.model;

import lombok.Data;

@Data
public class OrderMemoListDto {
    private int searchCategory; // 검색 카테고리
    private String keyword; // 검색어
    private String startDate; // 시작 날짜
    private String endDate; // 종료 날짜
    private int dateFl; // 기간 선택
    private int page;
    private int sort; // 정렬
}
