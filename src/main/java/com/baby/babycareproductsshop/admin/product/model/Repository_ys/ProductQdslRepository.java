package com.baby.babycareproductsshop.admin.product.model.Repository_ys;

import com.baby.babycareproductsshop.admin.product.model.AdminProductSearchDto;
import com.baby.babycareproductsshop.admin.product.model.ProductGetSearchDto;
import com.baby.babycareproductsshop.admin.product.model.ProductGetSearchSelVo;
import com.baby.babycareproductsshop.admin.product.model.ReviewSearchDto;
import com.baby.babycareproductsshop.entity.product.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductQdslRepository {
    List<ProductEntity> selProductAll(AdminProductSearchDto dto);

    List<ProductGetSearchSelVo> findProduct(ProductGetSearchDto dto);
}

