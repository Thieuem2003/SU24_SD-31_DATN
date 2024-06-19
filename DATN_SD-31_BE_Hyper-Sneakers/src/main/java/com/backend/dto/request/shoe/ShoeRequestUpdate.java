package com.backend.dto.request.shoe;

import com.backend.entity.Brand;
import com.backend.entity.Category;
import com.backend.entity.Material;
import com.backend.entity.Sole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoeRequestUpdate {
    private Long id;
    private Category category;
    private Material material;
    private Sole sole;
    private Brand brand;

    @NotBlank(message = "Khong duoc de trong")
    private String code;
    @NotBlank(message = "Khong duoc de trong")
    private String name;
    @NotBlank(message = "Khong duoc de trong")
    private String description;
    private Float shoeHeight;
    private Float shoeLength;
    private Float shoeWidth;
    private Integer status;
}
