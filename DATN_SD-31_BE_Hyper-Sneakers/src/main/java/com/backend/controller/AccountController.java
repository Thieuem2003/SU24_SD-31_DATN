package com.backend.controller;

import com.backend.dto.request.SignupRequest;
import com.backend.dto.request.account.ChangePassword;
import com.backend.entity.Account;
import com.backend.entity.EmailTemplate;
import com.backend.entity.Role;
import com.backend.entity.login.ERole;
import com.backend.repository.AccountRepository;
import com.backend.repository.IEmailTemplateRepository;
import com.backend.repository.QuyenHanRepository;
import com.backend.repository.RoleRepository;
import com.backend.service.IEmailTemplateService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Controller
public class AccountController {

    @Autowired
    private IEmailTemplateRepository xacNhanEmailRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;

//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AccountRepository accountRepository;


//    @Autowired
//    ShoppingCartService shoppingCartService;
//
//    @Autowired
//    ProductRepository productRepository;

    //    @Autowired AppRoleRepository appRoleRepository;
    @Autowired
    QuyenHanRepository quyenHanRepository;

//    @Autowired
//    UserRoleRepository userRoleRepository;
//
//    @Autowired
//    OrderRepository orderRepository;
//
//    @Autowired
//    OrderDetailRepository orderDetailRepository;

    @Autowired
    IEmailTemplateService iEmailTemplateService;
    //    SendMailService
    @Autowired
    HttpSession session;

    @GetMapping("/login")
    public ModelAndView loginForm(ModelMap model, @RequestParam("error") Optional<String> error) {
        String errorString = error.orElse("false");
        if (errorString.equals("true")) {
            model.addAttribute("error", "Tài khoản hoặc mật khẩu không đúng!");
        }
        return new ModelAndView("/user/login", model);
    }

    @RequestMapping("/logout")
    public String login() {

        return "redirect:/home";
    }

    // đăng kí tài khoản
    @GetMapping("/register")
    public ModelAndView registerForm(ModelMap model) {
        model.addAttribute("customer", new SignupRequest());
        return new ModelAndView("/user/register", model);
    }

    @PostMapping("/register")
    public String register(ModelMap model, @Valid @ModelAttribute("customer") SignupRequest dto, BindingResult result,
                           @RequestParam("password") String password) {
        if (result.hasErrors()) {
            return "/user/register";
        }
        if (!checkEmail(dto.getEmail())) {
            model.addAttribute("error", "Email này đã được sử dụng!");
            return "/user/register";
        }
        session.removeAttribute("otp");
        // Gửi mã xác nhận đến email của tài khoản vừa đăng ký

        Integer mailType = createVerificationCode();
        iEmailTemplateService.sendMaXacNhanToEmail(mailType);

        EmailTemplate xacNhanEmail = new EmailTemplate();
        xacNhanEmail.setMailType(mailType);
        xacNhanEmail.setMailContent("ok");
        xacNhanEmail.setSubject("Subject");
        xacNhanEmailRepository.save(xacNhanEmail);

        model.addAttribute("customer", dto);
        model.addAttribute("message", "Mã OTP đã được gửi tới Email, hãy kiểm tra Email của bạn!");

        return "/user/confirmOtpRegister";
    }

    public static int createVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(1000000); // Tạo số ngẫu nhiên từ 0 đến 999999
        String codeStr = String.format("%06d", code); // Đảm bảo có 6 chữ số, thêm số 0 ở đầu nếu cần
        return Integer.parseInt(codeStr); // Chuyển đổi chuỗi thành số nguyên
    }

    // xác nhận email đk
    @PostMapping("/confirmOtpRegister")
    public ModelAndView confirmRegister(ModelMap model, @ModelAttribute("customer") SignupRequest dto, @RequestParam("password") String password,
                                        @RequestParam("otp") Integer otp) {
        EmailTemplate emailTemplate = new EmailTemplate();
        // Kiểm tra thông tin xác nhận email trong cơ sở dữ liệu
        Optional<EmailTemplate> optionalXacNhanEmail = xacNhanEmailRepository.findByMailType(otp);
        if (otp.equals(optionalXacNhanEmail.get().getMailType())) {
            dto.setPassword(encoder.encode(password));

            Account c = new Account();
            BeanUtils.copyProperties(dto, c);
            c.setCreatedAt(LocalDate.now());
            c.setStatus(1);
            c.setAvatar("user.png");
            accountRepository.save(c);

//
            Set<String> strRoles = dto.getRole();
            List<Role> roles = new ArrayList<>();
            // Kiểm tra và thiết lập giá trị mặc định cho strRoles nếu nó là null hoặc rỗng
            if (strRoles == null || strRoles.isEmpty()) {
                strRoles = new HashSet<>();
                strRoles.add("user");  // Thiết lập mặc định là ROLE_USER

            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Vai trò không được tìm thấy."));
                            roles.add(adminRole);

                            break;
                        case "mod":
                            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                    .orElseThrow(() -> new RuntimeException("Error: Vai trò không được tìm thấy."));
                            roles.add(modRole);

                            break;
                        default:
                            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Vai trò không được tìm thấy."));
                            roles.add(userRole);
                    }
                });
