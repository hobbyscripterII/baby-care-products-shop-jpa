package com.baby.babycareproductsshop.admin.product.model.Repository_ys;

import com.baby.babycareproductsshop.admin.product.model.ReviewHideClickSelVo;
import com.baby.babycareproductsshop.admin.product.model.ReviewSearchDto;
import com.baby.babycareproductsshop.admin.product.model.SearchReviewSelVo;
import com.baby.babycareproductsshop.entity.product.ProductEntity;
import com.baby.babycareproductsshop.entity.review.ReviewEntity;

import java.util.List;

public interface ReviewQdslRepository {

    List<SearchReviewSelVo> selReviewAll(ReviewSearchDto dto);
    List<SearchReviewSelVo> findAllByDelFl();
    List<SearchReviewSelVo> findAllByNotDelFl();

    List<ReviewHideClickSelVo> findReview(Long ireview);






}
