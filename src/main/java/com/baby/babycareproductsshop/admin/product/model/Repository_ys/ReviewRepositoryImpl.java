package com.baby.babycareproductsshop.admin.product.model.Repository_ys;

import com.baby.babycareproductsshop.admin.product.model.ReviewHideClickSelVo;
import com.baby.babycareproductsshop.admin.product.model.ReviewSearchDto;
import com.baby.babycareproductsshop.admin.product.model.SearchReviewSelVo;
import com.baby.babycareproductsshop.entity.review.ReviewEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.baby.babycareproductsshop.entity.product.QProductEntity.productEntity;
import static com.baby.babycareproductsshop.entity.review.QReviewEntity.reviewEntity;
import static com.baby.babycareproductsshop.entity.user.QUserEntity.userEntity;


@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewQdslRepository{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<SearchReviewSelVo> selReviewAll(ReviewSearchDto dto) {
        JPAQuery<SearchReviewSelVo> query = jpaQueryFactory.select(Projections.constructor(SearchReviewSelVo.class,
                        reviewEntity.userEntity.nm,
                        reviewEntity.productEntity.repPic,
                        reviewEntity.productEntity.iproduct,
                        reviewEntity.productEntity.productNm,
                        reviewEntity.contents,
                        reviewEntity.productScore,
                        reviewEntity.delFl)
                )
                .from(reviewEntity)
                .rightJoin(reviewEntity.userEntity)
                .leftJoin(reviewEntity.productEntity)
                .where(whereProductNm(dto.getKeyword()), whereCategory(dto.getImain(), dto.getImiddle()));
        return query.fetch();
    }

    @Override
    public List<SearchReviewSelVo> findAllByDelFl() {
            JPAQuery<SearchReviewSelVo> query = jpaQueryFactory.select(Projections.constructor(SearchReviewSelVo.class,
                            reviewEntity.userEntity.nm,
                            reviewEntity.productEntity.repPic,
                            reviewEntity.productEntity.iproduct,
                            reviewEntity.productEntity.productNm,
                            reviewEntity.contents,
                            reviewEntity.productScore,
                            reviewEntity.delFl)
                    )
                    .from(reviewEntity)
                    .rightJoin(reviewEntity.userEntity)
                    .leftJoin(reviewEntity.productEntity)
                    .where(reviewEntity.delFl.eq(1));
            return query.fetch();

    }

    @Override
    public List<SearchReviewSelVo> findAllByNotDelFl() {
        JPAQuery<SearchReviewSelVo> query = jpaQueryFactory.select(Projections.constructor(SearchReviewSelVo.class,
                        reviewEntity.userEntity.nm,
                        reviewEntity.productEntity.repPic,
                        reviewEntity.productEntity.iproduct,
                        reviewEntity.productEntity.productNm,
                        reviewEntity.contents,
                        reviewEntity.productScore,
                        reviewEntity.delFl)
                )
                .from(reviewEntity)
                .rightJoin(reviewEntity.userEntity)
                .leftJoin(reviewEntity.productEntity)
                .where(reviewEntity.delFl.eq(0));
        return query.fetch();

    }

    @Override
    public List<ReviewHideClickSelVo> findReview(Long ireview) {
        JPAQuery<ReviewHideClickSelVo> query = jpaQueryFactory.select(Projections.constructor(ReviewHideClickSelVo.class,
                        reviewEntity.userEntity.nm,
                        reviewEntity.productEntity.repPic,
                        reviewEntity.productEntity.productNm,
                        reviewEntity.contents,
                        reviewEntity.productScore
                        )
                )
                .from(reviewEntity)
                .rightJoin(reviewEntity.userEntity)
                .leftJoin(reviewEntity.productEntity)
                .where(reviewEntity.ireview.eq(ireview));
        return query.fetch();

    }


    private BooleanExpression whereProductNm(String keyword) {
        return StringUtils.hasText(keyword) ? reviewEntity.productEntity.productNm.like("%" + keyword + "%") : null;
    }
    private BooleanExpression whereCategory(Long imain, int imiddle) {
        if(imain != 0 && imiddle != 0) {
            // 두 카테고리 모두 선택한 경우
            return reviewEntity.productEntity.productMainCategoryEntity.imain.eq(imain)
                    .and(reviewEntity.productEntity.middleCategoryEntity.imiddle.eq(imiddle));
        } else if(imain != 0) {
            // Main 카테고리만 선택한 경우
            return reviewEntity.productEntity.productMainCategoryEntity.imain.eq(imain);
        } else if(imiddle != 0) {
            // Middle 카테고리만 선택한 경우
            return reviewEntity.productEntity.middleCategoryEntity.imiddle.eq(imiddle);
        } else {
            // 두 카테고리 모두 선택하지 않은 경우
            return null;
        }
    }


}
