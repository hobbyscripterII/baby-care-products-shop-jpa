package com.baby.babycareproductsshop.admin.category;

import com.baby.babycareproductsshop.entity.product.ProductMiddleCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminMiddleCategoryRepository extends JpaRepository<ProductMiddleCategoryEntity, Long> {
    List<ProductMiddleCategoryEntity> getByProductMainCategory_Imain(long imain);
}
