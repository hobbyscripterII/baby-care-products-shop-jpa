package com.baby.babycareproductsshop.product.model;

import com.baby.babycareproductsshop.review.model.ReviewSelVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(title = "상품 상세 정보 조회시 응답할 전체 리스트")
public class ProductSelVo {

    @Schema(title = "상품 PK")
    private int iproduct;


    @Schema(title = "리뷰 PK")
    private int ireview;

    @Schema(title = "상품 이름")
    private String productNm;


    @Schema(title = "상품 대표 사진")
    private String repPic;

    @Schema(title = "상품 사진")
    private List<String> productPics = new ArrayList<>();


    @Schema(title = "상품 가격")
    private int price;

    // @Schema(title = "상품 수량")
    //  private int remainedProduct;

    @Schema(title = "상품 상세 정보")
    private String productDetails;

    @Schema(title = "찜하기 여부", description = "1 : 찜 한 상태 / 0 : 찜 하지 않은 상태")
    private int likeProduct;

    @Schema(title = "평균 별점")
    private double scoreAvg;

    @Schema(title = "리뷰 갯수")
    private int reviewCnt;

    @Schema(title = "상품 리뷰 리스트")
    List<ReviewSelVo> reviewSelVo = new ArrayList<>();

}
