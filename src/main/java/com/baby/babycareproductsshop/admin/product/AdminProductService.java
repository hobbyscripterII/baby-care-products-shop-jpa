package com.baby.babycareproductsshop.admin.product;


import com.baby.babycareproductsshop.admin.product.model.*;
import com.baby.babycareproductsshop.admin.product.model.Repository_ys.*;
import com.baby.babycareproductsshop.common.Const;
import com.baby.babycareproductsshop.common.MyFileUtils;
import com.baby.babycareproductsshop.common.ResVo;
import com.baby.babycareproductsshop.entity.product.*;

import com.baby.babycareproductsshop.entity.review.ReviewEntity;
import com.baby.babycareproductsshop.security.AuthenticationFacade;
;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminProductService {
    private final AdminProductMapper mapper;
    private final AuthenticationFacade authenticationFacade;

    //private final JPAQueryFactory query;

    private final MyFileUtils myFileUtils;
    private final ProductPicRepository productPicRepository;
    private final MainCategoryRepository mainCategoryRepository;
    private final MiddleCategoryRepository middleCategoryRepository;
    private final OrderTotalRepository orderTotalRepository;
    private final ProductRepository productRepository;
    private final BannerRepository bannerRepository;
    private final UserRepository2 userRepository2;
    private final OrderDetailsRepository orderDetailsRepository;
    private final ReviewRepository reviewRepository;



    //상품진열관리 검색
    public List<AdminProductSearchSelVo> getSearchProduct(AdminProductSearchDto dto) {
        List<AdminProductSearchSelVo> vo = productRepository.selProductAll(dto);
        return vo;
    }

    //------------상품진열관리 추천상품 조회
    public List<ProductManagementSelVo> getProductRc() {
        return productRepository.findAllByRcFl(1);
    }

    //------------상품진열관리 신상품 조회
    public List<ProductManagementSelVo> getProductNew() {
        return productRepository.findAllByNewFl(1);
    }

    //------------상품진열관리 인기상품 조회
    public List<ProductManagementSelVo> getProductPop() {
        return productRepository.findAllByPopFl(1);
    }

    //------------상품진열관리 신상품 토글
    public ResVo putProductNew(Long iproduct) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(iproduct);
        if (productEntityOptional.isPresent()) {
            ProductEntity entity = productEntityOptional.get();
            entity.setNewFl(entity.getNewFl() == 0 ? 1 : 0);
            productRepository.save(entity);
            return new ResVo(entity.getNewFl() == 0 ? Const.SUCCESS : Const.FAIL);
        } else {
            return new ResVo(Const.FAIL);  // 상품이 없는 경우
        }
    }
    //------------상품진열관리 인기상품 토글
    public ResVo putProductPop(Long iproduct) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(iproduct);
        ProductEntity entity = productEntityOptional.get();
        entity.setPopFl(entity.getPopFl() == 0 ? 1 : 0);
        productRepository.save(entity);
        return new ResVo(entity.getPopFl() == 0 ? Const.SUCCESS : Const.FAIL);
    }
    //------------상품진열관리 추천상품 토글
    public ResVo putProductRc(Long iproduct) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(iproduct);
        ProductEntity entity = productEntityOptional.get();
        entity.setRcFl(entity.getRcFl() == 0 ? 1 : 0);
        productRepository.save(entity);
        return new ResVo(entity.getRcFl() == 0 ? Const.SUCCESS : Const.FAIL);
    }

    // -------------- 상품등록
    @Transactional
    public ResVo postProduct(List<MultipartFile> pics, MultipartFile productDetails, AdminProductInsDto dto) {
        try {
            ProductEntity entity = new ProductEntity();

            ProductMiddleCategoryEntity middleCategoryEntity = middleCategoryRepository.findByImiddleAndProductMainCategory_Imain(dto.getImiddle(), dto.getImain());
            entity.setMiddleCategoryEntity(middleCategoryEntity);
            entity.setProductNm(dto.getProductNm());
            entity.setRecommandAge(dto.getRecommendedAge());
            entity.setPrice(dto.getPrice());
            entity.setAdminMemo(dto.getAdminMemo());
            entity.setRecommandAge(dto.getRemainedCount());
            productRepository.save(entity);
            String target = "/product/" + entity.getIproduct();

            String detailsFileNm = myFileUtils.transferTo(productDetails, target);
            entity.setProductDetails(detailsFileNm);
            entity.setNewFl(dto.getNewFl());
            entity.setPopFl(dto.getPopFl());

            ProductEntity savedEntity = productRepository.save(entity);
            for (MultipartFile pic : pics) {
                String fileNm = myFileUtils.transferTo(pic, target);
                ProductPicEntity productPicEntity = new ProductPicEntity();
                productPicEntity.setProductPic(fileNm);
                productPicEntity.setProductEntity(savedEntity);
                productPicRepository.save(productPicEntity);
            }
            return new ResVo(Const.SUCCESS);
        } catch (Exception e){
            return new ResVo(Const.FAIL);
        }
    }

    // 상품 수정
    public ResVo putProduct(List<MultipartFile> pics, MultipartFile productDetails, AdminProductInsDto dto, Long iproduct) {
        try {
            ProductEntity entity = productRepository.findById(iproduct).get();
            ProductMainCategoryEntity MainCategoryEntity = mainCategoryRepository.findById(dto.getImain()).get();
            ProductMiddleCategoryEntity middleCategoryEntity = middleCategoryRepository.findById( dto.getImiddle()).get();

            middleCategoryEntity.setProductMainCategory(MainCategoryEntity);
            middleCategoryEntity.setImiddle(dto.getImiddle());

            entity.setProductNm(dto.getProductNm());
            entity.setRecommandAge(dto.getRecommendedAge());
            entity.setPrice(dto.getPrice());
            entity.setAdminMemo(dto.getAdminMemo());
            entity.setRecommandAge(dto.getRemainedCount());
            entity.setNewFl(dto.getNewFl());
            entity.setPopFl(dto.getPopFl());
            productRepository.save(entity);

            String target = "/product/" + entity.getIproduct();
            myFileUtils.delDirTrigger(target);

            String detailsFileNm = myFileUtils.transferTo(productDetails, target);
            entity.setProductDetails(detailsFileNm);
            entity.setRepPic(pics.toString());

            ProductEntity savedEntity = productRepository.save(entity);
            for (MultipartFile file : pics) {
                String fileNm = myFileUtils.transferTo(file, target);
                ProductPicEntity productPicEntity = new ProductPicEntity();
                productPicEntity.setProductPic(fileNm);
                productPicEntity.setProductEntity(savedEntity);
                productPicRepository.save(productPicEntity);
            }
            return new ResVo(Const.SUCCESS);
        }catch (Exception e) {
            return new ResVo(Const.FAIL);
        }
    }

    //------------------------------------------------------- 상품삭제
    @Transactional
    public ResVo delProduct(Long iproduct) {
        Optional<ProductEntity> productOptional = productRepository.findById(iproduct);
        if (productOptional.isPresent()) {
            ProductEntity product = productOptional.get();
            product.setDelFl(1);
            productRepository.save(product);
            return new ResVo(Const.SUCCESS);
        }
        return new ResVo(Const.FAIL);
    }
    //상품검색
    public List<ProductGetSearchSelVo> getSearchProductSelVo(ProductGetSearchDto dto) {
//        List<ProductEntity> entity = productRepository.findProduct(dto);
//        List<ProductGetSearchSelVo> vo = entity.stream().map(item -> ProductGetSearchSelVo
//                .builder()
//                .productNm(item.getProductNm())
//                .price(item.getPrice())
//                .imain(item.getMiddleCategoryEntity().getProductMainCategory().getImain())
//                .imiddle(item.getMiddleCategoryEntity().getImiddle())
//                .repPic(item.getRepPic())
//                .build()).collect(Collectors.toList());
        List<ProductGetSearchSelVo> vo = productRepository.findProduct(dto);
        return vo;
    }

    //------------배너출력-------------------------
    public List<BannerSelVo> getBanner() {
        return bannerRepository.bannerSelVo();
    }

    //------------ 배너수정
    @Transactional
    public ResVo updateBanner(Long ibanner, MultipartFile pic, BannerInsDto dto) {
        try {
            BannerEntity banner = bannerRepository.findById(ibanner).get();
            banner.setBannerUrl(dto.getBannerUrl());
            banner.setTarget(dto.getTarget());
            bannerRepository.save(banner);
//            myFileUtils.delDirTrigger("/banner/" + banner.getBannerPic());
            String target = "/banner/" + banner.getIbanner();
            myFileUtils.delDirTrigger(target);
            String picName = myFileUtils.transferTo(pic, "/banner/" + ibanner);
            banner.setBannerPic(picName);
            bannerRepository.save(banner);
            return new ResVo(Const.SUCCESS);
        } catch (Exception e) {
            return new ResVo(Const.FAIL);
        }
    }

    //-----------배너 생성
    @Transactional
    public ResVo postBanner(MultipartFile pic, BannerInsDto dto) {
        try {
            BannerEntity banner = new BannerEntity();
            banner.setBannerUrl(dto.getBannerUrl());
            banner.setTarget(dto.getTarget());
            bannerRepository.save(banner);
            String savedPic = myFileUtils.transferTo(pic, "/banner/" + banner.getIbanner());
            banner.setBannerPic(savedPic);
            bannerRepository.save(banner);

            return new ResVo(Const.SUCCESS);
        } catch (Exception e) {
            return new ResVo(Const.FAIL);
        }
    }
    //-------배너삭제
    @Transactional
    public ResVo delBanner(Long ibanner) {
        int result = bannerRepository.deleteAllByIbanner(ibanner);
        if (result == 1) {
            return new ResVo(result);
        }
        return new ResVo(Const.FAIL);
    }

    //------리뷰 검색
    public List<SearchReviewSelVo> getSearchReview(ReviewSearchDto dto) {
//        List<ReviewEntity> result = reviewRepository.selReview(dto);
//        List<SearchReviewSelVo> vo = new ArrayList<>();
        List<SearchReviewSelVo> vo = reviewRepository.selReview(dto);
        return vo;
    }

    // 숨겼던 리뷰 검색
    public List<SearchReviewSelVo> getHiddenReview(ReviewSearchDto dto) {
        List<SearchReviewSelVo> vo = reviewRepository.selReviewDel(dto);
        return vo;
    }

    //관리자 메모 작성&수정
    public ResVo postReviewAdminMemo(ReviewMemoInsDto dto) {
        ReviewEntity entity = reviewRepository.findByIreview(dto.getIreview());
        if (entity != null) {
            entity.setAdminMemo(dto.getAdminMemo());
            reviewRepository.save(entity);
            return new ResVo(Const.SUCCESS);
        }
        return new ResVo(Const.FAIL);
    }
    //숨김리뷰 토글처ㅣㄹ
    public ResVo putReviewTogle(Long ireview) {
        Optional<ReviewEntity> optionalReview = reviewRepository.findById(ireview);
        ReviewEntity entity = optionalReview.get();
        entity.setDelFl(optionalReview.get().getDelFl() == 0 ? 1 : 0);
        reviewRepository.save(entity);
        return new ResVo(entity.getDelFl() == 0 ? Const.SUCCESS : Const.FAIL);
    }

    // 리뷰클릭시 뜨는창
    public List<ReviewHideClickSelVo> getHiCkSelVo(Long ireview) {
        return reviewRepository.findReview(ireview);
    }

    //관리자 메모만
    public String getReviewMeMo(Long ireview) {
        String vo = reviewRepository.findAdminMemoByIreview(ireview);
        return vo;
    }
























