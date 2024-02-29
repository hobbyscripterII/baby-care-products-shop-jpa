package com.baby.babycareproductsshop.admin.category.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MiddleCategoryInsDto {
    private long imain;
    private long imiddle;
    private String middleCategory;
}
