package com.enigma.inisalesapi.mapper;

import com.enigma.inisalesapi.dto.response.CommonResponse;
import com.enigma.inisalesapi.dto.response.DefaultResponse;
import com.enigma.inisalesapi.dto.response.PagingResponse;
import com.enigma.inisalesapi.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseControllerMapper {
    public static ResponseEntity<?> getResponseEntity(String message, HttpStatus httpStatus, Object object) {
        return ResponseEntity.status(httpStatus)
                .body(DefaultResponse.builder()
                        .statusCode(httpStatus.value())
                        .message(message)
                        .data(object)
                        .build());
    }

    public static ResponseEntity<?> getResponseEntityPaging
            (String message, HttpStatus httpStatus, Object object, PagingResponse pagingResponse) {
        CommonResponse commonResponse = CommonResponse.builder()
                .statusCode(httpStatus.value())
                .message(message)
                .data(object)
                .pagingResponse(pagingResponse)
                .build();
        return ResponseEntity.status(httpStatus).body(commonResponse);
    }

    public static PagingResponse getPagingResponse(Integer page, Integer size, Page<ProductResponse> productResponses) {
        return PagingResponse.builder()
                .currentPage(page)
                .totalPage(productResponses.getTotalPages())
                .size(size)
                .build();
    }
}
