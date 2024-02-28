package com.baby.babycareproductsshop.admin.product;
import com.baby.babycareproductsshop.admin.product.model.*;
import com.baby.babycareproductsshop.common.ResVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
@Tag(name = "관리자 기능 - 상품 관련 API")
public class AdminProductController {
    private final AdminProductService service;

    //    // 주문총수가격
//    @GetMapping("/Total Price & count")
//    @Operation(summary = "주문 총 가격 & 수 dkdhdkhddkdkdkdk")
//    public OrderTotalSelVo getOrderTotal() {
//        return service.getTotalPriceAndCount();
//    }
//    // 최근주무자
//    @GetMapping("recentOrders")
//    @Operation(summary = "최근 주문내역dkdhdkhddkdkdkdk")
//    public List<OrderRecentSelVo> getRecentOrders() {
//
//        return service.getRecentOrders();
//    }
//    // 유저가입최근
//    @GetMapping("recentUsers")
//    @Operation(summary = "최근 가입유저dkdhdkhddkdkdkdk")
//    public List<ddddMapping> getRecentUser() {
//        return service.getRecentUser();
//    }
//    //--------------------주문상태현황
//    @GetMapping("OrderStatus")
//    @Operation(summary = "주문상태현황dkdhdkhddkdkdkdk")
//    public List<OrderStatusCountVo> getOrderStatusCount() {
//        return service.getOrderStatusCount();
//    }
//    @GetMapping("OrderCancelCount")
//    @Operation(summary = "반품/취소 수dkdhdkhddkdkdkdk")
//    public OrderRefundAndCancelCountSelVo getCountRefundAndCancel() {
//        return service.getCountRefundAndCancel();
//    }


    //-----------------------------------------------------------------상품 등록------------------------------------------------------
    @PostMapping("/AdminProduct")
    @Operation(summary = "상품등록 ")
    public ResVo postProduct(@RequestPart(name = "pics") List<MultipartFile> pics,
                             @RequestPart(name = "productDetails") MultipartFile productDetails,
                             @RequestPart @Valid AdminProductInsDto dto) {
        log.info("dto : {}", dto);
        return service.postProduct(pics, productDetails, dto);
    }
    //-----------------------------------------------------------------상품 수정------------------------------------------------------
    @PatchMapping("/AdminProductUpt")
    @Operation(summary = "상품 수정 ")
    public ResVo uptProduct (@RequestPart(name = "pics") List<MultipartFile> pics,
                             @RequestPart(name = "productDetails") MultipartFile productDetails,
                             @RequestPart @Valid AdminProductInsDto dto,@RequestParam Long iproduct) {
        return service.putProduct(pics,productDetails,dto,iproduct);
    }

    //-----------------------------------------------------------------상품 삭제------------------------------------------------------
    @DeleteMapping("/AdminProductDel")
    @Operation(summary = "상품삭제 ")
    public ResVo delProduct( @RequestParam Long iproduct) {
        return service.delProduct(iproduct);
    }

    @GetMapping("/AdminProductSearch")
    @Operation(summary = "상품검색 ")
    public List<ProductGetSearchSelVo> getSearchProductSelVo(ProductGetSearchDto dto) {
        return service.getSearchProductSelVo(dto);
    }



