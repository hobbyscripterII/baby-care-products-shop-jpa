package com.baby.babycareproductsshop.admin.product;

import com.baby.babycareproductsshop.admin.product.model.*;
import com.baby.babycareproductsshop.admin.product.model.ProductInsDto;
import com.baby.babycareproductsshop.common.ResVo;
import com.baby.babycareproductsshop.entity.product.BannerEntity;
import io.swagger.v3.oas.annotations.Operation;
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
public class AdminProductController {
    private final AdminProductService service;

    // 주문총수가격
    @GetMapping("/Total Price & count")
    @Operation(summary = "주문 총 가격 & 수")
    public OrderTotalSelVo getOrderTotal() {
        return service.getTotalPriceAndCount();
    }

    // 최근주무자
    @GetMapping("recentOrders")
    @Operation(summary = "최근 주문내역")
    public List<OrderRecentSelVo> getRecentOrders() {

        return service.getRecentOrders();
    }

    // 유저가입최근
    @GetMapping("recentUsers")
    @Operation(summary = "최근 가입유저")
    public List<ddddMapping> getRecentUser() {
        return service.getRecentUser();
    }
    //--------------------주문상태현황

    @GetMapping("OrderStatus")
    @Operation(summary = "주문상태현황")
    public List<OrderStatusCountVo> getOrderStatusCount() {
        return service.getOrderStatusCount();
    }

//    @GetMapping("dfdzzzzzzz5")
//    public Map<String, Long> countRefundAndCancel() {
//        return service.countRefundAndCancel();
//    }

    //상품삭제
//    @DeleteMapping("dledledle")
//    public ResVo updateProductPrice(@PathVariable Long iproduct, @RequestBody Long newPrice) {
//        return p.updateProductPrice(newPrice, iproduct);
//    }





    //------------상품진열관리 추천상품
    @GetMapping("/productRc")
    @Operation(summary = "진열관리 추천상품")
    public List<Product2141234Vo> getProductRc() {
        return service.getProductRc();
    }
    // 상품진열관리 신상품

    @GetMapping("/productNew")
    @Operation(summary = "진열관리 신상품")
    public List<Product2141234Vo> getProductNew() {
        return service.getProductNew();
    }

    // 인기상품
    @GetMapping("/productPop")
    @Operation(summary = "진열관리 인기상품")
    public List<Product2141234Vo> getProductPop() {
        return service.getProductPop();
    }
    // 배너출력
    @GetMapping("banner")
    @Operation(summary = "배너출력")
    public List<BannerEntity> getBanner() {
        return service.getBanner();
    }

    //-----------------배너넣
    @PostMapping("/banner")
    @Operation(summary = "배너등록")
    public ResVo postBanner(@RequestPart MultipartFile pics, @RequestPart BannerInsDto dto) {
        return service.postBanner(pics, dto);
    }

    // ----------배너 수정
    @PatchMapping("/banner")
    @Operation(summary = "배너수정")
    public ResVo updateBanner(@RequestParam Long id, @RequestPart(required = false) MultipartFile pic, @RequestPart BannerInsDto dto) {
        return service.updateBanner(id, pic, dto);
    }
    //배너 삭제
    @DeleteMapping("/banner")
    @Operation(summary = "배너삭제")
    public ResVo delbanner(@RequestBody Long ibanner) {
        return service.delBanner(ibanner);
    }


    // 주인장 리뷰 검색
//    @GetMapping("/searchRRR")
//    public List<SearchReviewSelVo> getSearchReview( ReviewDto dto) {
//        log.info("dto : {}" ,dto);
//        return service.getSearchReview(dto);
//    }
    //상품 등록
    @PostMapping("/Product")
    @Operation(summary = "상품등록 아직안댐 ㅠ")
    public ResVo postProduct(@RequestPart(name = "pics") List<MultipartFile> pics,
                             @RequestPart(name = "productDetails") MultipartFile productDetails,
                             @RequestPart @Valid ProductInsDto dto) {
        return service.postProduct(pics, productDetails, dto);
    }
}




