package com.baby.babycareproductsshop.admin.product.model.Repository_ys;

import com.baby.babycareproductsshop.admin.product.model.*;
import com.baby.babycareproductsshop.entity.product.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductQdslRepository {
    List<AdminProductSearchSelVo> selProductAll(AdminProductSearchDto dto,Pageable pageable);

    List<AdminProductSearchSelVo> selPopProduct(AdminProductSearchDto dto,Pageable pageable);
    List<AdminProductSearchSelVo> selNewProduct(AdminProductSearchDto dto,Pageable pageable);

    List<AdminProductUptSelVo> selProductUptSelVo (Long iproduct);

    List<AdminProductPicUptSelVo> selProductPicUptSelVo (Long iproduct);







    List<ProductGetSearchSelVo> findProduct(ProductGetSearchDto dto, Pageable pageable);
}

