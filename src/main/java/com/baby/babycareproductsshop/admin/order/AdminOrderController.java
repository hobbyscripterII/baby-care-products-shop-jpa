package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.*;
import com.baby.babycareproductsshop.common.ResVo;
import com.baby.babycareproductsshop.exception.AuthErrorCode;
import com.baby.babycareproductsshop.exception.RestApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/api/admin/order")
@RequiredArgsConstructor
@Tag(name = "관리자 기능 - 주문 관리 API❤️")
public class AdminOrderController {
    private final AdminOrderService service;

//    @PageableDefault(page = 1, size = 20) Pageable pageable

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
    public List<OrderListVo> orderList(OrderFilterDto dto, @Parameter(hidden = true) @PageableDefault(page = 1, size = 20) Pageable pageable) {
        stringIsNullCheck(dto);
        return service.orderList(dto, pageable);
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
    public List<OrderDetailsListVo> orderDetailsList(@RequestParam(name = "process_state") int processState, OrderSmallFilterDto dto, @PageableDefault(page = 1, size = 20) Pageable pageable) {
        stringIsNullCheck(dto);
        dto.setProcessState(processState);

        if (dto.getSearchCategory() == 0) {
            dto.setKeyword(null);
            return service.orderDetailsList(dto, pageable);
        } else {
            // 검색어 타입 체크
            if (searchDataTypeCheck(dto.getSearchCategory(), dto.getKeyword())) {
                // 휴대폰 번호로 검색 시 휴대폰 번호 유효성 체크
                if (dto.getSearchCategory() == 6) {
                    String formatPhoneNumber = phoneNumberFormatConverter(dto.getKeyword());
                    dto.setKeyword(formatPhoneNumber);
                }
                return service.orderDetailsList(dto, pageable);
            }
        }
        throw new RestApiException(AuthErrorCode.PROCESS_STATE_CODE_NOT_FOUND);
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
    public List<OrderDeleteVo> orderDeleteList(OrderSmallFilterDto dto, @PageableDefault(page = 1, size = 20) Pageable pageable) {
        stringIsNullCheck(dto);
        return service.orderDeleteList(dto, pageable);
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
    public List<OrderRefundListVo> orderRefundList(OrderSmallFilterDto dto, @PageableDefault(page = 1, size = 20) Pageable pageable) {
        stringIsNullCheck(dto);
        return service.orderRefundList(dto, pageable);
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
    public List<OrderMemoListVo> adminMemoList(OrderMemoListDto dto, @PageableDefault(page = 1, size = 20) Pageable pageable) {
        stringIsNullCheck(dto);
        return service.adminMemoList(dto, pageable);
    }

    @Operation(summary = "주문 상세 페이지 출력")
    @Parameters(value = {@Parameter(name = "iorder", description = "주문 PK")})
    @GetMapping("/details/{iorder}")
    public List<OrderDetailsVo> orderDetails(@PathVariable(name = "iorder") int iorder) {
        return service.orderDetails(iorder);
    }

    private String phoneNumberFormatConverter(String phoneNumber) {
        String formatPhoneNumber = null;
        String pattern = "^\\d{3}-\\d{3,4}-\\d{4}$";
        // 휴대폰 번호 확인
        if (phoneNumber.length() == 11 || phoneNumber.length() == 13 && phoneNumber.startsWith("010") || phoneNumber.startsWith("011")) {
            if (phoneNumber.length() == 11) {
                formatPhoneNumber =
                        phoneNumber.substring(0, 3) + "-" +
                                phoneNumber.substring(3, 7) + "-" +
                                phoneNumber.substring(7);
            } else {
                if (Pattern.matches(pattern, phoneNumber)) {
                    return phoneNumber;
                } else {
                    throw new RestApiException(AuthErrorCode.SEARCH_FAILED_ERROR);
                }
            }
        } else {
            throw new RestApiException(AuthErrorCode.SEARCH_FAILED_ERROR);
        }
        return formatPhoneNumber;
    }

    private boolean searchDataTypeCheck(int searchCategory, String keyword) {
        boolean result = false;

        try {
            switch (searchCategory) {
                case 1, 2 -> {
                    Integer.valueOf(keyword);
                    return true;
                }
                case 3, 4, 5, 6 -> {
                    return !keyword.isBlank();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestApiException(AuthErrorCode.SEARCH_FAILED_ERROR);
        }
        return result;
    }

    private String stringIsNull(String str) {
        return str.equals("string") ? "" : str;
    }

    private void stringIsNullCheck(OrderFilterDto dto) {
        dto.setKeyword(stringIsNull(dto.getKeyword()));
        dto.setStartDate(stringIsNull(dto.getStartDate()));
        dto.setEndDate(stringIsNull(dto.getEndDate()));
    }

    private void stringIsNullCheck(OrderSmallFilterDto dto) {
        dto.setKeyword(stringIsNull(dto.getKeyword()));
        dto.setStartDate(stringIsNull(dto.getStartDate()));
        dto.setEndDate(stringIsNull(dto.getEndDate()));
    }

    private void stringIsNullCheck(OrderMemoListDto dto) {
        dto.setKeyword(stringIsNull(dto.getKeyword()));
        dto.setStartDate(stringIsNull(dto.getStartDate()));
        dto.setEndDate(stringIsNull(dto.getEndDate()));
    }
}
