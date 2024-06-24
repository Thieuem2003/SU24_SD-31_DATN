package com.backend.controller;

import com.backend.dto.request.material.MaterialRequest;
import com.backend.dto.request.material.MaterialRequestUpdate;
import com.backend.dto.response.MaterialResponse;
import com.backend.service.IMaterialService;
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
@RequestMapping("/admin/dashboard/chat-lieu")
public class MaterialController {
    @Autowired
    private IMaterialService iMaterialService;

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
        Page<MaterialResponse> materialResponsePage = iMaterialService.pageMaterial(pageNo, 5);
        model.addAttribute("listChatLieu", materialResponsePage);
        model.addAttribute("size", materialResponsePage.getSize());
        model.addAttribute("totalPages", materialResponsePage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "admin/chat_lieu/trang_chu_chat_lieu";
    }

    @GetMapping("/view-add")
    public String viewAddBrand(Model model) {
        model.addAttribute("chatLieu", new MaterialRequest());
        return "admin/chat_lieu/view_add_chat_lieu";
    }

    @PostMapping("/add")
    public String addNewBrand(@Valid @ModelAttribute("chatLieu") MaterialRequest request, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("chatLieu", new MaterialRequest());
            return "admin/chat_lieu/view_add_chat_lieu";
        }
        String successMessage = iMaterialService.addNewMaterial(request);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/chat-lieu/hien-thi";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable("id") Long id, Model model) {
        MaterialResponse materialResponse = iMaterialService.getOne(id);
        model.addAttribute("chatLieu", materialResponse);
        return "admin/chat_lieu/view_update_chat_lieu";
    }

    @PostMapping("/update")
    public String updateBrand(@Valid @ModelAttribute("chatLieu") MaterialRequestUpdate materialRequestUpdate, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("chatLieu", materialRequestUpdate);
            return "admin/chat_lieu/view_update_chat_lieu";
        }
        String successMessage = iMaterialService.updateMaterial(materialRequestUpdate);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/chat-lieu/hien-thi";
    }

    @PostMapping("/delete/{id}")
    public String deleteBrand(@PathVariable("id") Long id, HttpSession session) {
        String successMessage = iMaterialService.deleteMaterial(id);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/chat-lieu/hien-thi";
    }
}
