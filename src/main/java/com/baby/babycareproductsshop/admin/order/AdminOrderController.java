package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.model.*;
import com.baby.babycareproductsshop.common.ResVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/admin/order")
@RequiredArgsConstructor
@Tag(name = "관리자 기능 - 주문 관리 API")
public class AdminOrderController {
    private final AdminOrderService service;

    @PutMapping
    @Operation(summary = "주문 일괄 처리",
            description = "<ul><strong>iorders - 주문 번호(PK)</strong></ul>" +
                    "<ul><strong>processState</strong><br>\n" +
                    "<li>전체 - 0</li>\n" +
                    "<li>어제 - 1</li>\n" +
                    "<li>일주일 - 2</li>\n" +
                    "<li>지난 달 - 3</li>\n" +
                    "<li>1개월 - 4</li>\n" +
                    "<li>3개월 - 5</li>\n" +
                    "<li>전체 - 6</li></ul>\n")
    public ResVo orderBatchProcess(@RequestBody OrderBatchProcessDto dto) {
        return service.orderBatchProcess(dto);
    }

    @GetMapping
    @Operation(summary = "주문 전체 리스트 출력",
            description = "<ul><strong>searchCategory</strong><br>" +
                    "<li>주문 번호(iorder) - 0</li>\n" +
                    "<li>상품 번호(일련 번호, iproduct) - 1</li>\n" +
                    "<li>회원 아이디 - 2</li>\n" +
                    "<li>주문자명 - 3</li>\n" +
                    "<li>입금자명 - 4</li>\n" +
                    "<li>수령자명 - 5</li>\n" +
                    "<li>수령자 핸드폰 번호 - 6</li></ul>\n" +
                    "<ul><strong>keyword - 검색 키워드</strong></ul>\n" +
                    "<ul><strong>startDate - (예) 2024-02-22</strong><br></ul>\n" +
                    "<ul><strong>lastDate - (예) 2024-02-22</strong><br></ul>\n" +
                    "<ul><strong>dateFl</strong><br>\n" +
                    "<li>오늘 - 0</li>\n" +
                    "<li>어제 - 1</li>\n" +
                    "<li>일주일 - 2</li>\n" +
                    "<li>지난 달 - 3</li>\n" +
                    "<li>1개월 - 4</li>\n" +
                    "<li>3개월 - 5</li>\n" +
                    "<li>전체 - 6</li></ul>\n" +
                    "<ul><strong>payCategory</strong><br>\n" +
                    "<li>전체 - 0</li>\n" +
                    "<li>무통장 입금 - 1</li>\n" +
                    "<li>신용카드 - 2</li></ul>\n" +
                    "<ul><strong>processState</strong><br>\n" +
                    "<li>전체 - 0</li>\n" +
                    "<li>어제 - 1</li>\n" +
                    "<li>일주일 - 2</li>\n" +
                    "<li>지난 달 - 3</li>\n" +
                    "<li>1개월 - 4</li>\n" +
                    "<li>3개월 - 5</li>\n" +
                    "<li>전체 - 6</li></ul>\n" +
                    "<ul><strong>sort</strong><br>\n" +
                    "<li>주문일 역순 - 0</li>\n" +
                    "<li>주문일 순 - 1</li>\n" +
                    "<li>처리일 역순 - 2</li>\n" +
                    "<li>처리일 순 - 3</li></ul>\n" +
                    "\n")
    public OrderListVo orderList(OrderFilterDto dto) {
        return service.orderList(dto);
    }

