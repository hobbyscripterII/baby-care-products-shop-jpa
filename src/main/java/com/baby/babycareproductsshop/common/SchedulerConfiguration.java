package com.baby.babycareproductsshop.common;

import com.baby.babycareproductsshop.entity.order.OrderEntity;
import com.baby.babycareproductsshop.entity.user.UserEntity;
import com.baby.babycareproductsshop.order.OrderRepository;
import com.baby.babycareproductsshop.response.ApiResponse;
import com.baby.babycareproductsshop.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerConfiguration {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;


    @Transactional
    @Scheduled(cron = "0 0 4 * * *")
    public void delUnregisteredUser() {
        List<UserEntity> userEntityList = userRepository.findAllByUnregisterFlAndUpdatedAtLessThan(1L, LocalDateTime.now().minusDays(30));
        List<OrderEntity> orderEntityList = orderRepository.findAllByUserEntityIn(userEntityList);
        for (OrderEntity orderEntity : orderEntityList) {
            orderEntity.setUserEntity(null);
            orderEntity.setUserAddressEntity(null);
        }
        userRepository.deleteAll(userEntityList);
        log.info("deleted user cnt : {}", userEntityList.size());
    }
}

