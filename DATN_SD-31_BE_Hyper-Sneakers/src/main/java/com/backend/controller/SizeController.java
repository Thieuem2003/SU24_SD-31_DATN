package com.backend.controller;

import com.backend.dto.request.size.SizeRequest;
import com.backend.dto.request.size.SizeRequestUpdate;
import com.backend.dto.response.shoeDetail.SizeRespose;
import com.backend.service.ISizeService;
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
@RequestMapping("/admin/dashboard/kich-thuoc")
public class SizeController {
    @Autowired
    private ISizeService iSizeService;

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
        Page<SizeRespose> sizeResposePage = iSizeService.pageSize(pageNo, 5);
        model.addAttribute("listSize", sizeResposePage);
        model.addAttribute("size", sizeResposePage.getSize());
        model.addAttribute("totalPages", sizeResposePage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "admin/kich_thuoc/trang_chu_kich_thuoc";
    }

    @GetMapping("/view-add")
    public String viewAddSole(Model model) {
        model.addAttribute("kichThuoc", new SizeRequest());
        return "admin/kich_thuoc/view_add_kich_thuoc";
    }

    @PostMapping("/add")
    public String addNewSole(@Valid @ModelAttribute("kichThuoc") SizeRequest sizeRequest, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("kichThuoc", new SizeRequest());
            return "admin/kich_thuoc/view_add_kich_thuoc";
        }
        String successMessage = iSizeService.addSize(sizeRequest);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/kich-thuoc/hien-thi";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable("id") Long id, Model model) {
        SizeRespose sizeRespose = iSizeService.getOne(id);
        model.addAttribute("kichThuoc", sizeRespose);
        return "admin/kich_thuoc/view_update_kich_thuoc";
    }

    @PostMapping("/update")
    public String updateSole(@Valid @ModelAttribute("kichThuoc") SizeRequestUpdate sizeRequestUpdate, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("kichThuoc", sizeRequestUpdate);
            return "admin/kich_thuoc/view_update_kich_thuoc";
        }
        String successMessage = iSizeService.updateSize(sizeRequestUpdate);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/kich-thuoc/hien-thi";
    }

    @PostMapping("/delete/{id}")
    public String deleteBrand(@PathVariable("id") Long id, HttpSession session) {
        String successMessage = iSizeService.deleteSize(id);
        session.setAttribute("successMessage", successMessage);
        return "redirect:/admin/dashboard/kich-thuoc/hien-thi";
    }
}