    //-----------------------------------------------------------------상품진열관리 추천상품 조회------------------------------------------------------
    @GetMapping("/productRc")
    @Operation(summary = "진열관리 추천상품 조회")
    public List<ProductManagementSelVo> getProductRc() {
        return service.getProductRc();
    }
    //-----------------------------------------------------------------상품진열관리 신상품 조회------------------------------------------------------
    @GetMapping("/productNew")
    @Operation(summary = "진열관리 신상품 조회")
    public List<ProductManagementSelVo> getProductNew() {
        return service.getProductNew();
    }
    //-----------------------------------------------------------------상품진열관리 인기상품 조회------------------------------------------------------
    @GetMapping("/productPop")
    @Operation(summary = "진열관리 인기상품 조회")
    public List<ProductManagementSelVo> getProductPop() {
        return service.getProductPop();
    }
    //------------------------------------------상품진열관리 신상품 토글---------------------------------------------
    @PutMapping("/productNewDel")
    @Operation(summary = "진열관리 신상품 토글")
    public ResVo putProductNew(@RequestParam Long iproduct) {
        return service.putProductNew(iproduct);
    }
    //------------------------------------------상품진열관리 인기상품 토글---------------------------------------------
    @PutMapping("/productPopDel")
    @Operation(summary = "진열관리 인기상품 토글")
    public ResVo putProductPop(@RequestParam Long iproduct) {
        return service.putProductPop(iproduct);
    }
    //------------------------------------------상품진열관리 추천상품 토글---------------------------------------------
    @PutMapping("/productRcDel")
    @Operation(summary = "진열관리 추천상품 토글")
    public ResVo putProductRc(@RequestParam Long iproduct) {
        return service.putProductRc(iproduct);
    }
    //-----------------------진열관리 상품검색---------------
    @GetMapping("/ProductManagementSearch")
    @Operation(summary = "진열관리 상품검색")
    public List<AdminProductSearchSelVo> getSearchProduct(AdminProductSearchDto dto ) {
        return service.getSearchProduct(dto);
    }



    //-----------------------------------------------------------------리뷰검색------------------------------------------------------
    @GetMapping("/AdminSearchReview")
    @Operation(summary = "리뷰 검색")
    public List<SearchReviewSelVo> getSearchReview( ReviewSearchDto dto) {
        return service.getSearchReview(dto);
    }
    //-------------------------------------------------------------숨김리뷰검색------------------------------------------------------
    @GetMapping("/AdminSearchHiddenReview")
    @Operation(summary = "숨김리뷰조회")
    public List<SearchReviewSelVo> getHiddenReviewSelVo(ReviewSearchDto dto) {
        return service.getHiddenReview(dto);
    }
    //-------------------------------------------------------------리뷰 관리자 메모 ------------------------------------------------------
    @PatchMapping("/AdminInsReviewMemo")
    @Operation(summary = "관리자 메모 작성이랑 수정 둘다댐")
    public ResVo patchReviewAdminMemo(@RequestBody ReviewMemoInsDto dto) {
        return service.postReviewAdminMemo(dto);
    }

    //----------------------------------------------------------리뷰 숨김 복구 토글*-------------------------------------------------
    @PutMapping("/AdminReviewTogle")
    @Operation(summary = "리뷰 숨김&복구 토글")
    public ResVo putReviewTogle(Long ireview) {
        return service.putReviewTogle(ireview);
    }
    //-----------------------------------관리자 리뷰 관리 클릭할때-------------------------
    @GetMapping("/AdminHiCkReview")
    @Operation(summary = "리뷰 숨김클릭시")
    public List<ReviewHideClickSelVo> getHiCkSelVo (Long ireview) {
        return service.getHiCkSelVo(ireview);
    }
    //-----------------------------------------------------------------------------
    @GetMapping("/AdminReviewMemo")
    @Operation(summary = "리뷰관리자메모")
    public String getReviewMemo(Long ireview) {
        return service.getReviewMeMo(ireview);
    }


    //-----------------------------------------------------------------배너조회------------------------------------------------------
    @GetMapping("/banner")
    @Operation(summary = "배너조회")
    public List<BannerSelVo> getBanner() {
        return service.getBanner();
    }
    //-----------------------------------------------------------------배너등록------------------------------------------------------
    @PostMapping("/banner")
    @Operation(summary = "배너등록")
    public ResVo postBanner(@RequestPart MultipartFile pics, @RequestPart BannerInsDto dto) {
        return service.postBanner(pics, dto);
    }
    //-----------------------------------------------------------------배너수정------------------------------------------------------
    @PatchMapping("/banner")
    @Operation(summary = "배너수정")
    public ResVo updateBanner(@RequestParam Long id, @RequestPart(required = false) MultipartFile pic, @RequestPart BannerInsDto dto) {
        return service.updateBanner(id, pic, dto);
    }
    //-----------------------------------------------------------------배너삭제------------------------------------------------------
    @DeleteMapping("/banner")
    @Operation(summary = "배너삭제")
    public ResVo delbanner(@RequestBody Long ibanner) {
        return service.delBanner(ibanner);
    }
    //-----------------------------------------------------------------배너토글처리----------------------



}





