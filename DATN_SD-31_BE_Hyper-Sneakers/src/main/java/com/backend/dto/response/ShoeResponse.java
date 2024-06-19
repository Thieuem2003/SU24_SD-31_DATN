package com.backend.dto.response;

import com.backend.entity.Brand;
import com.backend.entity.Category;
import com.backend.entity.Material;
import com.backend.entity.Sole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShoeResponse {
    private Long id;

    private Category category;

    private Material material;

    private Sole sole;

    private Brand brand;

    private String code;

    private String name;

    private String description;

    private Float shoeHeight;

    private Float shoeLength;

    private Float shoeWidth;

    private Integer status;

    private String imageUrl;

}
