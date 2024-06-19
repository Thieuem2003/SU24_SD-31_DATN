package com.backend.dto.request.shoe;

import com.backend.entity.Brand;
import com.backend.entity.Category;
import com.backend.entity.Image;
import com.backend.entity.Material;
import com.backend.entity.Sole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoeRequest {
    private Category category;
    private Material material;
    private Sole sole;
    private Brand brand;

    @NotBlank(message = "Code is mandatory")
    private String code;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;

    @NotNull(message = "Shoe height is mandatory")
    private Float shoeHeight;

    @NotNull(message = "Shoe length is mandatory")
    private Float shoeLength;

    @NotNull(message = "Shoe width is mandatory")
    private Float shoeWidth;

    @PastOrPresent(message = "Created time must be in the past or present")
    private Date createdAt;

    @PastOrPresent(message = "Updated time must be in the past or present")
    private Date updatedAt;

    @NotNull(message = "Status is mandatory")
    @Min(value = 0, message = "Status should not be less than 0")
    @Max(value = 1, message = "Status should not be greater than 1")
    private Integer status;

    @OneToMany(mappedBy = "shoe", cascade = CascadeType.ALL)
    private List<Image> images;
}
