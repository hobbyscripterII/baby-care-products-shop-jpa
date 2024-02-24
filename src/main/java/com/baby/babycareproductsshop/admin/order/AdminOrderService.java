package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.*;
import com.baby.babycareproductsshop.common.Const;
import com.baby.babycareproductsshop.common.ProcessState;
import com.baby.babycareproductsshop.common.ResVo;
import com.baby.babycareproductsshop.entity.order.OrderEntity;
import com.baby.babycareproductsshop.exception.AuthErrorCode;
import com.baby.babycareproductsshop.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminOrderService {
    private final AdminOrderRepository adminOrderRepository;
    private final AdminOrderDetailsRepository adminOrderDetailsRepository;

    @Transactional // 주문 일괄 처리
    public ResVo orderBatchProcess(OrderBatchProcessDto dto) {
        int beforeProcessState = dto.getProcessState();
        int afterProcessState = changeProcessState(dto.getProcessState());
        LocalDateTime now = LocalDateTime.now();

        // >>>>> 정상적인 주문 처리 상태 코드가 맞는지 확인
        if (processStateCheck(beforeProcessState)) {
            List<Integer> list = dto.getIorders()
                    .stream()
                    .peek(iorder -> {
                        OrderEntity entity = adminOrderRepository.getReferenceById(iorder.longValue());
                        entity.setIorder(iorder.longValue());
                        entity.setProcessState(afterProcessState); // 1. 값 변환

                        switch (afterProcessState) {
                            case 3 -> entity.setDepositedAt(now);
                            case 4 -> entity.setDeliveryCompletedAt(now);
                        }

                        adminOrderRepository.save(entity); // 2. 주문 처리 상태 및 완료일자 수정
                    })
                    .toList();

            // 2. 변환 후 변환된 개수와 dto에 넘어온 값 확인
            if (list.size() == dto.getIorders().size()) {
                return new ResVo(Const.SUCCESS);
            } else {
                throw new RestApiException(AuthErrorCode.ORDER_BATCH_PROCESS_FAIL);
            }
        } else {
            throw new RestApiException(AuthErrorCode.PROCESS_STATE_CODE_NOT_FOUND);
        }
    }

    public List<OrderListVo> orderList(OrderFilterDto dto) {
        return null;
    }

    // select문은 @Transactional 필요 x(<- 어노테이션은 rollback이 있을 수 있는 곳만)

    /**
     * iorder : 1
     * iorder에 종속된 idetails : N
     * idetails가 갖고있는 iproduct : 1
     */
    public List<OrderDetailsListVo> orderDetailsList(OrderSmallFilterDto dto) {
        return adminOrderRepository.orderDetailsList(dto)
                .stream()
                .map(orderItem -> {
                    List<OrderProductVo> orderProductVoList = adminOrderDetailsRepository.findAll(orderItem.getIorder()).stream()
                            .map(productItem -> OrderProductVo.builder()
                                    .repPic(productItem.getProductEntity().getRepPic())
                                    .productNm(productItem.getProductEntity().getProductNm())
                                    .cnt(productItem.getProductCnt())
                                    .processState(productItem.getOrderEntity().getProcessState())
                                    .amount(productItem.getProductEntity().getPrice())
                                    .build())
                            .toList();

                    OrderDetailsListVo vo = new OrderDetailsListVo();
                    vo.setIorder(orderItem.getIorder().intValue());
                    vo.setOrderedAt(orderItem.getCreatedAt().toString());
                    vo.setProducts(orderProductVoList);
                    vo.setOrdered(orderItem.getUserEntity().getNm());
                    vo.setRecipient(orderItem.getUserEntity().getNm());
                    vo.setTotalAmount(orderProductVoList.stream()
                            .mapToInt(OrderProductVo::getAmount)
                            .sum());
                    vo.setPayCategory(orderItem.getOrderPaymentOptionEntity().getIpaymentOption().intValue());
                    vo.setBuyComfirmFl(0); // 구매 확정 여부(자동화 추가)
                    return vo;
                })
                .toList();
    }

    public List<OrderDeleteVo> orderDeleteList(OrderSmallFilterDto dto) {
        return null;
    }

    public List<OrderRefundListVo> orderRefundList(OrderSmallFilterDto dto) {
        return null;
    }

    public List<OrderMemoListVo> adminMemoList(OrderMemoListDto dto) {
        return null;
    }

    private boolean processStateCheck(int processState) {
        return processState > 0 && processState < 4;
    }

    private int changeProcessState(int processState) {
        switch (processState) {
            case 1 -> processState = ProcessState.DELIVER_IN_PROGRESS.getProcessStateNum();
            case 2 -> processState = ProcessState.ON_DELIVERY.getProcessStateNum();
            case 3 -> processState = ProcessState.DELIVER_SUCCESS.getProcessStateNum();
        }
        return processState;
    }
}
