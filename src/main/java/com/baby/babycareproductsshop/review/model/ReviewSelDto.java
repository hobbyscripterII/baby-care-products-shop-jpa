package com.baby.babycareproductsshop.review.model;

import com.baby.babycareproductsshop.common.Const;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReviewSelDto {
    @Schema(title = "페이지")
    private int page;
    //
    @JsonIgnore
    private int iuser;
    //
    @JsonIgnore
    private int startIdx;
    @JsonIgnore
    private int rowCount = Const.PAGE_ROWCOUNT;
    //
    public void setPage(int page){
        this.startIdx = (page - 1) * rowCount;
    }
}
