package com.backend.dto.request.brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandRequest {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name cannot be longer than 255 characters")
    private String name;

    @PastOrPresent(message = "Created time must be in the past or present")
    private Date createdAt;

    @PastOrPresent(message = "Updated time must be in the past or present")
    private Date updatedAt;

    @NotNull(message = "Status is mandatory")
    @Min(value = 0, message = "Status should not be less than 0")
    @Max(value = 1, message = "Status should not be greater than 1")
    private Integer status;
}
