package com.backend.service.impl;

import com.backend.dto.request.shoe.ShoeRequest;
import com.backend.dto.request.shoe.ShoeRequestUpdate;
import com.backend.dto.response.ImageResponse;
import com.backend.dto.response.ShoeDetailResponse;
import com.backend.dto.response.ShoeResponse;
import com.backend.entity.Color;
import com.backend.entity.Image;
import com.backend.entity.Shoe;
import com.backend.entity.ShoeDetail;
import com.backend.entity.Size;
import com.backend.repository.ImageRepository;
import com.backend.repository.ShoeDetailRepository;
import com.backend.repository.ShoeRepository;
import com.backend.service.IShoeDetailService;
import com.backend.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoeDetailServiceImpl implements IShoeDetailService {

    @Autowired
    private ShoeRepository shoeRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ShoeDetailRepository shoeDetailRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final ImageUploadService imageUploadService;


    @Override
    public Page<ShoeResponse> pageShoe(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Shoe> pageShoe = shoeRepository.findAll(pageable);
        return pageShoe.map(shoe -> {
            String imageUrl = null;
            if (!shoe.getImages().isEmpty()) {
                imageUrl = shoe.getImages().get(0).getImgUrl();
            }
            return ShoeResponse.builder()
                    .id(shoe.getId())
                    .category(shoe.getCategory())
                    .material(shoe.getMaterial())
                    .sole(shoe.getSole())
                    .brand(shoe.getBrand())
                    .code(shoe.getCode())
                    .name(shoe.getName())
                    .description(shoe.getDescription())
                    .shoeHeight(shoe.getShoeHeight())
                    .shoeLength(shoe.getShoeLength())
                    .shoeWidth(shoe.getShoeWidth())
                    .status(shoe.getStatus())
                    .imageUrl(imageUrl) // Gán đường dẫn ảnh vào trường imageUrl
                    .build();
        });
    }

    @Override
    public List<ShoeDetailResponse> listShoeDetailPending() {
        List<ShoeDetail> detailListPending = shoeDetailRepository.getShoeDetailPending();
        return convertToRes(detailListPending);
    }

    @Override
    public List<ShoeDetailResponse> listShoeDetailActive() {
        List<ShoeDetail> detailListActive = shoeDetailRepository.getAllShoeDetailActive();
        return convertToRes(detailListActive);
    }

    @Override
    public List<ShoeDetailResponse> listShoeDetailByShoe(Long shoeId) {
        Shoe shoe = shoeRepository.findById(shoeId).get();
        List<ShoeDetail> detailListByShoe = shoeDetailRepository.getShoeDetailByShoe(shoe);
        return convertToRes(detailListByShoe);
    }

    @Override
    public List<ShoeResponse> getAllShoes() {
        List<Shoe> shoeList = shoeRepository.findAll();
        return shoeList.stream().map(shoe -> modelMapper.map(shoe, ShoeResponse.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteShoeDetail(Long shoeId) {
        shoeDetailRepository.deleteById(shoeId);
    }

    @Override
    public void deleteShoe(Long shoeId) {
        Shoe shoe = shoeRepository.findById(shoeId).get();
        shoe.setStatus(0);
        shoeRepository.save(shoe);
    }

    @Override
    public ShoeResponse getByShoeId(Long shoeId) {
        Shoe shoe = shoeRepository.findById(shoeId).get();
        return modelMapper.map(shoe, ShoeResponse.class);
    }

    @Override
    public void activeShoe(Long shoeId) {
        Shoe shoe = shoeRepository.findById(shoeId).get();
        shoe.setStatus(1);
        shoeRepository.save(shoe);
    }

    @Override
    public List<ImageResponse> imageResponseList(Long shoeId) {
        List<Image> imageList = imageRepository.getImageByShoe(shoeRepository.findById(shoeId).get());
        return imageList.stream().map(image -> modelMapper.map(image, ImageResponse.class)).collect(Collectors.toList());
    }

    @Override
    public void updateQuantityAndPriceShoeDetail(Long id, Integer quantity, BigDecimal price) {
        ShoeDetail shoeDetail = shoeDetailRepository.findById(id).get();
        shoeDetail.setQuantity(quantity);
        shoeDetail.setPriceInput(price);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        shoeDetail.setUpdatedAt(date);
        shoeDetail.setStatus(1);
        shoeDetailRepository.save(shoeDetail);
    }

    // Voi san pham co status 1 la active, 0 la disable
    // Voi san pham chi tiet co status 0 la can cap nhat<aka pending>, 1 laf active, 2 la disable
    @Override
    public void addShoeAndShoeDetail(ShoeRequest shoeRequest, List<Size> sizeList, List<Color> colorList, MultipartFile[] files, Integer price) {
        Shoe shoe = new Shoe();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        shoe.setShoeHeight(shoeRequest.getShoeHeight());
        shoe.setShoeWidth(shoeRequest.getShoeWidth());
        shoe.setShoeLength(shoeRequest.getShoeLength());
        shoe.setName(shoeRequest.getName());
        shoe.setDescription(shoeRequest.getDescription());
        shoe.setCode(shoeRequest.getCode());
        shoe.setBrand(shoeRequest.getBrand());
        shoe.setMaterial(shoeRequest.getMaterial());
        shoe.setCategory(shoeRequest.getCategory());
        shoe.setSole(shoeRequest.getSole());
        shoe.setCreatedAt(date);
        shoe.setUpdatedAt(date);
        shoe.setStatus(shoeRequest.getStatus());
        Shoe savedShoe = shoeRepository.save(shoe);

        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = imageUploadService.uploadImage(file, savedShoe);
                images.add(image);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Lỗi up ảnh");
            }
        }
        imageRepository.saveAll(images);

        for (Size size : sizeList) {
            for (Color color : colorList) {
                ShoeDetail shoeDetail = new ShoeDetail();
                shoeDetail.setShoe(savedShoe);
                shoeDetail.setPriceInput(BigDecimal.valueOf(price));
                shoeDetail.setQuantity(1);
                shoeDetail.setStatus(0);
                shoeDetail.setColor(color);
                shoeDetail.setSize(size);
                shoeDetail.setCreatedAt(date);
                shoeDetail.setUpdatedAt(date);
                shoeDetailRepository.save(shoeDetail);
            }
        }
    }

    @Override
    public String updateShoe(ShoeRequestUpdate requestUpdate, MultipartFile[] files) {
        Shoe shoe = shoeRepository.findById(requestUpdate.getId()).get();
        shoe.setStatus(requestUpdate.getStatus());
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        shoe.setUpdatedAt(date);
        shoe.setShoeHeight(requestUpdate.getShoeHeight());
        shoe.setShoeWidth(requestUpdate.getShoeWidth());
        shoe.setShoeLength(requestUpdate.getShoeLength());
        shoe.setName(requestUpdate.getName());
        shoe.setDescription(requestUpdate.getDescription());
        shoe.setCode(requestUpdate.getCode());
        shoe.setBrand(requestUpdate.getBrand());
        shoe.setMaterial(requestUpdate.getMaterial());
        shoe.setCategory(requestUpdate.getCategory());
        shoe.setSole(requestUpdate.getSole());
        shoeRepository.save(shoe);
        if (files != null || files.length > 0) {
            imageRepository.deleteAllByShoe(shoe);
            List<Image> images = new ArrayList<>();
            for (MultipartFile file : files) {
                try {
                    Image image = imageUploadService.uploadImage(file, shoe);
                    images.add(image);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Lỗi up ảnh");
                }
            }
            imageRepository.saveAll(images);
        } else {
            return "Update ok";
        }
        return "Update ok";
    }

    private List<ShoeDetailResponse> convertToRes(List<ShoeDetail> detailList) {
        return detailList.stream().map(detail ->
                ShoeDetailResponse.builder()
                        .id(detail.getId())
                        .shoeName(detail.getShoe().getName())
                        .quantity(detail.getQuantity())
                        .color(detail.getColor().getName())
                        .size(detail.getSize().getName())
                        .priceInput(detail.getPriceInput())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public Page<ShoeResponse> searchAndPageShoe(String searchNameOrMa, int pageNo, int pageSize) {
        // Tạo đối tượng PageRequest cho việc phân trang
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

        // Gọi repository để thực hiện tìm kiếm và phân trang
        Page<Shoe> shoesPage = shoeRepository.searchByNameOrCode(searchNameOrMa, pageRequest);

        // Chuyển đổi từ Page<Shoe> sang Page<ShoeResponse> để trả về
        Page<ShoeResponse> shoeResponsePage = shoesPage.map(shoe -> convertToShoeResponse(shoe));

        return shoeResponsePage;
    }

    private ShoeResponse convertToShoeResponse(Shoe shoe) {
        return ShoeResponse.builder()
                .id(shoe.getId())
                .category(shoe.getCategory())
                .material(shoe.getMaterial())
                .sole(shoe.getSole())
                .brand(shoe.getBrand())
                .code(shoe.getCode())
                .name(shoe.getName())
                .description(shoe.getDescription())
                .shoeHeight(shoe.getShoeHeight())
                .shoeLength(shoe.getShoeLength())
                .shoeWidth(shoe.getShoeWidth())
                .status(shoe.getStatus())
                .imageUrl(shoe.getImages().isEmpty() ? null : shoe.getImages().get(0).getImgUrl())
                .build();
    }

}
