package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.*;
import com.baby.babycareproductsshop.common.ResVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.baby.babycareproductsshop.common.Const.PAGE_SIZE;

@Slf4j
@RestController
@RequestMapping("/api/admin/order")
@RequiredArgsConstructor
@Tag(name = "관리자 기능 - 주문 관리 API❤️")
public class AdminOrderController {
    private final AdminOrderService service;
    private final AdminOrderCommonValid valid;

    public Pageable pageable(int page) {
        return PageRequest.of(page, PAGE_SIZE);
    }

    @Operation(summary = "관리자 메모 수정", description = "<ul><strong>iorders - 주문 번호(PK)</strong></ul> <ul><strong>adminMemo - 관리자 메모</strong><br></ul>")
    @PatchMapping("/memo")
    public ResVo updateAdminMemo(@RequestBody OrderMemoUpdDto dto) {
        return service.updateAdminMemo(dto);
    }

    @PutMapping
    @Operation(summary = "주문 일괄 처리",
            description = """
                    <ul><strong>iorders - 주문 번호(PK)</strong></ul>
                    <ul><strong>processState - 주문 처리 상태</strong><br>
                    <li>전체 - 0</li>
                    <li>입금 대기 - 1</li>
                    <li>배송 준비중 - 2</li>
                    <li>배송중 - 3</li>
                    <li>배송완료 - 4</li>
                    <li>취소 - 5</li>
                    <li>반품 - 6</li></ul>""")
    public ResVo orderBatchProcess(@RequestBody OrderBatchProcessDto dto) {
        return service.orderBatchProcess(dto);
    }

    @GetMapping
    @Operation(summary = "주문 전체 리스트 출력", description = """
            <ul><strong>searchCategory</strong><br>
            <li>전체 - 0</li>
            <li>주문 번호(iorder) - 1</li>
            <li>상품 번호(일련 번호, iproduct) - 2</li>
            <li>회원 아이디 - 3</li>
            <li>주문자명 - 4</li>
            <li>입금자명 - 5</li>
            <li>수령자명 - 6</li>
            <li>수령자 핸드폰 번호 - 7</li></ul>
            <ul><strong>keyword - 검색 키워드</strong></ul>
            <ul><strong>startDate - (예) 2024-02-22</strong><br></ul>
            <ul><strong>endDate - (예) 2024-02-22</strong><br></ul>
            <ul><strong>dateFl</strong><br>
            <li>오늘 - 0</li>
            <li>어제 - 1</li>
            <li>일주일 - 2</li>
            <li>지난 달 - 3</li>
            <li>1개월 - 4</li>
            <li>3개월 - 5</li>
            <li>전체 - 6</li></ul>
            <ul><strong>payCategory</strong><br>
            <li>전체 - 0</li>
            <li>무통장 입금 - 1</li>
            <li>신용카드 - 2</li></ul>
            <ul><strong>processState</strong><br>
            <li>전체 - 0</li>
            <li>입금 대기 - 1</li>
            <li>배송 준비중 - 2</li>
            <li>배송중 - 3</li>
            <li>배송완료 - 4</li></ul>
            <ul><strong>sort</strong><br>
            <li>주문일 역순 - 0</li>
            <li>주문일 순 - 1</li>
            <li>처리일 역순 - 2</li>
            <li>처리일 순 - 3</li></ul>""")
    public List<OrderListVo> orderList(OrderFilterDto dto) {
        OrderFilterDto orderFilterDto = valid.commonValid(dto);
        return service.orderList(orderFilterDto, pageable(orderFilterDto.getPage()));
    }

    @GetMapping("/details")
    @Operation(summary = "주문 상세 리스트 출력", description = """
            <ul><strong>searchCategory</strong><br>
            <li>전체 - 0</li>
            <li>주문 번호(iorder) - 1</li>
            <li>상품 번호(일련 번호, iproduct) - 2</li>
            <li>회원 아이디 - 3</li>
            <li>주문자명 - 4</li>
            <li>입금자명 - 5</li>
            <li>수령자명 - 6</li>
            <li>수령자 핸드폰 번호 - 7</li></ul>
            <ul><strong>keyword - 검색 키워드</strong></ul>
            <ul><strong>startDate - (예) 2024-02-22</strong><br></ul>
            <ul><strong>endDate - (예) 2024-02-22</strong><br></ul>
            <ul><strong>dateFl - 기간 선택</strong><br>
            <li>전체 - 0</li>
            <li>오늘 - 1</li>
            <li>어제 - 2</li>
            <li>일주일 - 3</li>
            <li>지난 달, 1개월 - 4, 5</li>
            <li>3개월 - 6</li></ul>
            <ul><strong>payCategory - 결제 수단</strong><br>
            <li>전체 - 0</li>
            <li>무통장 입금 - 1</li>
            <li>신용카드 - 2</li></ul>
            <ul><strong>sort</strong><br>
            <li>주문일 역순 - 0</li>
            <li>주문일 순 - 1</li>
            <li>처리일 역순 - 2</li>
            <li>처리일 순 - 3</li></ul>""")
    @Parameters(value = {@Parameter(name = "process_state", description = "주문 처리 상태<br>전체 - 0<br>입금 대기 - 1<br>배송 준비중 - 2<br>배송중 - 3<br>배송완료 - 4<br>")})
    public List<OrderDetailsListVo> orderDetailsList(@RequestParam(name = "process_state") int processState, OrderSmallFilterDto dto) {
        dto.setProcessState(processState);
        OrderSmallFilterDto orderSmallFilterDto = valid.commonValid(dto);
        return service.orderDetailsList(orderSmallFilterDto, pageable(orderSmallFilterDto.getPage()));
    }

