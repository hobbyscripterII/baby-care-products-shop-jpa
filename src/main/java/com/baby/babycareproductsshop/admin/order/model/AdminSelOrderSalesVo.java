package com.baby.babycareproductsshop.admin.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AdminSelOrderSalesVo {
    private int totalSales;
    private LocalDateTime createdAt;
    @JsonIgnore
    private int processState;
    @JsonIgnore
    private int deleteFl;

    private int earnings;
    private int costPrice;

}
