package com.baby.babycareproductsshop.admin.product.model.Repository_ys;

import com.baby.babycareproductsshop.admin.product.model.ReviewSearchDto;
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
    public List<ReviewEntity> selReviewAll(ReviewSearchDto dto) {
        JPAQuery<ReviewEntity> query = jpaQueryFactory.select(Projections.fields(ReviewEntity.class,
                         reviewEntity.productScore
                        ,reviewEntity.contents //내용
//                        ,reviewEntity.userEntity.nm //작성자
//                        ,reviewEntity.productEntity.productNm // 상품이름
//                        ,reviewEntity.productEntity.price // 가격
//                        ,reviewEntity.productEntity.repPic //사진
//                        ,reviewEntity.productEntity.iproduct)
                        )
                ) //상품코드
                .from(reviewEntity)
                .join(reviewEntity.userEntity)
                .where(whereProductNm(dto.getKeyword()),whereImain(dto.getImain()),whereImiddel(dto.getImiddle()));
        return query.fetch();
    }


    private BooleanExpression whereProductNm(String keyword) {
        return StringUtils.hasText(keyword) ? reviewEntity.productEntity.productNm.contains(keyword) : null;
    }
    private BooleanExpression whereImain(Long imain) {
        return imain == 0 ? null : reviewEntity.productEntity.productMainCategoryEntity.imain.eq(imain);
    }
    private BooleanExpression whereImiddel(int imiddel) {
        return imiddel == 0 ? null : reviewEntity.productEntity.middleCategoryEntity.imiddle.eq(imiddel);
    }
}
