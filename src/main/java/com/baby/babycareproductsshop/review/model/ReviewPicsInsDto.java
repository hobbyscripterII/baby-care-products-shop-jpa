package com.baby.babycareproductsshop.review.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.*;

@Data
@Schema(title = "리뷰 작성 시 필요한 데이터")
public class ReviewPicsInsDto {
    @Schema(title = "주문상세 KEY")
    private int ireview;
    @Schema(title = "여러 장의 사진 정보")
    private List<String> pics = new ArrayList<>();
}

