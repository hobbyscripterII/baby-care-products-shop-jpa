package com.baby.babycareproductsshop.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
//    private final String path;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final T data;

    public ApiResponse(String code, String message) {
        this(code, message, null);
    }
}
