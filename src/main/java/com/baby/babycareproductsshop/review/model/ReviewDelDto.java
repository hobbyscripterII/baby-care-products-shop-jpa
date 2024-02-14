package com.baby.babycareproductsshop.review.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "리뷰 삭제 시 필요한 데이터")
public class ReviewDelDto {
    @Schema(title = "리뷰 PK")
    private int ireview;
    @JsonIgnore
    private int iuser;
}
