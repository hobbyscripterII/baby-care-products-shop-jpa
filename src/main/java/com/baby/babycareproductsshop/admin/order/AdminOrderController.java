package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.*;
import com.baby.babycareproductsshop.common.ResVo;
import com.baby.babycareproductsshop.exception.AuthErrorCode;
import com.baby.babycareproductsshop.exception.RestApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

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
                    "<li>입금 대기 - 1</li>\n" +
                    "<li>배송 준비중 - 2</li>\n" +
                    "<li>배송중 - 3</li>\n" +
                    "<li>배송완료 - 4</li>\n" +
                    "<li>취소 - 5</li>\n" +
                    "<li>반품 - 6</li></ul>\n")
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
                    "<li>입금 대기 - 1</li>\n" +
                    "<li>배송 준비중 - 2</li>\n" +
                    "<li>배송중 - 3</li>\n" +
                    "<li>배송완료 - 4</li></ul>\n" +
                    "<ul><strong>sort</strong><br>\n" +
                    "<li>주문일 역순 - 0</li>\n" +
                    "<li>주문일 순 - 1</li>\n" +
                    "<li>처리일 역순 - 2</li>\n" +
                    "<li>처리일 순 - 3</li></ul>\n" +
                    "\n")
    public List<OrderListVo> orderList(OrderFilterDto dto) {
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
    public List<OrderDetailsListVo> orderDetailsList(@RequestBody OrderSmallFilterDto dto) {
        log.info("dto = {}", dto);

        // 검색어 타입 체크
        if (searchDataTypeCheck(dto.getSearchCategory(), dto.getKeyword())) {
            // 휴대폰 번호로 검색 시 휴대폰 번호 유효성 체크
            if (dto.getSearchCategory() == 6) {
                String formatPhoneNumber = phoneNumberFormatConverter(dto.getKeyword());
                dto.setKeyword(formatPhoneNumber);
            }
            return service.orderDetailsList(dto);
        }
        throw new RestApiException(AuthErrorCode.SEARCH_FAILED_ERROR);
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
    public List<OrderDeleteVo> orderDeleteList(@RequestBody OrderSmallFilterDto dto) {
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
    public List<OrderRefundListVo> orderRefundList(@RequestBody OrderSmallFilterDto dto) {
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
    public List<OrderMemoListVo> adminMemo(@RequestBody OrderMemoListDto dto) {
        log.info("dto = {}", dto);
        return service.adminMemoList(dto);
    }

    private String phoneNumberFormatConverter(String phoneNumber) {
        String formatPhoneNumber = null;
        // 휴대폰 번호 확인
        if (phoneNumber.length() == 11 || phoneNumber.length() == 13 && phoneNumber.startsWith("010") || phoneNumber.startsWith("011")) {
            if (phoneNumber.length() == 11) {
                formatPhoneNumber =
                        phoneNumber.substring(0, 3) + "-" +
                                phoneNumber.substring(3, 7) + "-" +
                                phoneNumber.substring(7);
            } else {
                String pattern = "^\\d{3}-\\d{3,4}-\\d{4}$";
                boolean result = Pattern.matches(pattern, phoneNumber);
                log.info("phoneNumber = {} result = {}", phoneNumber, result);
                if (result) {
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

        switch (searchCategory) {
            case 0:
                return true;
            case 1:
                Integer temp = Integer.valueOf(keyword);
                if (temp instanceof Integer) {
                    result = true;
                } else {
                    result = false;
                }
            case 2, 3, 4, 5, 6:
                result = keyword != null;
                break;
        }
        log.info("result = {}", result);
        return result;
    }
}
