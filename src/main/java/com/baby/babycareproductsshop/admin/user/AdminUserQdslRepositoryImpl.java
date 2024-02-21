package com.baby.babycareproductsshop.admin.user;

import com.baby.babycareproductsshop.admin.user.model.AdminSelAllUserVo;
import com.baby.babycareproductsshop.entity.user.UserEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import static com.baby.babycareproductsshop.entity.user.QUserEntity.userEntity;
@Slf4j
@RequiredArgsConstructor
public class AdminUserQdslRepositoryImpl implements AdminUserQdslRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AdminSelAllUserVo> selUserAll(long unregisteredFl, Pageable pageable) {
        List<UserEntity> list = jpaQueryFactory.select(userEntity)
                .from(userEntity)
                .fetch();
        return null;
    }

    private BooleanExpression whereUnregisteredFl(long unregisteredFl) {
        return userEntity.unregisterFl.eq(unregisteredFl);
    }

    private BooleanExpression likeNm(String nm) {
        return nm == null ? null : userEntity.nm.like(nm);
    }

    private BooleanExpression goeCreatedAt(LocalDateTime condition) {
        return condition == null ? null : userEntity.createdAt.goe(condition);
    }

    private BooleanExpression likePhoneNumber(String phoneNumber) {
        return phoneNumber == null ? null : userEntity.phoneNumber.like(phoneNumber);
    }

    private BooleanExpression betweenCreatedAt(LocalDateTime before, LocalDateTime after) {
        return before == null || after == null ? null : userEntity.createdAt.between(before, after);
    }
}
