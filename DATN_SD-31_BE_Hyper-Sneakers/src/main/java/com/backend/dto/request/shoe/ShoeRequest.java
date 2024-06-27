package com.backend.dto.request.shoe;

import com.backend.entity.Brand;
import com.backend.entity.Category;
import com.backend.entity.Image;
import com.backend.entity.Material;
import com.backend.entity.Sole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
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
