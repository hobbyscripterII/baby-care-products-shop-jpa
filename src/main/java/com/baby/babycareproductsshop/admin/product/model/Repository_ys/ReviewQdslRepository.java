package com.baby.babycareproductsshop.admin.product.model.Repository_ys;

import com.baby.babycareproductsshop.admin.product.model.ReviewSearchDto;
import com.baby.babycareproductsshop.entity.product.ProductEntity;
import com.baby.babycareproductsshop.entity.review.ReviewEntity;

import java.util.List;

public interface ReviewQdslRepository {

    List<ReviewEntity> selReviewAll(ReviewSearchDto dto);


}
