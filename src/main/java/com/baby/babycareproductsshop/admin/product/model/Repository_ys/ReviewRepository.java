package com.baby.babycareproductsshop.admin.product.model.Repository_ys;

import com.baby.babycareproductsshop.entity.product.ProductEntity;
import com.baby.babycareproductsshop.entity.review.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity,Long>, ReviewQdslRepository {

}
