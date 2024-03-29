package com.baby.babycareproductsshop.review;

import com.baby.babycareproductsshop.common.ResVo;
import com.baby.babycareproductsshop.exception.AuthErrorCode;
import com.baby.babycareproductsshop.exception.RestApiException;
import com.baby.babycareproductsshop.review.model.ReviewInsDto;
import com.baby.babycareproductsshop.review.model.ReviewSelVo;
import com.baby.babycareproductsshop.security.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;


@WebMvcTest(controllers = ReviewController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class))
class ReviewControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper om;

    @MockBean private ReviewService service;

    @DisplayName("POST / 리뷰 등록 API 테스트")
    @Test
    @WithMockUser
    void insReview() throws Exception {

        final String fileName = "testImage1";
        final String fileName2 = "testImage2";
        final String contentType = ".jpg";

        ReviewInsDto dto = new ReviewInsDto();
        dto.setIproduct(25);

        List<MultipartFile> files = new ArrayList<>();
        MultipartFile multipartFile1 =
                new MockMultipartFile(fileName + contentType, "test file".getBytes(StandardCharsets.UTF_8) );
        MultipartFile multipartFile2 =
                new MockMultipartFile(fileName2 + contentType,  "test file2".getBytes(StandardCharsets.UTF_8) );
        files.add(multipartFile1);
        files.add(multipartFile2);


        dto.setPics(files);
        ResVo vo = new ResVo(1);
        String json = om.writeValueAsString(dto);
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/api/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf())

        )
                .andExpect(status().isOk())
                .andExpect(content().string(om.writeValueAsString(vo)))
                .andDo(print());

        verify(service).insReview(any());
    }


    @DisplayName("GET / 리뷰 목록 API 테스트")
    @Test
    @WithMockUser
    void getReview() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "1");
        params.add("iuser","19");

        List<ReviewSelVo> list = new ArrayList<>();
        List<String> pics = new ArrayList<>();
        String str1 = "abc.jpg";
        String str2 = "def.jpg";
        pics.add(str1);
        pics.add(str2);

        List<String> pics2 = Arrays.stream(new String[]{"a.jpg","b.jpg"}).toList();

//        list.add(new ReviewSelVo(1, "nm", 1, "cont", "pic","date", pics));
//        list.add(new ReviewSelVo(2, "nm2", 2, "cont2", "pic2", "date ", pics2));
//        given(service.getReview(any())).willReturn(list);
//
//        mvc.perform(
//                MockMvcRequestBuilders
//                        .get("/api/review")
//                        .params(params)
//                        .with(csrf())
//             )
//                .andExpect(status().isOk())
//                .andExpect(content().string(om.writeValueAsString(list)))
//                .andDo(print());
//        verify(service).getReview(any());
    }

    @DisplayName("DELETE / 리뷰 삭제 API 테스트")
    @Test
    @WithMockUser
    void delReview() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("ireview","75");
        params.add("iuser","19");

        ResVo vo = new ResVo(1);
        given(service.delReview(any())).willReturn(vo);

        mvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/review")
                        .params(params)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().string(om.writeValueAsString(vo)))
                .andDo(print());
        verify(service).delReview(any());
    }
}