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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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





    // ------------------------------------------------총 주문가격 &수
    public OrderTotalSelVo getTotalPriceAndCount() {
        return orderTotalRepository.getTotalPriceAndCount();
    }

    // ------------------------------------------------------최근주문
    public List<OrderRecentSelVo> getRecentOrders() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<OrderRecentSelVo> recentOrders = orderTotalRepository.findRecentOrders(pageable);

        return recentOrders;
    }
    // ------------------------------------------------------최근가입

    public List<ddddMapping> getRecentUser() {
        return userRepository2.findAllBy();

    }

    // *-------------------------------------------주문상태현황
    public List<OrderStatusCountVo> getOrderStatusCount() {
        List<OrderStatusCountVo> result = new ArrayList<>();

        result.add(new OrderStatusCountVo("입금전", orderTotalRepository.countByProcessState(0)));
        result.add(new OrderStatusCountVo("입금완료", orderTotalRepository.countByProcessState(1)));
        result.add(new OrderStatusCountVo("배송준비중", orderTotalRepository.countByProcessState(2)));
        result.add(new OrderStatusCountVo("배송중", orderTotalRepository.countByProcessState(3)));
        result.add(new OrderStatusCountVo("배송완료", orderTotalRepository.countByProcessState(4)));

        return result;
    }
    //-----------------------------qksvn반품취소
    public OrderRefundAndCancelCountSelVo getCountRefundAndCancel() {
        OrderRefundAndCancelCountSelVo vo = new OrderRefundAndCancelCountSelVo();
        vo.setRefundFl(orderDetailsRepository.countByRefundFl(1));
        vo.setDeleteFl(orderTotalRepository.countByDeleteFl(1));
        return vo;

    }



    //------------상품진열관리 추천상품 조회
    public List<Product2141234Vo> getProductRc() {
        return productRepository.findAllByRcFl(1);
    }

    //------------상품진열관리 신상품 조회
    public List<Product2141234Vo> getProductNew() {
        return productRepository.findAllByNewFl(1);
    }

    //------------상품진열관리 인기상품 조회
    public List<Product2141234Vo> getProductPop() {
        return productRepository.findAllByPopFl(1);
    }

    //------------상품진열관리 신상품 토글
    public ResVo putProductNew(Long iproduct) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(iproduct);
        ProductEntity entity = productEntityOptional.get();
        entity.setNewFl(entity.getNewFl() == 0 ? 1 : 0);
        productRepository.save(entity);
        return new ResVo(Const.SUCCESS);
    }
    //------------상품진열관리 인기상품 토글
    public ResVo putProductPop(Long iproduct) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(iproduct);
        ProductEntity entity = productEntityOptional.get();
        entity.setPopFl(entity.getPopFl() == 0 ? 1 : 0);
        productRepository.save(entity);
        return new ResVo(Const.SUCCESS);
    }
    //------------상품진열관리 추천상품 토글
    public ResVo putProductRc(Long iproduct) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(iproduct);
        ProductEntity entity = productEntityOptional.get();
        entity.setRcFl(entity.getRcFl() == 0 ? 1 : 0);
        productRepository.save(entity);
        return new ResVo(Const.SUCCESS);
    }

    // -------------- 상품등록
    @Transactional
    public ResVo postProduct(List<MultipartFile> pics, MultipartFile productDetails, AdminProductInsDto dto) {
        ProductEntity entity = new ProductEntity();

        ProductMainCategoryEntity productMainCategoryEntity = mainCategoryRepository.findById(dto.getImain()).get();
        entity.setProductMainCategoryEntity(productMainCategoryEntity);
        ProductMiddleCategoryEntity middleCategoryEntity = middleCategoryRepository.findById(dto.getImiddle()).get();
        entity.setMiddleCategoryEntity(middleCategoryEntity);

        entity.setProductNm(dto.getProductNm());
        entity.setRecommandAge(dto.getRecommendedAge());
        entity.setPrice(dto.getPrice());
       // entity.setAdminMemo(dtoS.getAdminMemo());
        entity.setRecommandAge(dto.getRemainedCount());
        String target = "/product/" + entity.getIproduct();

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
    }

    // 상품 수정
    public ResVo putProduct(List<MultipartFile> pics, MultipartFile productDetails, ProductUptDto dto, Long iproduct) {
        ProductEntity entity = productRepository.findById(iproduct).get();

        ProductMainCategoryEntity productMainCategoryEntity = mainCategoryRepository.findById(dto.getImain()).get();
        entity.setProductMainCategoryEntity(productMainCategoryEntity);
        ProductMiddleCategoryEntity middleCategoryEntity = middleCategoryRepository.findById(dto.getImiddle()).get();
        entity.setMiddleCategoryEntity(middleCategoryEntity);

        entity.setProductNm(dto.getProductNm());
        entity.setRecommandAge(dto.getRecommendedAge());
        entity.setPrice(dto.getPrice());
        entity.setNewFl(dto.getNewFl());
        entity.setPopFl(dto.getPopFl());
        entity.setRcFl(dto.getRcFl());
        entity.setAdminMemo(dto.getAdminMemo());
        entity.setRecommandAge(dto.getRemainedCount());

        String target = "/product/" + entity.getIproduct();

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
    }

    //------------------------------------------------------- 상품삭제
    @Transactional
    public ResVo delProduct(Long iproduct) {
        Optional<ProductEntity> productOptional = productRepository.findById(iproduct);
        ProductEntity product = productOptional.get();
        product.setDelFl(1);
        productRepository.save(product);  // 변경
        return new ResVo(Const.SUCCESS);
    }


    //------리뷰 검색
    @Transactional
    public List<SearchReviewSelVo> getSearchReview(ReviewSearchDto dto) {
        List<SearchReviewSelVo> vo = reviewRepository.selReviewAll(dto);
        return vo;
    }

    //------------배너출력-------------------------
    public List<BannerSelVo> getBanner() {
        return bannerRepository.bannerSelVo();
    }

    //------------ 배너수정
    @Transactional
    public ResVo updateBanner(Long id, MultipartFile pic, BannerInsDto dto) {

        BannerEntity banner = bannerRepository.findById(id).orElse(null);
        banner.setBannerUrl(dto.getBannerUrl());
        banner.setTarget(dto.getTarget());
        if (pic != null) {
            myFileUtils.delDirTrigger("/banner/" + id + "/" + banner.getBannerPic());
            String picName = myFileUtils.transferTo(pic, "/banner/" + id);
            banner.setBannerPic(picName);
        }
        bannerRepository.save(banner);
        return new ResVo(1);
    }

    //-----------배너 생성
    @Transactional
    public ResVo postBanner(MultipartFile pic, BannerInsDto dto) {
        BannerEntity banner = new BannerEntity();

        banner.setBannerUrl(dto.getBannerUrl());
        banner.setTarget(dto.getTarget());
        String savedPic = myFileUtils.transferTo(pic, "/banner/" + banner.getIbanner() + "/");
        banner.setBannerPic(savedPic);
        bannerRepository.save(banner);
        return new ResVo(Const.SUCCESS);
    }
    //-------배너삭제
    @Transactional
    public ResVo delBanner(Long ibanner) {
        int result = bannerRepository.deleteAllByIbanner(ibanner);
        return new ResVo(result);
    }
    // 배너토글
    public ResVo putBanner(Long ibanner) {
        Optional<BannerEntity> bannerOptional = bannerRepository.findById(ibanner);
        BannerEntity banner = bannerOptional.get();
        banner.setStatus(banner.getStatus() == 0 ? 1 : 0);
        bannerRepository.save(banner);
        return new ResVo(Const.SUCCESS);
    }

    // 숨김리뷰만 조회
    public List<SearchReviewSelVo> getHiddenReview() {
        List<SearchReviewSelVo> vo = reviewRepository.findAllByDelFl();
        return vo;
    }
    // 숨기지 않는 리뷰
    public List<SearchReviewSelVo> getReviewSelVo() {
        List<SearchReviewSelVo> vo = reviewRepository.findAllByNotDelFl();
        return vo;
    }
    //관리자 메모 작성&수정
    public ResVo postReviewAdminMemo(ReviewMemoInsDto dto) {
        ReviewEntity entity = reviewRepository.findByIreview(dto.getIreview());
        entity.setAdminMemo(dto.getAdminMemo());
        reviewRepository.save(entity);
        return new ResVo(Const.SUCCESS);
    }

    public ResVo putReviewTogle(Long ireview) {
        Optional<ReviewEntity> optionalReview = reviewRepository.findById(ireview);
        ReviewEntity entity = optionalReview.get();
        entity.setDelFl(optionalReview.get().getDelFl() == 0 ? 1 : 0);
        reviewRepository.save(entity);
        return new ResVo(Const.SUCCESS);
    }


    public List<ReviewHideClickSelVo> getHiCkSelVo(Long ireview) {
       return reviewRepository.findReview(ireview);


    }
}


