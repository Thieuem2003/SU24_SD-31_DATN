package com.backend.controller;

import com.backend.dto.request.brand.BrandRequest;
import com.backend.dto.request.category.CategoryRequest;
import com.backend.dto.request.color.ColorRequest;
import com.backend.dto.request.material.MaterialRequest;
import com.backend.dto.request.shoe.ShoeRequest;
import com.backend.dto.request.shoe.ShoeRequestUpdate;
import com.backend.dto.request.size.SizeRequest;
import com.backend.dto.request.sole.SoleRequest;
import com.backend.dto.response.ShoeResponse;
import com.backend.entity.Color;
import com.backend.entity.ShoeDetail;
import com.backend.entity.Size;
import com.backend.service.IBrandService;
import com.backend.service.ICategoryService;
import com.backend.service.IColorService;
import com.backend.service.IMaterialService;
import com.backend.service.IShoeDetailService;
import com.backend.service.ISizeService;
import com.backend.service.ISoleService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class ShoeDetailController {
    @Autowired
    private IShoeDetailService iShoeDetailService;

    @Autowired
    private ICategoryService iCategoryService;

    @Autowired
    private ISoleService iSoleService;

    @Autowired
    private IMaterialService iMaterialService;

    @Autowired
    private IBrandService iBrandService;

    @Autowired
    private IColorService iColorService;

    @Autowired
    private ISizeService iSizeService;

    @GetMapping("/admin/dashboard/san-pham/hien-thi")
    public String getAllShoe(Model model, HttpSession session) {
        if (session.getAttribute("successMessage") != null) {
            String successMessage = (String) session.getAttribute("successMessage");
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }
        return pageShoe(model, 0);
    }

    @GetMapping("/admin/dashboard/san-pham/page/{pageNo}")
    public String pageShoe(Model model, @PathVariable("pageNo") Integer pageNo) {
        Page<ShoeResponse> shoeResponsePage = iShoeDetailService.pageShoe(pageNo, 5);
        model.addAttribute("size", shoeResponsePage.getSize());
        model.addAttribute("totalPages", shoeResponsePage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("listSanPham", shoeResponsePage);
        return "admin/san_pham/trang_chu_san_pham";
    }

    @GetMapping("/admin/dashboard/san-pham/view-add")
    public String viewAddShoe(Model model) {

        model.addAttribute("sanPham", new ShoeRequest());
        model.addAttribute("category", new CategoryRequest());
        model.addAttribute("sole", new SoleRequest());
        model.addAttribute("material", new MaterialRequest());
        model.addAttribute("brand", new BrandRequest());
        model.addAttribute("color", new ColorRequest());
        model.addAttribute("size", new SizeRequest());

        model.addAttribute("listCategory", iCategoryService.getAll());
        model.addAttribute("listSole", iSoleService.getAll());
        model.addAttribute("listMaterial", iMaterialService.getAll());
        model.addAttribute("listBrand", iBrandService.getAll());
        model.addAttribute("listSize", iSizeService.getAll());
        model.addAttribute("listColor", iColorService.getAll());
        model.addAttribute("listPending", iShoeDetailService.listShoeDetailPending());
        return "admin/san_pham/view_add_san_pham";
    }

    @PostMapping("/admin/dashboard/san-pham/add-mau-sac")
    public String popupAddColor(@ModelAttribute("color") ColorRequest colorRequest) {
        iColorService.addColor(colorRequest);
        return "redirect:/admin/dashboard/san-pham/view-add";
    }

    @PostMapping("/admin/dashboard/san-pham/add-de-giay")
    public String popupAddSole(@ModelAttribute("sole") SoleRequest soleRequest) {
        soleRequest.setStatus(1);
        iSoleService.addNewSole(soleRequest);
        return "redirect:/admin/dashboard/san-pham/view-add";
    }

    @PostMapping("/admin/dashboard/san-pham/add-chat-lieu")
    public String popupAddMaterial(@ModelAttribute("material") MaterialRequest materialRequest) {
        materialRequest.setStatus(1);
        iMaterialService.addNewMaterial(materialRequest);
        return "redirect:/admin/dashboard/san-pham/view-add";
    }

    @PostMapping("/admin/dashboard/san-pham/add-thuong-hieu")
    public String popupAddBrand(@ModelAttribute("brand") BrandRequest brandRequest) {
        brandRequest.setStatus(1);
        iBrandService.addNewBrand(brandRequest);
        return "redirect:/admin/dashboard/san-pham/view-add";
    }

    @PostMapping("/admin/dashboard/san-pham/add-category")
    public String popupAddCategory(@ModelAttribute("category") CategoryRequest request) {
        request.setStatus(1);
        iCategoryService.addCategory(request);
        return "redirect:/admin/dashboard/san-pham/view-add";
    }

    @PostMapping("/admin/dashboard/san-pham/add-size")
    public String popupAddSize(@ModelAttribute("size") SizeRequest request) {
        request.setStatus(1);
        iSizeService.addSize(request);
        return "redirect:/admin/dashboard/san-pham/view-add";
    }

    @PostMapping("/admin/dashboard/san-pham/add")
    public String addSanPham(@Valid @ModelAttribute("sanPham") ShoeRequest request,
                             BindingResult result, @RequestParam("sizes") List<Size> sizeList,
                             @RequestParam("colors") List<Color> colorList, Model model,
                             @RequestParam("prices") Integer price, @RequestParam("image") MultipartFile[] files) {
        if (sizeList == null || sizeList.isEmpty() || colorList == null || colorList.isEmpty()) {
            return "admin/san_pham/view_add_san_pham";
        }
        if (result.hasErrors()) {
            model.addAttribute("sanPham", new ShoeRequest());
            model.addAttribute("category", new CategoryRequest());
            model.addAttribute("sole", new SoleRequest());
            model.addAttribute("material", new MaterialRequest());
            model.addAttribute("brand", new BrandRequest());
            model.addAttribute("color", new ColorRequest());
            model.addAttribute("size", new SizeRequest());

            model.addAttribute("listCategory", iCategoryService.getAll());
            model.addAttribute("listSole", iSoleService.getAll());
            model.addAttribute("listMaterial", iMaterialService.getAll());
            model.addAttribute("listBrand", iBrandService.getAll());
            model.addAttribute("listSize", iSizeService.getAll());
            model.addAttribute("listColor", iColorService.getAll());
            model.addAttribute("listPending", iShoeDetailService.listShoeDetailPending());
            return "admin/san_pham/view_add_san_pham";
        }
        iShoeDetailService.addShoeAndShoeDetail(request, sizeList, colorList, files, price);
        return "redirect:/admin/dashboard/san-pham/view-add";
    }

    @PostMapping("/admin/dashboard/chi-tiet-san-pham/update-pending")
    public String updatePendingProductDetails(@RequestParam("ids") List<Long> ids,
                                              @RequestParam("soLuongs") List<Integer> quantities,
                                              @RequestParam("priceInput") List<BigDecimal> prices) {
        for (int i = 0; i < ids.size(); i++) {
            Long id = ids.get(i);
            Integer quantity = quantities.get(i);
            BigDecimal price = prices.get(i);
            iShoeDetailService.updateQuantityAndPriceShoeDetail(id, quantity, price);
        }
        return "redirect:/admin/dashboard/san-pham/hien-thi";
    }

    @GetMapping("/admin/dashboard/san-pham/view-update/{id}")
    public String viewUpdate(Model model, @PathVariable("id") Long id) {
        ShoeResponse shoeResponse = iShoeDetailService.getByShoeId(id);

        model.addAttribute("sanPham", shoeResponse);
        model.addAttribute("category", new CategoryRequest());
        model.addAttribute("sole", new SoleRequest());
        model.addAttribute("material", new MaterialRequest());
        model.addAttribute("brand", new BrandRequest());
        model.addAttribute("color", new ColorRequest());
        model.addAttribute("size", new SizeRequest());

        model.addAttribute("listCategory", iCategoryService.getAll());
        model.addAttribute("listSole", iSoleService.getAll());
        model.addAttribute("listMaterial", iMaterialService.getAll());
        model.addAttribute("listBrand", iBrandService.getAll());
        model.addAttribute("listSize", iSizeService.getAll());
        model.addAttribute("listColor", iColorService.getAll());
        model.addAttribute("listDetail", iShoeDetailService.listShoeDetailByShoe(shoeResponse.getId()));
        model.addAttribute("listImage", iShoeDetailService.imageResponseList(shoeResponse.getId()));

        return "admin/san_pham/view_update_san_pham";
    }

    @PostMapping("/admin/dashboard/san-pham/update")
    public String updateSanPham(@Valid @ModelAttribute("sanPham") ShoeRequestUpdate request,
                                BindingResult result, @RequestParam("image") MultipartFile[] files, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("category", new CategoryRequest());
            model.addAttribute("sole", new SoleRequest());
            model.addAttribute("material", new MaterialRequest());
            model.addAttribute("brand", new BrandRequest());
            model.addAttribute("color", new ColorRequest());
            model.addAttribute("size", new SizeRequest());

            model.addAttribute("listCategory", iCategoryService.getAll());
            model.addAttribute("listSole", iSoleService.getAll());
            model.addAttribute("listMaterial", iMaterialService.getAll());
            model.addAttribute("listBrand", iBrandService.getAll());
            model.addAttribute("listSize", iSizeService.getAll());
            model.addAttribute("listColor", iColorService.getAll());
            model.addAttribute("listDetail", iShoeDetailService.listShoeDetailByShoe(request.getId()));
            model.addAttribute("listImage", iShoeDetailService.imageResponseList(request.getId()));

            return "admin/san_pham/view_update_san_pham";
        }
        iShoeDetailService.updateShoe(request, files);
        session.setAttribute("successMessage", "Cập nhập thành công!");
        return "redirect:/admin/dashboard/san-pham/hien-thi";
    }

    @PostMapping("/admin/dashboard/chi-tiet-san-pham/update-detail")
    public String updateDetail(@RequestParam("ids") List<Long> ids,
                               @RequestParam("soLuongs") List<Integer> quantities,
                               @RequestParam("priceInput") List<BigDecimal> prices) {
        for (int i = 0; i < ids.size(); i++) {
            Long id = ids.get(i);
            Integer quantity = quantities.get(i);
            BigDecimal price = prices.get(i);
            iShoeDetailService.updateQuantityAndPriceShoeDetail(id, quantity, price);
        }
        return "redirect:/admin/dashboard/san-pham/hien-thi";
    }

    @GetMapping("admin/dashboard/san-pham/delete/{id}")
    public String deleteShoe(@PathVariable("id") Long id) {
        iShoeDetailService.deleteShoe(id);
        return "redirect:/admin/dashboard/san-pham/hien-thi";
    }
    @GetMapping("admin/dashboard/san-pham/active/{id}")
    public String activeShoe(@PathVariable("id") Long id) {
        iShoeDetailService.activeShoe(id);
        return "redirect:/admin/dashboard/san-pham/hien-thi";
    }
    @GetMapping("admin/dashboard/chi-tiet-san-pham/delete/{id}")
    public String deleteShoeDetail(@PathVariable("id") Long id){
        iShoeDetailService.deleteShoeDetail(id);
        return "redirect:/admin/dashboard/san-pham/view-add";
    }

//    @GetMapping("admin/dashboard/san-pham/search")
//    public String searchShoeDetail(@RequestParam("name") String name, Model model){
//        List<ShoeDetail> shoeDetails = iShoeDetailService.searchShoesByName(name);
//        model.addAttribute("shoeDetails", shoeDetails);
//        return "redirect:/admin/dashboard/san-pham/view-add";
//    }
}
