package com.baby.babycareproductsshop.admin.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchReviewSelVo {
    private String nm; //작성자이름
    private String repPic; // 대표사진
    private Long iproduct; // pk이긴한데 상품코드
    private String productNm; // 상품이름
    private String contents; // 리뷰내용
    private int productScore; // 별점
    private int delFl;

}
