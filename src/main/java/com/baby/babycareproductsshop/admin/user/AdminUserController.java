package com.baby.babycareproductsshop.admin.user;

import com.baby.babycareproductsshop.admin.user.model.AdminSelAllUserDto;
import com.baby.babycareproductsshop.admin.user.model.AdminUpdUserDto;
import com.baby.babycareproductsshop.response.ApiResponse;
import com.baby.babycareproductsshop.user.model.UserSignInDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Tag(name = "관리자 기능 - 회원 관리 API")
public class AdminUserController {
    private final AdminUserService service;

    @Operation(summary = "관리자 로그인")
    @PostMapping
    public ApiResponse<?> postSigninAdmin(HttpServletResponse res, @RequestBody UserSignInDto dto) {
        return service.postSigninAdmin(res, dto);
    }

    @Operation(summary = "회원 정보 전체 조회", description = """
            unregisterFl = 0 : 탈퇴하지 않은 회원 정보 조회<br>
            unregisterFl = 1 : 탈퇴한 회원 정보 조회
            """)
    @GetMapping("/user")
    public ApiResponse<?> getUserList(AdminSelAllUserDto dto) {
        return service.getUserList(dto, null);
    }

    @Operation(summary = "회원 정보 수정 시 기존 데이터 호출")
    @GetMapping("/user/{iuser}")
    public ApiResponse<?> getUserInfo(@PathVariable long iuser) {
        return service.getUserInfo(iuser);
    }

    @Operation(summary = "관리자에 의한 회원 정보 수정")
    @PatchMapping("/user/{iuser}")
    public ApiResponse<?> patchUserInfo(@PathVariable long iuser, @RequestBody @Valid AdminUpdUserDto dto) {
        return service.patchUserInfo(iuser, dto);
    }
}