    @GetMapping("/delete")
    @Operation(summary = "주문 취소 리스트 출력", description = """
            <ul><strong>searchCategory</strong><br>
            <li>전체 - 0</li>
            <li>주문 번호(iorder) - 1</li>
            <li>상품 번호(일련 번호, iproduct) - 2</li>
            <li>회원 아이디 - 3</li>
            <li>주문자명 - 4</li>
            <li>입금자명 - 5</li>
            <li>수령자명 - 6</li>
            <li>수령자 핸드폰 번호 - 7</li></ul>
            <ul><strong>keyword - 검색 키워드</strong></ul>
            <ul><strong>startDate - (예) 2024-02-22</strong><br></ul>
            <ul><strong>endDate - (예) 2024-02-22</strong><br></ul>
            <ul><strong>dateFl</strong><br>
            <li>오늘 - 0</li>
            <li>어제 - 1</li>
            <li>일주일 - 2</li>
            <li>지난 달 - 3</li>
            <li>1개월 - 4</li>
            <li>3개월 - 5</li>
            <li>전체 - 6</li></ul>
            <ul><strong>payCategory</strong><br>
            <li>전체 - 0</li>
            <li>무통장 입금 - 1</li>
            <li>신용카드 - 2</li></ul>
            <ul><strong>sort</strong><br>
            <li>주문일 역순 - 0</li>
            <li>주문일 순 - 1</li>
            <li>처리일 역순 - 2</li>
            <li>처리일 순 - 3</li></ul>""")
    public List<OrderDeleteVo> orderDeleteList(OrderSmallFilterDto dto) {
        OrderSmallFilterDto orderSmallFilterDto = valid.commonValid(dto);
        return service.orderDeleteList(orderSmallFilterDto, pageable(orderSmallFilterDto.getPage()));
    }

    @GetMapping("/refund")
    @Operation(summary = "주문 반품 리스트 출력", description = """
            <ul><strong>searchCategory</strong><br>
            <li>전체 - 0</li>
            <li>주문 번호(iorder) - 1</li>
            <li>상품 번호(일련 번호, iproduct) - 2</li>
            <li>회원 아이디 - 3</li>
            <li>주문자명 - 4</li>
            <li>입금자명 - 5</li>
            <li>수령자명 - 6</li>
            <li>수령자 핸드폰 번호 - 7</li></ul>
            <ul><strong>keyword - 검색 키워드</strong></ul>
            <ul><strong>startDate - (예) 2024-02-22</strong><br></ul>
            <ul><strong>endDate - (예) 2024-02-22</strong><br></ul>
            <ul><strong>dateFl</strong><br>
            <li>오늘 - 0</li>
            <li>어제 - 1</li>
            <li>일주일 - 2</li>
            <li>지난 달 - 3</li>
            <li>1개월 - 4</li>
            <li>3개월 - 5</li>
            <li>전체 - 6</li></ul>
            <ul><strong>payCategory</strong><br>
            <li>전체 - 0</li>
            <li>무통장 입금 - 1</li>
            <li>신용카드 - 2</li></ul>
            <ul><strong>sort</strong><br>
            <li>주문일 역순 - 0</li>
            <li>주문일 순 - 1</li>
            <li>처리일 역순 - 2</li>
            <li>처리일 순 - 3</li></ul>""")
    public List<OrderRefundListVo> orderRefundList(OrderSmallFilterDto dto) {
        OrderSmallFilterDto orderSmallFilterDto = valid.commonValid(dto);
        return service.orderRefundList(orderSmallFilterDto, pageable(orderSmallFilterDto.getPage()));
    }

    @GetMapping("/memo")
    @Operation(summary = "주문 리스트 관리자 메모 출력", description = """
            <ul><strong>searchCategory</strong><br>
            <li>전체 - 0</li>
            <li>주문 번호(iorder) - 1</li>
            <li>상품 번호(일련 번호, iproduct) - 2</li>
            <li>회원 아이디 - 3</li>
            <li>주문자명 - 4</li>
            <li>입금자명 - 5</li>
            <li>수령자명 - 6</li>
            <li>수령자 핸드폰 번호 - 7</li></ul>
            <ul><strong>keyword - 검색 키워드</strong></ul>
            <ul><strong>startDate - (예) 2024-02-22</strong><br></ul>
            <ul><strong>endDate - (예) 2024-02-22</strong><br></ul>
            <ul><strong>dateFl</strong><br>
            <li>오늘 - 0</li>
            <li>어제 - 1</li>
            <li>일주일 - 2</li>
            <li>지난 달 - 3</li>
            <li>1개월 - 4</li>
            <li>3개월 - 5</li>
            <li>전체 - 6</li></ul>
            <ul><strong>sort</strong><br>
            <li>주문일 역순 - 0</li>
            <li>주문일 순 - 1</li>
            <li>처리일 역순 - 2</li>
            <li>처리일 순 - 3</li></ul>""")
    public List<OrderMemoListVo> adminMemoList(OrderMemoListDto dto) {
        OrderMemoListDto orderMemoListDto = valid.commonValid(dto);
        return service.adminMemoList(orderMemoListDto, pageable(orderMemoListDto.getPage()));
    }

    @Operation(summary = "주문 상세 페이지 출력")
    @Parameters(value = {@Parameter(name = "iorder", description = "주문 PK")})
    @GetMapping("/details/{iorder}")
    public List<OrderDetailsVo> orderDetails(@PathVariable(name = "iorder") int iorder) {
        return service.orderDetails(iorder);
    }
}