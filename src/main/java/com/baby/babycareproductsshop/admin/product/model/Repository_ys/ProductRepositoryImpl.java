package com.baby.babycareproductsshop.admin.product.model.Repository_ys;

import com.baby.babycareproductsshop.admin.product.model.AdminProductSearchDto;
import com.baby.babycareproductsshop.admin.product.model.ProductGetSearchDto;
import com.baby.babycareproductsshop.admin.product.model.ProductGetSearchSelVo;
import com.baby.babycareproductsshop.admin.product.model.ReviewSearchDto;
import com.baby.babycareproductsshop.entity.product.ProductEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

import static com.baby.babycareproductsshop.entity.product.QProductEntity.productEntity;
import static com.baby.babycareproductsshop.entity.review.QReviewEntity.reviewEntity;

//import static com.baby.babycareproductsshop.entity.product.QProductEntity.productEntity;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductQdslRepository{
    private final JPAQueryFactory jpaQueryFactory;
    // 물어봐야함 이거 태그 들어감
    @Override
    public List<ProductEntity> selProductAll(AdminProductSearchDto dto) {
        JPAQuery<ProductEntity> query = jpaQueryFactory.select(Projections.fields(ProductEntity.class,
                         productEntity.productNm
                        ,productEntity.iproduct
                        ,productEntity.price
                        ,productEntity.middleCategoryEntity.productMainCategory.imain
                        ,productEntity.middleCategoryEntity.imiddle
                        ,productEntity.repPic))
                .where(ProductNm(dto.getKeyword())
                        ,iproduct(dto.getIproduct())
                        ,Category(dto.getImain(),dto.getImiddle())
                        ,tage(dto.getNewFl(),dto.getPopFl(),dto.getRcFl())
                )
                .from(productEntity);
        return query.fetch();
    }
