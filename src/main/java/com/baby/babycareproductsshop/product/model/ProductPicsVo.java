package com.baby.babycareproductsshop.product.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "상품 사진 넣을 때 필요한 데이터")
public class ProductPicsVo {
    @Schema(title = "리뷰 사진 PK")
    private int ireview; // 리뷰사진 pk

    @Schema(title = "상품 PK")
    private int iproduct; // 상품 pk

    @Schema(title = "상품 사진")
    private String productPic;

    @Schema(title = "리뷰 사진")
    private String reviewPic;


}
