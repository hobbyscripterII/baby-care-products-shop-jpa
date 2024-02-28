package com.baby.babycareproductsshop.admin.product.model.Repository_ys;

import com.baby.babycareproductsshop.admin.product.model.*;
import com.baby.babycareproductsshop.entity.product.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductQdslRepository {
    List<AdminProductSearchSelVo> selProductAll(AdminProductSearchDto dto);

    List<ProductGetSearchSelVo> findProduct(ProductGetSearchDto dto);
}

