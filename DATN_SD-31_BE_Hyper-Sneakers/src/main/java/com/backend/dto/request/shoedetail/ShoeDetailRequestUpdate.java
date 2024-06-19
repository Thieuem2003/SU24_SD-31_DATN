package com.backend.dto.request.shoedetail;

import com.backend.entity.Color;
import com.backend.entity.Shoe;
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
public class ShoeDetailRequestUpdate {
    private Long id;

    private Shoe shoe;

    private Color color;

    private BigDecimal priceInput;

    private Integer quantity;

    private Integer status;
}
