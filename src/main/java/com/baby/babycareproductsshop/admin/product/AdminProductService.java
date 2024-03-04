package com.baby.babycareproductsshop.admin.product;


import com.baby.babycareproductsshop.admin.product.model.*;
import com.baby.babycareproductsshop.admin.product.model.Repository_ys.*;
import com.baby.babycareproductsshop.admin.user.model.AdminSelUserSignupVo;
import com.baby.babycareproductsshop.common.Const;
import com.baby.babycareproductsshop.common.MyFileUtils;
import com.baby.babycareproductsshop.common.ResVo;
import com.baby.babycareproductsshop.common.Utils;
import com.baby.babycareproductsshop.entity.order.OrderDetailsEntity;
import com.baby.babycareproductsshop.entity.order.OrderEntity;
import com.baby.babycareproductsshop.entity.product.*;

import com.baby.babycareproductsshop.entity.review.ReviewEntity;
import com.baby.babycareproductsshop.entity.user.UserEntity;
import com.baby.babycareproductsshop.response.ApiResponse;
import com.baby.babycareproductsshop.security.AuthenticationFacade;
;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
    public List<AdminProductSearchSelVo> getSearchProductSelVo(AdminProductSearchDto dto, Pageable pageable) {
        List<AdminProductSearchSelVo> vo = productRepository.selProductAll(dto, pageable);
        return vo;
    }

    public List<AdminProductSearchSelVo> getSearchPopProductSelVo(AdminProductSearchDto dto, Pageable pageable) {
        List<AdminProductSearchSelVo> vo = productRepository.selPopProduct(dto, pageable);
        return vo;
    }

    public List<AdminProductSearchSelVo> getSearchNewProductSelVo(AdminProductSearchDto dto, Pageable pageable) {
        List<AdminProductSearchSelVo> vo = productRepository.selNewProduct(dto, pageable);
        return vo;
    }

    //------------상품진열관리 추천상품 조회
    public List<ProductManagementSelVo> getProductRc() {
        return productRepository.findAllByStatusAndRcFl(1,1);
    }

    //------------상품진열관리 신상품 조회
    public List<ProductManagementSelVo> getProductNew() {
        return productRepository.findAllByStatusAndNewFl(1,1);
    }

    //------------상품진열관리 인기상품 조회
    public List<ProductManagementSelVo> getProductPop() {
        return productRepository.findAllByStatusAndPopFl(1,1);
    }

    //------------상품진열관리 신상품 토글
    public ResVo putProductNew(Long iproduct) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(iproduct);
        ProductEntity entity = productEntityOptional.get();
        entity.setStatus(entity.getStatus() == 0 ? 1 : 0);
        productRepository.save(entity);
        return new ResVo(entity.getStatus() == 0 ? Const.FAIL : Const.SUCCESS);
    }

    //------------상품진열관리 인기상품 토글
    public ResVo putProductPop(Long iproduct) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(iproduct);
        ProductEntity entity = productEntityOptional.get();
        entity.setStatus(entity.getStatus() == 0 ? 1 : 0);
        productRepository.save(entity);
        return new ResVo(entity.getStatus() == 0 ? Const.FAIL : Const.SUCCESS);
    }

    //------------상품진열관리 추천상품 토글
    public ResVo putProductRc(Long iproduct) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(iproduct);
        ProductEntity entity = productEntityOptional.get();
        entity.setRcFl(entity.getRcFl() == 0 ? 1 : 0);
        entity.setStatus(entity.getStatus() == 0 ? 1 : 0);
        productRepository.save(entity);
        return new ResVo(entity.getStatus() == 0 ? Const.FAIL : Const.SUCCESS);
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
            entity.setRecommandAge(dto.getRemainedCnt());
            entity.setNewFl(1);
            productRepository.save(entity);
            String target = "/product/" + entity.getIproduct();

            String detailsFileNm = myFileUtils.transferTo(productDetails, target);
            entity.setProductDetails(detailsFileNm);

            ProductEntity savedEntity = productRepository.save(entity);
            for (MultipartFile pic : pics) {
                String fileNm = myFileUtils.transferTo(pic, target);
                ProductPicEntity productPicEntity = new ProductPicEntity();
                productPicEntity.setProductPic(fileNm);
                productPicEntity.setProductEntity(savedEntity);
                if (entity.getRepPic() == null) {
                    entity.setRepPic(fileNm);
                }
                productPicRepository.save(productPicEntity);
            }
            return new ResVo(Const.SUCCESS);
        } catch (Exception e) {
            return new ResVo(Const.FAIL);
        }
    }

    // 상품 수정
    public ResVo putProduct(List<MultipartFile> pics, MultipartFile productDetails, AdminProductUptDto dto, Long iproduct) {
        try {
            ProductEntity entity = productRepository.findById(iproduct).get();
            ProductMainCategoryEntity MainCategoryEntity = mainCategoryRepository.findById(dto.getImain()).get();
            ProductMiddleCategoryEntity middleCategoryEntity = middleCategoryRepository.findById(dto.getImiddle()).get();

            middleCategoryEntity.setProductMainCategory(MainCategoryEntity);
            middleCategoryEntity.setImiddle(dto.getImiddle());

            entity.setProductNm(dto.getProductNm());
            entity.setRecommandAge(dto.getRecommendedAge());
            entity.setPrice(dto.getPrice());
            entity.setAdminMemo(dto.getAdminMemo());
            entity.setRemainedCnt(dto.getRemainedCnt());
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
        } catch (Exception e) {
            return new ResVo(Const.FAIL);
        }
    }

    //------------------------------------------------------- 상품삭제
    @Transactional
    public ResVo delProduct(List<Long> iproduct) {
        List<ProductEntity> products = productRepository.findAllById(iproduct);
        if (!products.isEmpty()) {
            for (ProductEntity product : products) {
                product.setDelFl(1);
            }
            productRepository.saveAll(products);
            return new ResVo(Const.SUCCESS);
        }
        return new ResVo(Const.FAIL);
    }

    //상품검색
    public List<ProductGetSearchSelVo> getSearchProductSelVo(ProductGetSearchDto dto, Pageable pageable) {
//        List<ProductEntity> entity = productRepository.findProduct(dto);
//        List<ProductGetSearchSelVo> vo = entity.stream().map(item -> ProductGetSearchSelVo
//                .builder()
//                .productNm(item.getProductNm())
//                .price(item.getPrice())
//                .imain(item.getMiddleCategoryEntity().getProductMainCategory().getImain())
//                .imiddle(item.getMiddleCategoryEntity().getImiddle())
//                .repPic(item.getRepPic())
//                .build()).collect(Collectors.toList());
        List<ProductGetSearchSelVo> vo = productRepository.findProduct(dto, pageable);
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
            if (pic != null) {
                String target = "/banner/" + banner.getIbanner();
                myFileUtils.delDirTrigger(target);
                String savedPic = myFileUtils.transferTo(pic, target);
                banner.setBannerPic(savedPic);
            }
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
        String target = "/banner/" + ibanner;
        myFileUtils.delDirTrigger(target);
        int result = bannerRepository.deleteAllByIbanner(ibanner);
        if (result == 1) {
            return new ResVo(result);
        }
        return new ResVo(Const.FAIL);
    }

    //------리뷰 검색
    public List<SearchReviewSelVo> getSearchReview(ReviewSearchDto dto, Pageable pageable) {
//        List<ReviewEntity> result = reviewRepository.selReview(dto);
//        List<SearchReviewSelVo> vo = new ArrayList<>();
        List<SearchReviewSelVo> vo = reviewRepository.selReview(dto, pageable);
        return vo;
    }

    // 숨겼던 리뷰 검색
    public List<SearchReviewSelVo> getHiddenReview(ReviewSearchDto dto, Pageable pageable) {
        List<SearchReviewSelVo> vo = reviewRepository.selReviewDel(dto, pageable);
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

    // 취소수
    public List<AdminRefundReturnSelVo> OrderCancelSelVo(AdminRefundReturnDto dto) {
        List<OrderEntity> entityList = orderTotalRepository.refundReturnSelVo(dto);
        Map<String, AdminRefundReturnSelVo> map = new HashMap<>();
        int totalRegisterCnt = 0;

        for (OrderEntity entity : entityList) {
            totalRegisterCnt += entity.getDeleteFl();
            //totalRegisterCnt ++;
        }
        List<AdminRefundReturnSelVo> result = new ArrayList<>();
        for (OrderEntity entity : entityList) {

                AdminRefundReturnSelVo vo = new AdminRefundReturnSelVo();
                vo.setRegisterCnt(entity.getDeleteFl());
                vo.setUpdatedAt(entity.getUpdatedAt());
                vo.setDate(Utils.getDate(dto.getYear(), dto.getMonth(), vo));
                vo.setTotalRegisterCnt(totalRegisterCnt);
                vo.setRegisterRate(String.format("%.2f", (double) vo.getRegisterCnt() / totalRegisterCnt));

                map.put(Utils.getDate(dto.getYear(), dto.getMonth(), vo), vo);
                result.add(vo);
        }
        if (dto.getYear() == 0 && dto.getMonth() == 0) {
            return result;
        }

        int date = Utils.getDaysOrMonths(dto.getYear(), dto.getMonth());

        for (int i = 1; i <= date; i++) {
            String key = Utils.getKey(dto.getYear(), dto.getMonth(), i);
            AdminRefundReturnSelVo vo = map.get(key);
            if (vo == null) {
                map.put(key, new AdminRefundReturnSelVo(key));
            }
        }
        result = map.values().stream().sorted().toList();
        return result;
    }
    // 반품
    public List<AdminRefundReturnSelVo> OrderReturnSelVo(AdminRefundReturnDto dto) {
        List<OrderDetailsEntity> entityList = orderDetailsRepository.refundReturnSelVo(dto);
        Map<String, AdminRefundReturnSelVo> map = new HashMap<>();
        int totalRegisterCnt = 0;
        for (OrderDetailsEntity entity : entityList) {
            totalRegisterCnt += entity.getRefundFl();
            //totalRegisterCnt ++;
        }
        List<AdminRefundReturnSelVo> result = new ArrayList<>();
        for (OrderDetailsEntity entity : entityList) {
            AdminRefundReturnSelVo vo = new AdminRefundReturnSelVo();
            vo.setRegisterCnt(entity.getRefundFl());
            vo.setUpdatedAt(entity.getUpdatedAt());
            vo.setDate(Utils.getDate(dto.getYear(), dto.getMonth(), vo));
            vo.setTotalRegisterCnt(totalRegisterCnt);
//            if (vo.getRegisterCnt() == 0 ) {
//                vo.setRegisterRate(String.valueOf(0));
//            }
            vo.setRegisterRate(String.format("%.2f", (double) vo.getRegisterCnt() / totalRegisterCnt));

            map.put(Utils.getDate(dto.getYear(), dto.getMonth(), vo), vo);
            result.add(vo);
        }

        if (dto.getYear() == 0 && dto.getMonth() == 0) {
            return result;
        }
        int date = Utils.getDaysOrMonths(dto.getYear(), dto.getMonth());
        for (int i = 1; i <= date; i++) {
            String key = Utils.getKey(dto.getYear(), dto.getMonth(), i);
            AdminRefundReturnSelVo vo = map.get(key);
            if (vo == null) {
                map.put(key, new AdminRefundReturnSelVo(key));
            }
        }
        result = map.values().stream().sorted().toList();
        return result;
    }

    public List<AdminProductUptSelVo> getProduct(Long iproduct) {
        return productRepository.selProductUptSelVo(iproduct);
    }

    public List<AdminProductPicUptSelVo> getProductPic(Long iproduct) {
        return productRepository.selProductPicUptSelVo(iproduct);
    }
}


