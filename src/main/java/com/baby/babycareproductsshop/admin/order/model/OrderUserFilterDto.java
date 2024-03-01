package com.baby.babycareproductsshop.admin.order.model;

import lombok.Data;

@Data
public class OrderUserFilterDto {
    private int iuser;
    private int dateFl;
    private String startDate;
    private String endDate;
    private int processState;
    private int page;
    private int sort;
}
