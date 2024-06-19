package com.backend.controller;

import com.backend.dto.request.category.CategoryRequest;
import com.backend.dto.request.category.CategoryRequestUpdate;
import com.backend.dto.response.shoeDetail.CategoryResponse;
import com.backend.service.ICategoryService;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashboard/category")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping("/hien-thi")
    public String getAllBrand(Model model, HttpSession session) {
        if (session.getAttribute("successMessage") != null) {
            String successMessage = (String) session.getAttribute("successMessage");
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }
        return pageMaterial(0, model);
    }

    @GetMapping("/page/{pageNo}")
    public String pageMaterial(@PathVariable("pageNo") Integer pageNo, Model model) {
        Page<CategoryResponse> responses = iCategoryService.getByPage(pageNo, 5);
        model.addAttribute("listCate", responses);
        model.addAttribute("size", responses.getSize());
        model.addAttribute("totalPages", responses.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "admin/loai_san_pham/trang_chu_loai_san_pham";
    }

    @GetMapping("/view-add")
    public String viewAddBrand(Model model) {
        model.addAttribute("category", new CategoryRequest());
        return "admin/loai_san_pham/view_add_loai_san_pham";
    }

    @PostMapping("/add")
    public String addNewBrand(@Valid @ModelAttribute("category") CategoryRequest request, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("category", new CategoryRequest());
            return "admin/loai_san_pham/view_add_loai_san_pham";
        }
        String successMessage = iCategoryService.addCategory(request);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/category/hien-thi";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable("id") Long id, Model model) {
        CategoryResponse response = iCategoryService.getById(id);
        model.addAttribute("category", response);
        return "admin/loai_san_pham/view_update_loai_san_pham";
    }

    @PostMapping("/update")
    public String updateBrand(@Valid @ModelAttribute("mauSac") CategoryRequestUpdate requestUpdate, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("category", requestUpdate);
            return "admin/loai_san_pham/view_update_loai_san_pham";
        }
        String successMessage = iCategoryService.updateCategory(requestUpdate);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/category/hien-thi";
    }

    @PostMapping("/delete/{id}")
    public String deleteBrand(@PathVariable("id") Long id, HttpSession session) {
        String successMessage = iCategoryService.deleteCategory(id);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/category/hien-thi";
    }
}
