package com.baby.babycareproductsshop.admin.user;

import com.baby.babycareproductsshop.admin.user.model.AdminSelAllUserVo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;
import static com.baby.babycareproductsshop.entity.user.QUserEntity.userEntity;
@Slf4j
@RequiredArgsConstructor
public class AdminUserQdslRepositoryImpl implements AdminUserQdslRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AdminSelAllUserVo> selUserAll(long unregisteredFl, Pageable pageable) {
        return null;
    }
}
