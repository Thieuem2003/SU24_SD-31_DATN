package com.backend.dto.request.size;

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
public class SizeRequestUpdate {

    private Long id;

    private Integer name;

    private Date createdAt;

    private Date updatedAt;

    private Integer status;
}
