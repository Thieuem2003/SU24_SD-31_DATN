package com.backend.controller;

import com.backend.dto.request.color.ColorRequest;
import com.backend.dto.request.color.ColorRequestUpdate;
import com.backend.dto.request.material.MaterialRequest;
import com.backend.dto.response.shoeDetail.ColorResponse;
import com.backend.service.IColorService;
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
@RequestMapping("/admin/dashboard/mau-sac")
public class ColorController {

    @Autowired
    private IColorService iColorService;

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
        Page<ColorResponse> responses = iColorService.pageColor(pageNo, 5);
        model.addAttribute("listMau", responses);
        model.addAttribute("size", responses.getSize());
        model.addAttribute("totalPages", responses.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "admin/mau_sac/trang_chu_mau_sac";
    }

    @GetMapping("/view-add")
    public String viewAddBrand(Model model) {
        model.addAttribute("mauSac", new MaterialRequest());
        return "admin/mau_sac/view_add_mau_sac";
    }

    @PostMapping("/add")
    public String addNewBrand(@Valid @ModelAttribute("mauSac") ColorRequest request, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("mauSac", new ColorRequest());
            return "admin/mau_sac/view_add_mau_sac";
        }
        String successMessage = iColorService.addColor(request);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/mau-sac/hien-thi";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable("id") Long id, Model model) {
        ColorResponse response = iColorService.getById(id);
        model.addAttribute("mauSac", response);
        return "admin/mau_sac/view_update_mau_sac";
    }

    @PostMapping("/update")
    public String updateBrand(@Valid @ModelAttribute("mauSac") ColorRequestUpdate requestUpdate, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("mauSac", requestUpdate);
            return "admin/mau_sac/view_update_mau_sac";
        }
        String successMessage = iColorService.updateColor(requestUpdate);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/mau-sac/hien-thi";
    }

    @PostMapping("/delete/{id}")
    public String deleteBrand(@PathVariable("id") Long id, HttpSession session) {
        String successMessage = iColorService.deleteColor(id);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/mau-sac/hien-thi";
    }
}
