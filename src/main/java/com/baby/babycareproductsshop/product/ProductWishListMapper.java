package com.baby.babycareproductsshop.product;

import com.baby.babycareproductsshop.product.model.ProductSelWishListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductWishListMapper {
    List<ProductSelWishListVo> selWishList(int iuser);
}
