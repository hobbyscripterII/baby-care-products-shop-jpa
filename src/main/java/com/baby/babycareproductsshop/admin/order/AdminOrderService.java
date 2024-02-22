package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.model.*;
import com.baby.babycareproductsshop.common.Const;
import com.baby.babycareproductsshop.common.ProcessState;
import com.baby.babycareproductsshop.common.ResVo;
import com.baby.babycareproductsshop.entity.order.OrderEntity;
import com.baby.babycareproductsshop.exception.AuthErrorCode;
import com.baby.babycareproductsshop.exception.RestApiException;
import com.baby.babycareproductsshop.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminOrderService {
    private final AdminOrderQdlsRepository repository;

    public OrderListVo orderList(OrderFilterDto dto) {
        return null;
    }

    public OrderDetailsListVo orderDetailsList(OrderSmallFilterDto dto) {
        return null;
    }

    public OrderDeleteVo orderDeleteList(OrderSmallFilterDto dto) {
        return null;
    }

    public OrderRefundListVo orderRefundList(OrderSmallFilterDto dto) {
        return null;
    }

    public OrderMemoListVo adminMemo(OrderMemoListDto dto) {
        return null;
    }

    @Transactional
    public ResVo orderBatchProcess(OrderBatchProcessDto dto) {
        try {
            List<Integer> list = dto.getIorders()
                    .stream()
                    .map(iorder -> {
                        OrderEntity entity = repository.getReferenceById(iorder.longValue());
                        int processState = changeProcessState(dto.getProcessState());
                        entity.setIorder(iorder.longValue());
                        entity.setProcessState(processState); // 1. 값 변환
                        repository.save(entity); // 2. 주문 처리 상태 수정
                        return iorder.intValue();
                    })
                    .toList();

            // 2. 변환 후 변환된 개수와 dto에 넘어온 값 확인
            if (list.size() == dto.getIorders().size()) {
                return new ResVo(Const.SUCCESS);
            } else {
                throw new RestApiException(AuthErrorCode.ORDER_BATCH_PROCESS_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestApiException(AuthErrorCode.ORDER_BATCH_PROCESS_FAIL);
        }
    }

    private int changeProcessState(int processState) {
        switch (processState) {
            case 1:
                processState = ProcessState.DELIVER_IN_PROGRESS.getProcessStateNum();
                break;
            case 2:
                processState = ProcessState.ON_DELIVERY.getProcessStateNum();
                break;
            case 3:
                processState = ProcessState.DELIVER_SUCCESS.getProcessStateNum();
                break;
        }
        return processState;
    }
}
