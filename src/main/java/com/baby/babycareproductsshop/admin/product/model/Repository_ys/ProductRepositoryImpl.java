package com.baby.babycareproductsshop.admin.product.model.Repository_ys;

import com.baby.babycareproductsshop.admin.product.model.ReviewSearchDto;
import com.baby.babycareproductsshop.entity.product.ProductEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.baby.babycareproductsshop.entity.product.QProductEntity.productEntity;

//import static com.baby.babycareproductsshop.entity.product.QProductEntity.productEntity;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductQdslRepository{
    private final JPAQueryFactory jpaQueryFactory;
    //@Override
    public List<ProductEntity> selProductAll(ReviewSearchDto dto) {
        JPAQuery<ProductEntity> query = jpaQueryFactory.select(Projections.fields(ProductEntity.class,
                        productEntity.productNm,productEntity.iproduct,productEntity.price,productEntity.repPic))
                .where(whereProductNm(dto.getKeyword()),whereImain(dto.getImain()),whereImiddel(dto.getImiddle()))
                .from(productEntity);
        return query.fetch();
       // return null;
    }
    private BooleanExpression whereProductNm(String keyword) {
        return StringUtils.hasText(keyword) ? productEntity.productNm.contains(keyword) : null;
    }
    private BooleanExpression whereImain(Long imain) {
        return imain == 0 ? null : productEntity.productMainCategoryEntity.imain.eq(imain);
    }
   private BooleanExpression whereImiddel(int imiddel) {
        return imiddel == 0 ? null : productEntity.middleCategoryEntity.imiddle.eq(imiddel);
    }
}

