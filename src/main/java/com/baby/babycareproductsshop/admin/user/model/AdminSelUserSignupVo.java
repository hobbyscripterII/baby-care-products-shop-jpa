package com.baby.babycareproductsshop.admin.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(title = "[Get]api/admin/user/signup response",description = "회원가입통계 조회 응답 데이터")
public class AdminSelUserSignupVo {
    @Schema(title = "날짜")
    private String date;
    @JsonIgnore
    private int registerCnt;
    @Schema(title = "회원가입 비율")
    private String registerRate;
    @Schema(title = "총 회원가입 수")
    private int totalRegisterCnt;
}