//    // 배너토글
//    public ResVo putBanner(Long ibanner) {
//        Optional<BannerEntity> bannerOptional = bannerRepository.findById(ibanner);
//        BannerEntity banner = bannerOptional.get();
//        banner.setStatus(banner.getStatus() == 0 ? 1 : 0);
//        bannerRepository.save(banner);
//        return new ResVo(banner.getStatus() == 0 ? Const.SUCCESS : Const.FAIL);
//    }
    //    // 숨기지 않는 리뷰
//    public List<SearchReviewSelVo> getReviewSelVo() {
//        List<SearchReviewSelVo> vo = reviewRepository.findAllByNotDelFl();
//        return vo;
//    }
    //    // ------------------------------------------------총 주문가격 &수
//    public OrderTotalSelVo getTotalPriceAndCount() {
//        return orderTotalRepository.getTotalPriceAndCount();
//    }
//
//    // ------------------------------------------------------최근주문
//    public List<OrderRecentSelVo> getRecentOrders() {
//        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
//        List<OrderRecentSelVo> recentOrders = orderTotalRepository.findRecentOrders(pageable);
//
//        return recentOrders;
//    }
//    // ------------------------------------------------------최근가입
//
//    public List<ddddMapping> getRecentUser() {
//        return userRepository2.findAllBy();
//
//    }
//
//    // *-------------------------------------------주문상태현황
//    public List<OrderStatusCountVo> getOrderStatusCount() {
//        List<OrderStatusCountVo> result = new ArrayList<>();
//
//        result.add(new OrderStatusCountVo("입금전", orderTotalRepository.countByProcessState(0)));
//        result.add(new OrderStatusCountVo("입금완료", orderTotalRepository.countByProcessState(1)));
//        result.add(new OrderStatusCountVo("배송준비중", orderTotalRepository.countByProcessState(2)));
//        result.add(new OrderStatusCountVo("배송중", orderTotalRepository.countByProcessState(3)));
//        result.add(new OrderStatusCountVo("배송완료", orderTotalRepository.countByProcessState(4)));
//
//        return result;
//    }
//    //-----------------------------qksvn반품취소
//    public OrderRefundAndCancelCountSelVo getCountRefundAndCancel() {
//        OrderRefundAndCancelCountSelVo vo = new OrderRefundAndCancelCountSelVo();
//        vo.setRefundFl(orderDetailsRepository.countByRefundFl(1));
//        vo.setDeleteFl(orderTotalRepository.countByDeleteFl(1));
//        return vo;
//
//    }


}