    @GetMapping("/details")
    @Operation(summary = "주문 상세 리스트 출력",
            description = "<ul><strong>searchCategory</strong><br>" +
                    "<li>주문 번호(iorder) - 0</li>\n" +
                    "<li>상품 번호(일련 번호, iproduct) - 1</li>\n" +
                    "<li>회원 아이디 - 2</li>\n" +
                    "<li>주문자명 - 3</li>\n" +
                    "<li>입금자명 - 4</li>\n" +
                    "<li>수령자명 - 5</li>\n" +
                    "<li>수령자 핸드폰 번호 - 6</li></ul>\n" +
                    "<ul><strong>keyword - 검색 키워드</strong></ul>\n" +
                    "<ul><strong>startDate - (예) 2024-02-22</strong><br></ul>\n" +
                    "<ul><strong>lastDate - (예) 2024-02-22</strong><br></ul>\n" +
                    "<ul><strong>dateFl</strong><br>\n" +
                    "<li>오늘 - 0</li>\n" +
                    "<li>어제 - 1</li>\n" +
                    "<li>일주일 - 2</li>\n" +
                    "<li>지난 달 - 3</li>\n" +
                    "<li>1개월 - 4</li>\n" +
                    "<li>3개월 - 5</li>\n" +
                    "<li>전체 - 6</li></ul>\n" +
                    "<ul><strong>payCategory</strong><br>\n" +
                    "<li>전체 - 0</li>\n" +
                    "<li>무통장 입금 - 1</li>\n" +
                    "<li>신용카드 - 2</li></ul>\n" +
                    "<ul><strong>sort</strong><br>\n" +
                    "<li>주문일 역순 - 0</li>\n" +
                    "<li>주문일 순 - 1</li>\n" +
                    "<li>처리일 역순 - 2</li>\n" +
                    "<li>처리일 순 - 3</li></ul>\n" +
                    "\n")
    public OrderDetailsListVo orderDetailsList(@RequestBody OrderSmallFilterDto dto) {
        log.info("dto = {}", dto);
        return service.orderDetailsList(dto);
    }

    @GetMapping("/delete")
    @Operation(summary = "주문 취소 리스트 출력",
            description = "<ul><strong>searchCategory</strong><br>" +
                    "<li>주문 번호(iorder) - 0</li>\n" +
                    "<li>상품 번호(일련 번호, iproduct) - 1</li>\n" +
                    "<li>회원 아이디 - 2</li>\n" +
                    "<li>주문자명 - 3</li>\n" +
                    "<li>입금자명 - 4</li>\n" +
                    "<li>수령자명 - 5</li>\n" +
                    "<li>수령자 핸드폰 번호 - 6</li></ul>\n" +
                    "<ul><strong>keyword - 검색 키워드</strong></ul>\n" +
                    "<ul><strong>startDate - (예) 2024-02-22</strong><br></ul>\n" +
                    "<ul><strong>lastDate - (예) 2024-02-22</strong><br></ul>\n" +
                    "<ul><strong>dateFl</strong><br>\n" +
                    "<li>오늘 - 0</li>\n" +
                    "<li>어제 - 1</li>\n" +
                    "<li>일주일 - 2</li>\n" +
                    "<li>지난 달 - 3</li>\n" +
                    "<li>1개월 - 4</li>\n" +
                    "<li>3개월 - 5</li>\n" +
                    "<li>전체 - 6</li></ul>\n" +
                    "<ul><strong>payCategory</strong><br>\n" +
                    "<li>전체 - 0</li>\n" +
                    "<li>무통장 입금 - 1</li>\n" +
                    "<li>신용카드 - 2</li></ul>\n" +
                    "<ul><strong>sort</strong><br>\n" +
                    "<li>주문일 역순 - 0</li>\n" +
                    "<li>주문일 순 - 1</li>\n" +
                    "<li>처리일 역순 - 2</li>\n" +
                    "<li>처리일 순 - 3</li></ul>\n" +
                    "\n")
    public OrderDeleteVo orderDeleteList(@RequestBody OrderSmallFilterDto dto) {
        log.info("dto = {}", dto);
        return service.orderDeleteList(dto);
    }

