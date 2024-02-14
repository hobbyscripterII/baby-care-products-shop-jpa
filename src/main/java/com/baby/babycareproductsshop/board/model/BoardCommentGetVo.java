package com.baby.babycareproductsshop.board.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BoardCommentGetVo {
    @Schema(title = "댓글 PK", description = "")
    private int icomment;
    @Schema(title = "회원 PK", description = "")
    private int iuser;
    @Schema(title = "회원 이름", description = "")
    private String nm;
    @Schema(title = "댓글 내용", description = "")
    private String comment;
    @Schema(title = "댓글 작성일", description = "")
    private String createdAt;
}
