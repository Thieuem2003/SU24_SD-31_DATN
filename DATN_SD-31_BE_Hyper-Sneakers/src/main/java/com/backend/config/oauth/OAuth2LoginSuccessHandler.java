package com.backend.config.oauth;

import com.backend.entity.Account;
import com.backend.entity.Role;
import com.backend.entity.RoleAccount;
import com.backend.repository.AccountRepository;
import com.backend.repository.RoleAccountRepository;
import com.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    //CustomerRepository customerRepository;
    @Autowired
    AccountRepository accountRepository;

    //	AppRoleRepository appRoleRepository;
    @Autowired
    RoleRepository roleRepository;

    //UserRoleRepository userRoleRepository;
    @Autowired
    RoleAccountRepository roleAccountRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oauth2User.getName();
        System.out.println(email);
        Optional<Account> cus = accountRepository.FindByEmail(email);
        if (cus.isEmpty()) {
            Account c = new Account();
            c.setName(oauth2User.getNameReal());
            c.setCode("");
            c.setEmail(email);
            c.setPassword(bCryptPasswordEncoder.encode("123@ABCxyzalualua"));
            c.setAvatar("");
            c.setCreatedAt(LocalDate.now());
            c.setUpdatedAt(null);
            c.setStatus(1);
            accountRepository.save(c);
            Optional<Role> a = roleRepository.findById(2L);
            RoleAccount urole = new RoleAccount(0L, c, a.get());
            roleAccountRepository.save(urole);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
