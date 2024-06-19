package com.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ShoeDetailResponse {
    private Long id;
    private String shoeName;
    private Integer size;
    private String color;
    private BigDecimal priceInput;
    private Integer quantity;
}
