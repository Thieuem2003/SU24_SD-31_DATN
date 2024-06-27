package com.backend.controller;

import com.backend.entity.Account;
import com.backend.entity.RoleAccount;
import com.backend.repository.AccountRepository;
import com.backend.repository.IEmailTemplateRepository;
import com.backend.repository.RoleAccountRepository;
import com.backend.repository.RoleRepository;
import com.backend.service.IEmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AccountRepository accountRepository;

//    @Autowired
//    ShoppingCartService shoppingCartService;
//
//    @Autowired
//    ProductRepository productRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleAccountRepository roleAccountRepository;

//    @Autowired
//    OrderRepository orderRepository;
//
//    @Autowired
//    OrderDetailRepository orderDetailRepository;

    @Autowired
    IEmailTemplateService iEmailTemplateService;
    @Autowired
    IEmailTemplateRepository iEmailTemplateRepository;

    @Autowired
    HttpSession session;

    @RequestMapping(value = {"/home", "/"})
    public ModelAndView home(ModelMap model, Principal principal) {
        // phần home của admin
        boolean isLogin = false;
        if (principal != null) {
            isLogin = true;
        }
        model.addAttribute("isLogin", isLogin);

        if (principal != null) {
            Optional<Account> c = accountRepository.FindByEmail(principal.getName());
            Optional<RoleAccount> uRole = roleAccountRepository.findByAccount_Id(Long.valueOf(c.get().getId()));
            if (uRole.get().getRole().getName().equals("ROLE_ADMIN")) {
                return new ModelAndView("forward:/admin/dashboard/san-pham/hien-thi", model);
            }
        }

       // phần home của khách hàng

//        Page<Product> listP = productRepository.findAll(PageRequest.of(0, 6));
//
//        int totalPage = listP.getTotalPages();
//        if (totalPage > 0) {
//            int start = 1;
//            int end = Math.min(2, totalPage);
//            List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
//        model.addAttribute("totalCartItems", shoppingCartService.getCount());
//        List<Category> listC = categoryRepository.findAll();
//        model.addAttribute("categories", listC);
//        model.addAttribute("products", listP);
//        model.addAttribute("slide", true);
//        return new ModelAndView("/site/index", model);
//    }
        return new ModelAndView("/user/trang_chu", model);

    }
}
