package com.baby.babycareproductsshop.admin.board;

import com.baby.babycareproductsshop.admin.board.model.AdminSelBoardVo;
import com.baby.babycareproductsshop.board.model.AdminInsBoardCommentDto;
import com.baby.babycareproductsshop.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/board")
public class AdminBoardController {
    private final AdminBoardService service;

    @GetMapping
    public ApiResponse<List<AdminSelBoardVo>> getInquiryList(@RequestParam(required = false) String keyword) {
        return service.getInquiryList(keyword);
    }

    @GetMapping("/{iboard}")
    public ApiResponse<AdminSelBoardVo> getInquiry(@PathVariable long iboard) {
        return service.getInquiry(iboard);
    }

    @PostMapping("/{iboard}")
    public ApiResponse<?> postBoardComment(@PathVariable long iboard,
                                           @RequestBody AdminInsBoardCommentDto dto) {
        dto.setIboard(iboard);
        return service.postBoardComment(dto);
    }

    @DeleteMapping
    public ApiResponse<?> delBoard(long iboard) {
        return service.delBoard(iboard);
    }

    @DeleteMapping("/{iboard}")
    public ApiResponse<?> delBoardComment(@PathVariable long iboard, @RequestParam long icomment) {
        return service.delBoardComment(iboard, icomment);
    }
}
