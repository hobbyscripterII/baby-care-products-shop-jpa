package com.baby.babycareproductsshop.admin.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderProductVo {
    @Schema(title = "이미지", description = "")
    private String repPic;
    @Schema(title = "주문 상품", description = "")
    private String productNm;
    @Schema(title = "수량", description = "")
    private int cnt;
    @Schema(title = "처리 상태", description = "")
    private int processState;
    @JsonIgnore
    private int amount;
}
