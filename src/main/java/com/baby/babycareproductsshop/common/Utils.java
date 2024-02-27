package com.baby.babycareproductsshop.common;

import com.baby.babycareproductsshop.admin.order.model.AdminSelOrderStatisticsDto;
import com.baby.babycareproductsshop.admin.order.model.StatisticsVo;

import java.time.LocalDateTime;

public class Utils {
    public static boolean isNotNull(String str) {
        return str == null ? false : true;
    }

    public static boolean isNotNull(int num) {
        return num == 0 ? false : true;
    }

    public static boolean isNotNull(long num) {
        return num == 0 ? false : true;
    }

    public static boolean isNotNull(Object object) {
        return object == null ? false : true;
    }

    public static String getDate(AdminSelOrderStatisticsDto dto, StatisticsVo vo) {
        StringBuilder sb = new StringBuilder();
        return dto.getMonth() != 0 ?
                vo.getCreatedAt().toLocalDate().toString()
                : dto.getYear() != 0 ?
                sb.append(vo.getCreatedAt().getYear()).append("-").append(vo.getCreatedAt().getMonthValue()).toString()
                : sb.append(vo.getCreatedAt().getYear()).toString();
    }

    public int getDaysOrMonths(AdminSelOrderStatisticsDto dto) {

        return 0;
    }
}
