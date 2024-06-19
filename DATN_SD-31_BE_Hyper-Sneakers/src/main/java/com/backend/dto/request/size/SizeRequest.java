package com.backend.dto.request.size;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SizeRequest {

    @NotNull(message = "Name is mandatory")
    private Integer name;

    @PastOrPresent(message = "Created time must be in the past or present")
    private Date createdAt;

    @PastOrPresent(message = "Updated time must be in the past or present")
    private Date updatedAt;

    @NotNull(message = "Status is mandatory")
    @Min(value = 0, message = "Status should not be less than 0")
    @Max(value = 1, message = "Status should not be greater than 1")
    private Integer status;
}
