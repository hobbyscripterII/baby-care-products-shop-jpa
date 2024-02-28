package com.baby.babycareproductsshop.admin.user;

import com.baby.babycareproductsshop.admin.user.model.AdminSelAllUserDto;
import com.baby.babycareproductsshop.admin.user.model.AdminSelUserSignupDto;
import com.baby.babycareproductsshop.entity.user.UserEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.baby.babycareproductsshop.entity.user.QUserEntity.userEntity;

@Slf4j
@RequiredArgsConstructor
public class AdminUserQdslRepositoryImpl extends AdminUserSearchCondition implements AdminUserQdslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UserEntity> selUserAll(AdminSelAllUserDto dto, Pageable pageable) {
        log.info("dto : {}", dto);
        JPAQuery<UserEntity> query = jpaQueryFactory.select(Projections.fields(UserEntity.class,
                        userEntity.iuser, userEntity.nm, userEntity.email,
                        userEntity.phoneNumber, userEntity.createdAt, userEntity.updatedAt
                ))
                .from(userEntity)
                .where(whereUnregisteredFl(dto.getUnregisteredFl()))
                .orderBy(userEntity.iuser.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        query.where(dto.getKeywordType() == 0 ? null :
                dto.getKeywordType() == 1 ?
                        likeEmail(dto.getKeyword()) : likeNm(dto.getKeyword()));

        if (dto.getBefore() != null) {
            query.where(dto.getAfter() == null ? betweenCreatedAt(LocalDateTime.of(dto.getBefore(), LocalTime.MIN))
                    : betweenCreatedAt(LocalDateTime.of(dto.getBefore(), LocalTime.MIN), LocalDateTime.of(dto.getAfter(), LocalTime.MAX).withNano(0)));
        }
        if (StringUtils.hasText(dto.getPhoneNumber())) {
            query.where(likePhoneNumber(dto.getPhoneNumber()));
        }
        log.info("query : {}", query);
        log.info("userEntity : {}", query.fetch());
        return query.fetch();
    }

    @Override
    public List<UserEntity> selUserSignupStatistics(AdminSelUserSignupDto dto) {
        JPAQuery<UserEntity> query = jpaQueryFactory.select(Projections.fields(UserEntity.class,
                        userEntity.createdAt, userEntity.iuser.count().as("iuser")
                ))
                .from(userEntity)
                .orderBy(userEntity.createdAt.asc());
        if (dto.getMonth() != 0) {
            query.groupBy(transformDate(userEntity.createdAt));
            query.having(whereYear(dto.getYear()), whereMonth(dto.getMonth()));
            return query.fetch();
        }
        query.groupBy(userEntity.createdAt.year(), userEntity.createdAt.month());
        query.having(whereYear(dto.getYear()));
        return query.fetch();
    }
}
