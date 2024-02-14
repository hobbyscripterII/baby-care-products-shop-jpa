package com.baby.babycareproductsshop.user;

import com.baby.babycareproductsshop.user.model.UserDelAddressDto;
import com.baby.babycareproductsshop.user.model.UserInsAddressDto;
import com.baby.babycareproductsshop.user.model.UserSelAddressVo;
import com.baby.babycareproductsshop.user.model.UserUpdAddressDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserAddressMapperTest {
    @Autowired
    private UserAddressMapper mapper;

    @Test
    @DisplayName("ins user address test")
    void insUserAddress() {
        UserInsAddressDto dto = new UserInsAddressDto();
        dto.setIuser(38);
        dto.setZipCode("12345");
        dto.setAddress("대구시");
        dto.setAddressDetail("중앙로");

        List<UserSelAddressVo> beforeInsertList = mapper.selUserAddress(dto.getIuser());
        assertEquals(2, beforeInsertList.size());

        int insResult = mapper.insUserAddress(dto);
        List<UserSelAddressVo> afterInsertList = mapper.selUserAddress(dto.getIuser());
        assertEquals(3, afterInsertList.size());
    }

    @Test
    void selUserAddress() {
    }

    @Test
    @DisplayName("upd user address test")
    void updUserAddress() {
        UserUpdAddressDto dto = new UserUpdAddressDto();
        dto.setIaddress(28);
        dto.setIuser(38);
        dto.setZipCode("64213");
        dto.setAddress("경산시");
        dto.setAddressDetail("신상리");


        List<UserSelAddressVo> beforeUpdList = mapper.selUserAddress(dto.getIuser());
        assertEquals(28, beforeUpdList.get(0).getIaddress());
        assertEquals("대구", beforeUpdList.get(0).getAddress());

        int updResult = mapper.updUserAddress(dto);
        List<UserSelAddressVo> afterUpdList = mapper.selUserAddress(dto.getIuser());
        assertEquals(28, afterUpdList.get(0).getIaddress());
        assertEquals("경산시", afterUpdList.get(0).getAddress());

    }

    @Test
    void delUserAddress() {
        UserDelAddressDto dto = new UserDelAddressDto();
        dto.setIaddress(29);
        dto.setIuser(38);

        List<UserSelAddressVo> beforeDelList = mapper.selUserAddress(dto.getIuser());
        assertEquals(2, beforeDelList.size());
        int delResult = mapper.delUserAddress(dto);
        List<UserSelAddressVo> afterDelList = mapper.selUserAddress(dto.getIuser());
        assertEquals(1, afterDelList.size());
    }
}