//            Optional<AppRole> a = appRoleRepository.findById(2L);
//            UserRole urole = new UserRole(0L, c, a.get());
//            userRoleRepository.save(urole);


            }
            session.removeAttribute("otp");
            model.addAttribute("message", "Đăng kí thành công");
            return new ModelAndView("/user/login");
        }
            model.addAttribute("customer", dto);
            model.addAttribute("error", "Mã OTP không đúng, hãy thử lại!");
            return new ModelAndView("/user/confirmOtpRegister", model);
        }

    // check email
    public boolean checkEmail(String email) {
        List<Account> list = accountRepository.findAll();
        for (Account c : list) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }

//    quên mk
@GetMapping("/forgotPassword")
public ModelAndView forgotFrom() {

        return new ModelAndView("/user/forgotPassword");
}

    @PostMapping("/forgotPassword")
    public ModelAndView forgotPassowrd(ModelMap model, @RequestParam("email") String email) {
        List<Account> listC = accountRepository.findAll();
        for (Account c : listC) {
            if (email.trim().equals(c.getEmail())) {
                session.removeAttribute("otp");
                // Gửi mã xác nhận đến email của tài khoản vừa đăng ký
                Integer mailType = createVerificationCode();
                iEmailTemplateService.sendMaXacNhanToEmail(mailType);

                EmailTemplate xacNhanEmail = new EmailTemplate();
                xacNhanEmail.setMailType(mailType);
                xacNhanEmail.setMailContent("ok");
                xacNhanEmail.setSubject("Subject");
                xacNhanEmailRepository.save(xacNhanEmail);

                model.addAttribute("email", email);
                model.addAttribute("message", "Mã OTP đã được gửi tới Email, hãy kiểm tra Email của bạn!");
                return new ModelAndView("/user/confirmOtp", model);
            }
        }
        model.addAttribute("error", "Email này không tồn tại trong hệ thống!");
        return new ModelAndView("/user/forgotPassword", model);
    }

    @PostMapping("/confirmOtp")
    public ModelAndView confirm(ModelMap model, @RequestParam("otp") Integer otp, @RequestParam("email") String email) {
        Optional<EmailTemplate> optionalXacNhanEmail = xacNhanEmailRepository.findByMailType(otp);
        if (otp.equals(optionalXacNhanEmail.get().getMailType())) {
            model.addAttribute("email", email);
            model.addAttribute("newPassword", "");
            model.addAttribute("confirmPassword", "");
            model.addAttribute("changePassword", new ChangePassword());
            return new ModelAndView("/user/changePassword", model);
        }
        model.addAttribute("error", "Mã OTP không trùng, vui lòng kiểm tra lại!");
        return new ModelAndView("/user/confirmOtp", model);
    }

    @PostMapping("/changePassword")
    public ModelAndView changeForm(ModelMap model,
                                   @Valid @ModelAttribute("changePassword") ChangePassword changePassword, BindingResult result,
                                   @RequestParam("email") String email, @RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword) {
        if (result.hasErrors()) {

            model.addAttribute("newPassword", newPassword);
            model.addAttribute("newPassword", confirmPassword);
//			model.addAttribute("changePassword", changePassword);
            model.addAttribute("email", email);
            return new ModelAndView("/site/changePassword", model);
        }

        if (!changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {

            model.addAttribute("newPassword", newPassword);
            model.addAttribute("newPassword", confirmPassword);
//			model.addAttribute("changePassword", changePassword);
            model.addAttribute("error", "error");
            model.addAttribute("email", email);
            return new ModelAndView("/site/changePassword", model);
        }
        // Handle potential absence of account using Optional
        Optional<Account> optionalAccount = accountRepository.FindByEmail(email);
        if (!optionalAccount.isPresent()) {
            // Handle case where account with email is not found
            model.addAttribute("error", "Không tìm thấy tài khoản với email đã nhập (Account with entered email not found)");
            model.addAttribute("email", email);
            return new ModelAndView("/user/changePassword", model);
        }

        Account account = optionalAccount.get(); // Safe access after checking isPresent()

        // Update account details
        account.setStatus(1);
        account.setPassword(encoder.encode(newPassword));
        accountRepository.save(account);
        //
        model.addAttribute("message", "Đổi mật khẩu thành công!");
        model.addAttribute("email", "");
        session.removeAttribute("otp");
        return new ModelAndView("/user/changePassword", model);
    }
    @RequestMapping("/403")
    public String error() {

        return "/user/error";
    }

}
