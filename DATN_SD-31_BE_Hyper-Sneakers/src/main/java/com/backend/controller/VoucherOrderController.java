package com.backend.controller;

import com.backend.dto.request.VoucherOrderRequest;
import com.backend.dto.response.VoucherOrderResponse;
import com.backend.entity.VoucherOrder;
import com.backend.service.IVoucherOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/dashboard/voucher_order")
public class VoucherOrderController {

    @Autowired
    private IVoucherOrderService voucherOrderService;

    @GetMapping("/hien_thi")
    public String getAllVouchers(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "") String searchKeyword,
                                 Model model) {
        Page<VoucherOrderResponse> vouchersPage = voucherOrderService.getAllVouchers(PageRequest.of(page, size), searchKeyword);
        model.addAttribute("vouchersPage", vouchersPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("searchKeyword", searchKeyword);
        return "admin/voucher_order/vouchers";
    }

    @GetMapping("/new")
    public String createVoucherForm(Model model) {
        VoucherOrderRequest voucherOrderRequest = new VoucherOrderRequest();
        model.addAttribute("voucherOrderRequest", voucherOrderRequest);
        return "admin/voucher_order/create_voucher";
    }

    @PostMapping
    public String saveVoucher(@ModelAttribute("voucherOrderRequest") VoucherOrderRequest voucherOrderRequest) {
        VoucherOrder voucherOrder = voucherOrderService.convertToVoucherOrder(voucherOrderRequest);
        voucherOrderService.saveVoucher(voucherOrder);
        return "redirect:/admin/dashboard/voucher_order/hien_thi";
    }

    @GetMapping("/edit/{id}")
    public String editVoucherForm(@PathVariable Long id, Model model) {
        VoucherOrderResponse voucherOrderResponse = voucherOrderService.getVoucherById(id).orElseThrow(() -> new IllegalArgumentException("Invalid voucher Id:" + id));
        VoucherOrderRequest voucherOrderRequest = voucherOrderService.convertToVoucherOrderRequest(voucherOrderResponse);
        model.addAttribute("voucherOrderRequest", voucherOrderRequest);
        return "admin/voucher_order/edit_voucher";
    }

    @PostMapping("/{id}")
    public String updateVoucher(@PathVariable Long id, @ModelAttribute("voucherOrderRequest") VoucherOrderRequest voucherOrderRequest) {
        VoucherOrder voucherOrder = voucherOrderService.convertToVoucherOrder(voucherOrderRequest);
        voucherOrderService.saveVoucher(voucherOrder);
        return "redirect:/admin/dashboard/voucher_order/hien_thi";
    }

    @GetMapping("/delete/{id}")
    public String deleteVoucher(@PathVariable Long id) {
        voucherOrderService.deleteVoucher(id);
        return "redirect:/admin/dashboard/voucher_order/hien_thi";
    }

//    @GetMapping("/search")
//    public String searchVouchers(@RequestParam(defaultValue = "0") int page,
//                                 @RequestParam(defaultValue = "10") int size,
//                                 @RequestParam(defaultValue = "") String searchKeyword,
//                                 Model model) {
//        Page<VoucherOrderResponse> vouchersPage = voucherOrderService.searchVouchers(PageRequest.of(page, size), searchKeyword);
//        model.addAttribute("vouchersPage", vouchersPage);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("pageSize", size);
//        model.addAttribute("searchKeyword", searchKeyword);
//        return "admin/voucher_order/vouchers"; // Đây là tên template Thymeleaf để hiển thị danh sách vouchers
//    }
}
