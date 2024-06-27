package com.backend.controller;

import com.backend.dto.request.sole.SoleRequest;
import com.backend.dto.request.sole.SoleRequestUpdate;
import com.backend.dto.response.SoleResponse;
import com.backend.service.ISoleService;
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
@RequestMapping("/admin/dashboard/de-giay")
public class SoleController {
    @Autowired
    private ISoleService iSoleService;

    @GetMapping("/hien-thi")
    public String getAllBrand(Model model, HttpSession session) {
        if (session.getAttribute("successMessage") != null) {
            String successMessage = (String) session.getAttribute("successMessage");
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }
        return pageSole(0, model);
    }

    @GetMapping("/page/{pageNo}")
    public String pageSole(@PathVariable("pageNo") Integer pageNo, Model model) {
        Page<SoleResponse> soleResponsePage = iSoleService.pageSole(pageNo, 5);
        model.addAttribute("listDeGiay", soleResponsePage);
        model.addAttribute("size", soleResponsePage.getSize());
        model.addAttribute("totalPages", soleResponsePage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "admin/de-giay/trang_chu_de_giay";
    }

    @GetMapping("/view-add")
    public String viewAddSole(Model model) {
        model.addAttribute("deGiay", new SoleRequest());
        return "admin/de-giay/view_add_de_giay";
    }

    @PostMapping("/add")
    public String addNewSole(@Valid @ModelAttribute("deGiay") SoleRequest request, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("deGiay", new SoleRequest());
            return "admin/de-giay/view_add_de_giay";
        }
        String successMessage = iSoleService.addNewSole(request);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/de-giay/hien-thi";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable("id") Long id, Model model) {
        SoleResponse soleResponse = iSoleService.getOne(id);
        model.addAttribute("deGiay", soleResponse);
        return "admin/de-giay/view_update_de_giay";
    }

    @PostMapping("/update")
    public String updateSole(@Valid @ModelAttribute("deGiay") SoleRequestUpdate soleRequestUpdate, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("deGiay", soleRequestUpdate);
            return "admin/de-giay/view_update_de_giay";
        }
        String successMessage = iSoleService.updateSole(soleRequestUpdate);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/de-giay/hien-thi";
    }

    @PostMapping("/delete/{id}")
    public String deleteBrand(@PathVariable("id") Long id, HttpSession session) {
        String successMessage = iSoleService.deleteSole(id);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/de-giay/hien-thi";
    }
}
