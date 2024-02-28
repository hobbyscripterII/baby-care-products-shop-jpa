package com.baby.babycareproductsshop.admin.order.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderFilterDto {
    private int processState;
    private int dateCategory;
    private int searchCategory;
    @Schema(defaultValue = "")
    private String keyword;
    @Schema(defaultValue = "")
    private String startDate;
    @Schema(defaultValue = "")
    private String endDate;
    private int dateFl;
    private int payCategory;
    private int sort;
}
