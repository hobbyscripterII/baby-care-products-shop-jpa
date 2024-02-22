package com.baby.babycareproductsshop.admin.order;

import com.baby.babycareproductsshop.admin.model.OrderBatchProcessDto;
import com.baby.babycareproductsshop.common.ResVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/admin/order")
@RequiredArgsConstructor
public class AdminOrderController {
    private final AdminOrderService service;

    @PutMapping
    public ResVo changeProcessState(@RequestBody OrderBatchProcessDto dto) {
        log.info("dto.getIorders = {}", dto.getIorders());
//        return null;
        return service.changeProcessState(dto);
    }
}
