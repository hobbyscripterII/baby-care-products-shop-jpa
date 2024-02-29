package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.order.model.*;
import com.baby.babycareproductsshop.common.*;
import com.baby.babycareproductsshop.entity.order.OrderEntity;
import com.baby.babycareproductsshop.exception.AuthErrorCode;
import com.baby.babycareproductsshop.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Scheduled(cron = "0 0 0 * * *")
    void deliveredAutoChange() {
        adminOrderRepository.deliveredAutoChange();
    }

    @Scheduled(cron = "0 0 0 * * *")
    void orderCancelAutoChange() {
        adminOrderRepository.orderCancelAutoChange();
    }

    @Transactional
    public ResVo updateAdminMemo(OrderMemoUpdDto dto) {
        try {
            OrderEntity orderEntity = adminOrderRepository.getReferenceById(dto.getIorder());
            orderEntity.setIorder(dto.getIorder());
            orderEntity.setAdminMemo(dto.getAdminMemo());
            adminOrderRepository.save(orderEntity);
            return new ResVo(Const.SUCCESS);
        } catch (Exception e) {
            throw new RestApiException(AuthErrorCode.ADDRESS_UPDATE_FAIL);
        }
    }

    @Transactional
    public ResVo orderBatchProcess(OrderBatchProcessDto dto) {
        int beforeProcessState = dto.getProcessState();
        int afterProcessState = changeProcessState(dto.getProcessState());
        LocalDateTime now = LocalDateTime.now();

        // 정상적인 주문 처리 상태 코드가 맞는지 확인
        List<Integer> list = dto.getIorders()
                .stream()
                .peek(iorder -> {
                    if (processStateCheck(iorder, beforeProcessState)) { // 수정 요청 상태가 이전 주문 요청 상태와 맞는지
                        OrderEntity entity = adminOrderRepository.getReferenceById(iorder.longValue());
                        entity.setIorder(iorder.longValue());
                        entity.setProcessState(afterProcessState); // 1. 값 변환

                        switch (afterProcessState) {
                            case 3 -> entity.setDepositedAt(now);
                            case 4 -> entity.setDeliveredAt(now);
                        }

                        adminOrderRepository.save(entity); // 2. 주문 처리 상태 및 완료일자 수정
                    } else {
                        throw new RestApiException(AuthErrorCode.PROCESS_STATE_CODE_ERROR);
                    }
                })
                .toList();

        // 2. 변환 후 변환된 개수와 dto에 넘어온 값 확인
        if (list.size() == dto.getIorders().size()) {
            return new ResVo(Const.SUCCESS);
        } else {
            throw new RestApiException(AuthErrorCode.ORDER_BATCH_PROCESS_FAIL);
        }
    }

    public List<OrderListVo> orderList(OrderFilterDto dto, Pageable pageable) {
        return adminOrderRepository.orderList(dto, pageable)
                .stream()
                .map(orderItem -> {
                    List<OrderProductVo> orderProductVoList = adminOrderDetailsRepository.findAll(orderItem.getIorder()).stream()
                            .map(productItem -> OrderProductVo
                                    .builder()
                                    .repPic(productItem.getProductEntity().getRepPic())
                                    .productNm(productItem.getProductEntity().getProductNm())
                                    .cnt(productItem.getProductCnt())
                                    .processState(productItem.getOrderEntity().getProcessState())
                                    .amount(productItem.getProductEntity().getPrice())
                                    .build())
                            .toList();

                    return OrderListVo
                            .builder()
                            .processState(orderItem.getProcessState())
                            .iorder(orderItem.getIorder().intValue())
                            .orderedAt(orderItem.getCreatedAt().toString())
                            .products(orderProductVoList)
                            .ordered(orderItem.getUserEntity().getNm())
                            .recipient(orderItem.getUserEntity().getNm())
                            .totalAmount(orderItem.getTotalPrice())
                            .payCategory(orderItem.getOrderPaymentOptionEntity().getIpaymentOption().intValue())
                            .refundFl(orderItem.getProcessState() == ProcessState.DELIVER_SUCCESS.getProcessStateNum() ? 1 : 0)
                            .build();
                })
                .toList();
    }

    // select문은 @Transactional 필요 x(<- 어노테이션은 rollback이 있을 수 있는 곳만)

    /**
     * iorder : 1
     * iorder에 종속된 idetails : N
     * idetails가 갖고있는 iproduct : 1
     */
    public List<OrderDetailsListVo> orderDetailsList(OrderSmallFilterDto dto, Pageable pageable) {
        return adminOrderRepository.orderDetailsList(dto, pageable)
                .stream()
                .map(orderItem -> {
                    List<OrderProductVo> orderProductVoList = adminOrderDetailsRepository.findAll(orderItem.getIorder()).stream()
                            .map(productItem -> OrderProductVo
                                    .builder()
                                    .repPic(productItem.getProductEntity().getRepPic())
                                    .productNm(productItem.getProductEntity().getProductNm())
                                    .cnt(productItem.getProductCnt())
                                    .processState(productItem.getOrderEntity().getProcessState())
                                    .amount(productItem.getProductEntity().getPrice())
                                    .build())
                            .toList();

                    return OrderDetailsListVo
                            .builder()
                            .iorder(orderItem.getIorder().intValue())
                            .orderedAt(orderItem.getCreatedAt().toString())
                            .products(orderProductVoList)
                            .ordered(orderItem.getUserEntity().getNm())
                            .recipient(orderItem.getUserEntity().getNm())
                            .totalAmount(orderProductVoList.stream()
                                    .mapToInt(OrderProductVo::getAmount)
                                    .sum())
                            .payCategory(orderItem.getOrderPaymentOptionEntity().getIpaymentOption().intValue())
                            .buyComfirmFl(0) // 구매 확정 여부(자동화 추가)
                            .build();
                })
                .toList();
    }

    public List<OrderDeleteVo> orderDeleteList(OrderSmallFilterDto dto, Pageable pageable) {
        return adminOrderRepository.orderDeleteList(dto, pageable)
                .stream()
                .map(orderItem -> {
                    List<OrderProductVo> orderProductVoList = adminOrderDetailsRepository.findAll(orderItem.getIorder()).stream()
                            .map(productItem -> OrderProductVo
                                    .builder()
                                    .repPic(productItem.getProductEntity().getRepPic())
                                    .productNm(productItem.getProductEntity().getProductNm())
                                    .cnt(productItem.getProductCnt())
                                    .processState(productItem.getOrderEntity().getProcessState())
                                    .amount(productItem.getProductEntity().getPrice())
                                    .build())
                            .toList();

                    return OrderDeleteVo
                            .builder()
                            .iorder(orderItem.getIorder().intValue())
                            .orderedAt(orderItem.getCreatedAt().toString())
                            .products(orderProductVoList)
                            .deletedAt(orderItem.getDeletedAt().toString())
                            .ordered(orderItem.getUserEntity().getNm())
                            .totalAmount(orderItem.getTotalPrice())
                            .payCategory(orderItem.getOrderPaymentOptionEntity().getIpaymentOption().intValue())
                            .build();
                })
                .toList();
    }

    public List<OrderRefundListVo> orderRefundList(OrderSmallFilterDto dto, Pageable pageable) {
        return adminOrderRepository.orderRefundList(dto, pageable)
                .stream()
                .map(item -> OrderRefundListVo
                        .builder()
                        .iorder(item.getOrderDetailsEntity().getOrderEntity().getIorder().intValue())
                        .orderedAt(item.getOrderDetailsEntity().getOrderEntity().getCreatedAt().toString())
                        .repPic(item.getOrderDetailsEntity().getProductEntity().getRepPic())
                        .productNm(item.getOrderDetailsEntity().getProductEntity().getProductNm())
                        .cnt(item.getRefundCnt())
                        .productPrice(item.getRefundPrice())
                        .processState(item.getOrderDetailsEntity().getOrderEntity().getProcessState())
                        .refundedAt(item.getCreatedAt().toString())
                        .ordered(item.getOrderDetailsEntity().getOrderEntity().getUserEntity().getNm())
                        .build())
                .toList();
    }

    public List<OrderMemoListVo> adminMemoList(OrderMemoListDto dto, Pageable pageable) {
        return adminOrderRepository.adminMemoList(dto, pageable)
                .stream()
                .map(item ->
                        OrderMemoListVo
                                .builder()
                                .iorder(item.getIorder().intValue())
                                .orderedAt(item.getCreatedAt().toString())
                                .ordered(item.getAddressNm())
                                .processState(item.getProcessState())
                                .memo(item.getAdminMemo())
                                .build()
                )
                .toList();
    }

    public List<OrderDetailsVo> orderDetails(int iorder) {
        return adminOrderRepository.orderDetails(iorder)
                .stream()
                .map(item -> {
                    List<OrderProductVo> products = adminOrderDetailsRepository.findAll(iorder).stream()
                            .map(productItem -> OrderProductVo
                                    .builder()
                                    .repPic(productItem.getProductEntity().getRepPic())
                                    .productNm(productItem.getProductEntity().getProductNm())
                                    .cnt(productItem.getProductCnt())
                                    .processState(productItem.getOrderEntity().getProcessState())
                                    .amount(productItem.getProductEntity().getPrice())
                                    .refundFl(productItem.getRefundFl())
                                    .build())
                            .toList();

                    int deleteAmount = getDeleteAmount(item.getDeleteFl(), item.getTotalPrice());
                    int refundAmount = getRefundAmount(products);

                    return OrderDetailsVo
                            .builder()
                            .products(products)
                            .productAmount(item.getTotalPrice())
                            .deleteAmount(deleteAmount)
                            .refundAmount(refundAmount)
                            .totalAmount(refundAmount != 0 ? (item.getTotalPrice() - refundAmount) : item.getTotalPrice() - deleteAmount)
                            .iorder(item.getIorder().intValue())
                            .orderedAt(item.getCreatedAt().toString())
                            .payCategory(item.getOrderPaymentOptionEntity().getIpaymentOption().intValue())
                            .processState(item.getProcessState())
                            .ordered(item.getUserEntity().getNm())
                            .orderedEmail(item.getUserEntity().getEmail())
                            .orderedPhoneNumber(item.getPhoneNumber())
                            .recipient(item.getAddressNm())
                            .recipientPhoneNumber(item.getPhoneNumber())
                            .address(item.getUserAddressEntity().getAddress() + " " + item.getUserAddressEntity().getAddressDetail())
                            .adminMemo(item.getAdminMemo())
                            .build();
                })
                .toList();
    }

    private int getDeleteAmount(int deleteFl, int totalAmount) {
        return deleteFl == 1 ? totalAmount : 0;
    }

    private int getRefundAmount(List<OrderProductVo> products) {
        int refundAmount = 0;
        for (OrderProductVo product : products) {
            if (product.getRefundFl() == 1) {
                refundAmount += product.getAmount();
            }
        }
        return refundAmount;
    }

    private boolean processStateCheck(int iorder, int processState) {
        boolean result = false;
        switch (iorder) {
            case 1 -> result = processState == ProcessState.DELIVER_IN_PROGRESS.getProcessStateNum();
            case 2 -> result = processState == ProcessState.ON_DELIVERY.getProcessStateNum();
            case 3 -> result = processState == ProcessState.DELIVER_SUCCESS.getProcessStateNum();
            case 5 ->
                    result = processState == ProcessState.BEFORE_DEPOSIT.getProcessStateNum() || processState == ProcessState.DELIVER_IN_PROGRESS.getProcessStateNum();
        }
        return result;
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
