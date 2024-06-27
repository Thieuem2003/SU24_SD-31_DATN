package com.backend.controller;

import com.backend.dto.request.brand.BrandRequest;
import com.backend.dto.request.brand.BrandRequestUpdate;
import com.backend.dto.response.BrandResponse;
import com.backend.service.IBrandService;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/dashboard/thuong-hieu")
public class BrandController {

    @Autowired
    private IBrandService iBrandService;

    @GetMapping("/hien-thi")
    public String getAllBrand(Model model, HttpSession session) {
        if (session.getAttribute("successMessage") != null) {
            String successMessage = (String) session.getAttribute("successMessage");
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }
        return pageBrand(0, model);
    }

    @GetMapping("/page/{pageNo}")
    public String pageBrand(@PathVariable("pageNo") Integer pageNo, Model model) {
        Page<BrandResponse> brandResponsesPage = iBrandService.pageBrand(pageNo, 5);
        model.addAttribute("listThuongHieu", brandResponsesPage);
        model.addAttribute("size", brandResponsesPage.getSize());
        model.addAttribute("totalPages", brandResponsesPage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "admin/thuong_hieu/trang_chu_thuong_hieu";
    }

    @GetMapping("/view-add")
    public String viewAddBrand(Model model) {
        model.addAttribute("thuongHieu", new BrandRequest());
        return "admin/thuong_hieu/view_add_thuong_hieu";
    }

    @PostMapping("/add")
    public String addNewBrand(@Valid @ModelAttribute("thuongHieu") BrandRequest brandRequest, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("thuongHieu", new BrandRequest());
            return "admin/thuong_hieu/view_add_thuong_hieu";
        }
        String successMessage = iBrandService.addNewBrand(brandRequest);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/thuong-hieu/hien-thi";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable("id") Long id, Model model) {
        BrandResponse brandResponse = iBrandService.getOne(id);
        model.addAttribute("thuongHieu", brandResponse);
        return "admin/thuong_hieu/view_update_thuong_hieu";
    }

    @PostMapping("/update")
    public String updateBrand(@Valid @ModelAttribute("thuongHieu") BrandRequestUpdate brandRequestUpdate, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("thuongHieu", brandRequestUpdate);
            return "admin/thuong_hieu/view_update_thuong_hieu";
        }
        String successMessage = iBrandService.updateBrand(brandRequestUpdate);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/thuong-hieu/hien-thi";
    }

    @PostMapping("/delete/{id}")
    public String deleteBrand(@PathVariable("id") Long id, HttpSession session) {
        String successMessage = iBrandService.deleteBrand(id);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/thuong-hieu/hien-thi";
    }

}
