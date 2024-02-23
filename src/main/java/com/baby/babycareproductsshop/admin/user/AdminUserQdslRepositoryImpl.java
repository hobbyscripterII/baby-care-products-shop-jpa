package com.baby.babycareproductsshop.admin.user;

import com.baby.babycareproductsshop.admin.user.model.AdminSelAllUserDto;
import com.baby.babycareproductsshop.admin.user.model.AdminSelUserSignupDto;
import com.baby.babycareproductsshop.entity.user.UserEntity;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.baby.babycareproductsshop.entity.user.QUserEntity.userEntity;

@Slf4j
@RequiredArgsConstructor
public class AdminUserQdslRepositoryImpl implements AdminUserQdslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UserEntity> selUserAll(AdminSelAllUserDto dto) {
        log.info("dto : {}", dto);
        JPAQuery<UserEntity> query = jpaQueryFactory.select(Projections.fields(UserEntity.class,
                        userEntity.iuser, userEntity.nm, userEntity.email,
                        userEntity.phoneNumber, userEntity.createdAt, userEntity.updatedAt
                ))
                .from(userEntity)
                .where(whereUnregisteredFl(dto.getUnregisteredFl()))
                .orderBy(userEntity.iuser.desc());

        query.where(dto.getKeywordType() == 0 ? null :
                dto.getKeywordType() == 1 ?
                        likeUid(dto.getKeyword()) : likeNm(dto.getKeyword()));

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

    private BooleanExpression whereUnregisteredFl(long unregisteredFl) {
        return userEntity.unregisterFl.eq(unregisteredFl);
    }

    private BooleanExpression likeNm(String nm) {
        return StringUtils.hasText(nm) ? userEntity.nm.contains(nm) : null;
    }

    private BooleanExpression likeUid(String uid) {
        return StringUtils.hasText(uid) ? userEntity.uid.contains(uid) : null;
    }

    private BooleanExpression goeCreatedAt(LocalDateTime condition) {
        return condition == null ? null : userEntity.createdAt.goe(condition);
    }

    private BooleanExpression likePhoneNumber(String phoneNumber) {
        return phoneNumber == null ? null : userEntity.phoneNumber.contains(phoneNumber);
    }

    private BooleanExpression betweenCreatedAt(LocalDateTime before, LocalDateTime after) {
        return before == null || after == null ? null : userEntity.createdAt.between(before, after);
    }

    private BooleanExpression betweenCreatedAt(LocalDateTime before) {
        return betweenCreatedAt(before, LocalDateTime.now());
    }

    private BooleanExpression whereYear(int year) {
        return year == 0 ? null : userEntity.createdAt.year().eq(year);
    }

    private BooleanExpression whereMonth(int month) {
        return month == 0 ? null : userEntity.createdAt.month().eq(month);
    }

    private StringTemplate transformDate(Object object) {
        return Expressions.stringTemplate("DATE_FORMAT({0},'{1s}')", object, ConstantImpl.create("%Y-%m-%d"));
    }
}