//--------------------------------------------------------------------------------------------------------

    //상품검색
    @Override
    public List<ProductGetSearchSelVo> findProduct(ProductGetSearchDto dto) {
        JPAQuery<ProductGetSearchSelVo> query = jpaQueryFactory.select(Projections.fields(ProductGetSearchSelVo.class,
                        productEntity.productNm
                        ,productEntity.iproduct
                        ,productEntity.price
                        ,productEntity.middleCategoryEntity.productMainCategory.imain
                        ,productEntity.middleCategoryEntity.imiddle
                        ,productEntity.repPic))
                .where(ProductNm(dto.getKeyword())
                        ,iproduct(dto.getIproduct())
                        ,Category(dto.getImain(),dto.getImiddle())
                        ,dateSelectSearch(dto.getDateFl())
                        ,price(dto.getMinPrice(),dto.getMaxPrice())
                        ,searchDateFilter(dto.getSearchStartDate(),dto.getSearchEndDate())
                )
                .from(productEntity);

        return query.fetch();
    }

    //*---------------------------------------------------------------------------------


    private BooleanExpression ProductNm(String keyword) { //상품이름
        return StringUtils.hasText(keyword) ? productEntity.productNm.contains(keyword) : null;
    }

    private BooleanExpression iproduct(Long iproduct) { //상품코드
        return iproduct != 0 ? reviewEntity.productEntity.iproduct.eq(iproduct) : null;
    }
    private BooleanExpression Category(Long imain, Long imiddle) { //카테고리
        if(imain != 0 && imiddle != 0) {
            return productEntity.middleCategoryEntity.productMainCategory.imain.eq(imain)
                    .and(reviewEntity.productEntity.middleCategoryEntity.imiddle.eq(imiddle));
        } else if(imain != 0) {
            return productEntity.middleCategoryEntity.productMainCategory.imain.eq(imain);
        } else if(imiddle != 0) {
            return productEntity.middleCategoryEntity.imiddle.eq( imiddle);
        } else {
            return null;
        }
    }
    private BooleanExpression price(int minPrice, int maxPrice) {
        if (minPrice != 0 && maxPrice != 0) {
            return productEntity.price.between(minPrice, maxPrice);
        }
        if (minPrice != 0) {
            return productEntity.price.goe(minPrice);
        }
        if (maxPrice != 0) {
            return productEntity.price.loe(maxPrice);
        }
        return null;
    }
    private BooleanExpression searchDateFilter(LocalDate searchStartDate, LocalDate searchEndDate) {
        if(searchStartDate == null || searchEndDate == null){
            return null;
        }
        if(searchStartDate.isAfter(searchEndDate)){
            return null;
        }
        try{
            BooleanExpression isGoeStartDate = productEntity.createdAt.goe(LocalDateTime.of(searchStartDate, LocalTime.MIN));
            BooleanExpression isLoeEndDate = productEntity.createdAt.loe(LocalDateTime.of(searchEndDate, LocalTime.MAX).withNano(0));
            return Expressions.allOf(isGoeStartDate, isLoeEndDate);
        }catch(Exception e){
            return null;
        }
    }

    private BooleanExpression tage(int newFl, int popFl, int rcFl) {
        if (newFl != 0 && popFl != 0 && rcFl != 0) {
            // 모두 선택한 경우
            return productEntity.newFl.eq(newFl)
                    .and(productEntity.popFl.eq(popFl))
                    .and(productEntity.rcFl.eq(rcFl));
        } else if (newFl != 0 && popFl != 0) {
            // newFl과 popFl만 선택한 경우
            return productEntity.newFl.eq(newFl)
                    .and(productEntity.popFl.eq(popFl));
        } else if (newFl != 0 && rcFl != 0) {
            // newFl과 rcFl만 선택한 경우
            return productEntity.newFl.eq(newFl)
                    .and(productEntity.rcFl.eq(rcFl));
        } else if (popFl != 0 && rcFl != 0) {
            // popFl과 rcFl만 선택한 경우
            return productEntity.popFl.eq(popFl)
                    .and(productEntity.rcFl.eq(rcFl));
        } else if (newFl != 0) {
            // newFl만 선택한 경우
            return productEntity.newFl.eq(newFl);
        } else if (popFl != 0) {
            // popFl만 선택한 경우
            return productEntity.popFl.eq(popFl);
        } else if (rcFl != 0) {
            // rcFl만 선택한 경우
            return productEntity.rcFl.eq(rcFl);
        } else {
            // 모두 선택하지 않은 경우
            return null;
        }
    }
    private BooleanExpression dateSelectSearch(int dateFl) {
        BooleanExpression booleanExpression = null;

        switch (dateFl) {
            case 1 -> booleanExpression = productEntity.createdAt.between(todayStartTime(), todayEndTime());
            case 2 -> booleanExpression = productEntity.createdAt.between(yesterdayStartTime(), yesterdayEndTime());
            case 3 -> booleanExpression = productEntity.createdAt.between(todayStartTime().minusDays(7), todayEndTime());
            case 4, 5 -> booleanExpression = productEntity.createdAt.between(monthStartDay(), monthEndDay());
            case 6 -> booleanExpression = productEntity.createdAt.between(monthStartDay().minusMonths(3), monthEndDay().minusMonths(3));
            default -> {
                return null;
            }
        }
        return booleanExpression;
    }

    // 아래는 어제, 오늘 시작 시간과 끝 시간 조건문 모음
    private LocalDateTime todayStartTime() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    private LocalDateTime todayEndTime() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }

    private LocalDateTime yesterdayStartTime() {
        return LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN);
    }

    private LocalDateTime yesterdayEndTime() {
        return LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);
    }

    private LocalDateTime monthStartDay() {
        return LocalDateTime.of(YearMonth.now().minusMonths(1).atDay(1), LocalTime.MIN);
    }

    private LocalDateTime monthEndDay() {
        return LocalDateTime.of(YearMonth.now().minusMonths(1).atDay(31), LocalTime.MAX);
    }
}


