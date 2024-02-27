package com.baby.babycareproductsshop.admin.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrderCommonSearchFilterDto {
    private int searchCategory;
    private String keyword;
    private int dateFl;
    private int dateCategory;
    private String startDate;
    private String endDate;
    private int processState;
    private int payCategory;
    private int sort;

    private long offSet;
    private int size;
}
