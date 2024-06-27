package com.backend.dto.request.shoedetail;

import com.backend.entity.Color;
import com.backend.entity.Shoe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoeDetailRequest {
    private Shoe shoe;

    private Color color;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal priceInput;

    @NotNull(message = "Quantity is mandatory")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;

    @NotNull(message = "Status is mandatory")
    @Min(value = 0, message = "Status should not be less than 0")
    @Max(value = 1, message = "Status should not be greater than 1")
    private Integer status;
}
