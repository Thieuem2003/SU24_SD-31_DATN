package com.backend.service;

import com.backend.dto.request.shoe.ShoeRequest;
import com.backend.dto.request.shoe.ShoeRequestUpdate;
import com.backend.dto.response.ImageResponse;
import com.backend.dto.response.ShoeDetailResponse;
import com.backend.dto.response.ShoeResponse;
import com.backend.entity.Color;
import com.backend.entity.ShoeDetail;
import com.backend.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface IShoeDetailService {
    Page<ShoeResponse> pageShoe(Integer pageNo, Integer pageSize);

    List<ShoeDetailResponse> listShoeDetailPending();

    List<ShoeDetailResponse> listShoeDetailActive();

    List<ShoeDetailResponse> listShoeDetailByShoe(Long shoeId);

    List<ShoeResponse> getAllShoes();

    void deleteShoeDetail(Long shoeId);

    void deleteShoe(Long shoeId);

    ShoeResponse getByShoeId(Long shoeId);

    void activeShoe(Long shoeId);

    List<ImageResponse> imageResponseList(Long shoeId);

    String updateShoe(ShoeRequestUpdate requestUpdate, MultipartFile[] files);

    void updateQuantityAndPriceShoeDetail(Long id, Integer quantity, BigDecimal price);

    void addShoeAndShoeDetail(ShoeRequest shoeRequest, List<Size> sizeList, List<Color> colorList, MultipartFile[] files, Integer price);

    Page<ShoeResponse> searchAndPageShoe(String searchNameOrMa, int pageNo, int pageSize);
}
