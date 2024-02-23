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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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


    //------------------------------------------------------- 상품삭제
    @Transactional
    public ResVo delProduct(Long iproduct) {
//        int result = productRepository.updatePriceForAllByIdIn(iproduct);
//        return new ResVo(result);
        return null;
    }

    //------------배너출력-------------------------
    public List<BannerEntity> getBanner() {
        return bannerRepository.findAllBy();
    }

    //------------상품진열관리 추천상품
    public List<Product2141234Vo> getProductRc() {
        List<Product2141234Vo> test = productRepository.findAllByRcFl(1L);
//        List<Product2141234Vo> result = new ArrayList<>();
//
//        for (Product2141234Vo vo : test) {
//            Long currentRc = vo.getRcFl(); // getRc()는 Product2141234Vo의 rc 값을 가져오는 메소드임
//            vo.setRcFl(currentRc + 1); // setRc()는 Product2141234Vo의 rc 값을 설정하는 메소드임
//            result.add(vo);
//        }
        return test;
    }

    //------------상품진열관리 신상품
    public List<Product2141234Vo> getProductNew() {
        return productRepository.findAllByNewFl(1L);
    }

    //------------상품진열관리 인기상품
    public List<Product2141234Vo> getProductPop() {
        return productRepository.findAllByPopFl(1L);
    }
    // -------------- 상품등록

    @Transactional
    public ResVo postProduct(List<MultipartFile> pics, MultipartFile productDetails, ProductInsDto dto) {
        ProductEntity entity = new ProductEntity();

        ProductMainCategoryEntity productMainCategoryEntity = mainCategoryRepository.findById(dto.getImain()).get();
        entity.setProductMainCategoryEntity(productMainCategoryEntity);
        ProductMiddleCategoryEntity middleCategoryEntity = middleCategoryRepository.findById(dto.getImiddle()).get();
        entity.setMiddleCategoryEntity(middleCategoryEntity);

        entity.setProductNm(dto.getProductNm());
        entity.setRecommandAge(dto.getRecommendedAge());
        entity.setPrice(dto.getPrice());
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

    //-----------배너 생성
    @Transactional
    public ResVo postBanner(MultipartFile pic, BannerInsDto dto) {
        BannerEntity banner = new BannerEntity();

        banner.setBannerUrl(dto.getBannerUrl());
        banner.setTarget(dto.getTarget());

        bannerRepository.save(banner);

        String savedPic = myFileUtils.transferTo(pic, "/banner/" + banner.getIbanner() + "/");

        banner.setBannerPic(savedPic);
        bannerRepository.save(banner);

        return new ResVo(Const.SUCCESS);
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

    //-------배너삭제
    @Transactional
    public ResVo delBanner(Long ibanner) {
        int result = bannerRepository.deleteAllByIbanner(ibanner);
        return new ResVo(result);
    }
    //------리뷰 검색

    @Transactional
    public List<SearchReviewSelVo> getSearchReview(ReviewSearchDto dto) {
//        List<ProductEntity> productEntities = productRepository.selProductAll(dto);
//        List<SearchReviewSelVo> vo = productEntities.stream().map(item ->SearchReviewSelVo
//                .builder()
//                .productNm(item.getProductNm())
//                .build()).collect(Collectors.toList());
//        return vo;
        List<ReviewEntity> reviewList =  reviewRepository.selReviewAll(dto);

        List<SearchReviewSelVo> vo = reviewList.stream().map(item -> SearchReviewSelVo
                        .builder()
                        .nm(item.getUserEntity().getNm()) // 유저명
                        .productNm(item.getProductEntity().getProductNm()) // 상품이름
                        .repPic(item.getProductEntity().getRepPic()) //대표사진
                        .ipoduct(item.getProductEntity().getIproduct()) //상품코드
                        .contents(item.getContents()) //리뷰내용
                        .productScore(item.getProductScore()) //리뷰별점
                        .build())
                .collect(Collectors.toList());

        return vo;

    }
}


