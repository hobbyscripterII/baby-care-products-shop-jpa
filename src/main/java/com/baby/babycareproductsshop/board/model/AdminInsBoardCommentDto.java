package com.baby.babycareproductsshop.board.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class AdminInsBoardCommentDto {
    @JsonInclude
    private long iboard;
    private String comment;
}