    @GetMapping("/refund")
    @Operation(summary = "주문 반품 리스트 출력",
            description = "<ul><strong>searchCategory</strong><br>" +
                    "<li>주문 번호(iorder) - 0</li>\n" +
                    "<li>상품 번호(일련 번호, iproduct) - 1</li>\n" +
                    "<li>회원 아이디 - 2</li>\n" +
                    "<li>주문자명 - 3</li>\n" +
                    "<li>입금자명 - 4</li>\n" +
                    "<li>수령자명 - 5</li>\n" +
                    "<li>수령자 핸드폰 번호 - 6</li></ul>\n" +
                    "<ul><strong>keyword - 검색 키워드</strong></ul>\n" +
                    "<ul><strong>startDate - (예) 2024-02-22</strong><br></ul>\n" +
                    "<ul><strong>lastDate - (예) 2024-02-22</strong><br></ul>\n" +
                    "<ul><strong>dateFl</strong><br>\n" +
                    "<li>오늘 - 0</li>\n" +
                    "<li>어제 - 1</li>\n" +
                    "<li>일주일 - 2</li>\n" +
                    "<li>지난 달 - 3</li>\n" +
                    "<li>1개월 - 4</li>\n" +
                    "<li>3개월 - 5</li>\n" +
                    "<li>전체 - 6</li></ul>\n" +
                    "<ul><strong>payCategory</strong><br>\n" +
                    "<li>전체 - 0</li>\n" +
                    "<li>무통장 입금 - 1</li>\n" +
                    "<li>신용카드 - 2</li></ul>\n" +
                    "<ul><strong>sort</strong><br>\n" +
                    "<li>주문일 역순 - 0</li>\n" +
                    "<li>주문일 순 - 1</li>\n" +
                    "<li>처리일 역순 - 2</li>\n" +
                    "<li>처리일 순 - 3</li></ul>\n" +
                    "\n")
    public OrderRefundListVo orderRefundList(@RequestBody OrderSmallFilterDto dto) {
        log.info("dto = {}", dto);
        return service.orderRefundList(dto);
    }

    @GetMapping("/memo")
    @Operation(summary = "주문 리스트 관리자 메모 출력",
            description = "<ul><strong>searchCategory</strong><br>" +
                    "<li>주문 번호(iorder) - 0</li>\n" +
                    "<li>상품 번호(일련 번호, iproduct) - 1</li>\n" +
                    "<li>회원 아이디 - 2</li>\n" +
                    "<li>주문자명 - 3</li>\n" +
                    "<li>입금자명 - 4</li>\n" +
                    "<li>수령자명 - 5</li>\n" +
                    "<li>수령자 핸드폰 번호 - 6</li></ul>\n" +
                    "<ul><strong>keyword - 검색 키워드</strong></ul>\n" +
                    "<ul><strong>startDate - (예) 2024-02-22</strong><br></ul>\n" +
                    "<ul><strong>lastDate - (예) 2024-02-22</strong><br></ul>\n" +
                    "<ul><strong>dateFl</strong><br>\n" +
                    "<li>오늘 - 0</li>\n" +
                    "<li>어제 - 1</li>\n" +
                    "<li>일주일 - 2</li>\n" +
                    "<li>지난 달 - 3</li>\n" +
                    "<li>1개월 - 4</li>\n" +
                    "<li>3개월 - 5</li>\n" +
                    "<li>전체 - 6</li></ul>\n" +
                    "<ul><strong>processState</strong><br>\n" +
                    "<li>전체 - 0</li>\n" +
                    "<li>어제 - 1</li>\n" +
                    "<li>일주일 - 2</li>\n" +
                    "<li>지난 달 - 3</li>\n" +
                    "<li>1개월 - 4</li>\n" +
                    "<li>3개월 - 5</li>\n" +
                    "<li>전체 - 6</li></ul>\n" +
                    "<ul><strong>sort</strong><br>\n" +
                    "<li>주문일 역순 - 0</li>\n" +
                    "<li>주문일 순 - 1</li>\n" +
                    "<li>처리일 역순 - 2</li>\n" +
                    "<li>처리일 순 - 3</li></ul>\n" +
                    "\n")
    public OrderMemoListVo adminMemo(@RequestBody OrderMemoListDto dto) {
        log.info("dto = {}", dto);
        return service.adminMemo(dto);
    }
}
