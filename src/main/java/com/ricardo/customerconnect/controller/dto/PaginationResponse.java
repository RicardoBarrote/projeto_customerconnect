package com.ricardo.customerconnect.controller.dto;

public record PaginationResponse(Integer page,
                                 Integer pageSize,
                                 Long totalElements,
                                 Integer totalPage) {
}
