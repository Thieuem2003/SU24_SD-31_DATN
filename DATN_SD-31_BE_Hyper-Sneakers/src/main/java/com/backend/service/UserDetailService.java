package com.backend.service;

import com.backend.entity.Account;
import com.backend.entity.Role;
import com.backend.entity.RoleAccount;
import com.backend.repository.AccountRepository;
import com.backend.repository.RoleAccountRepository;
import com.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    //	userRoleRepository
    @Autowired
    private RoleAccountRepository roleAccountRepository;

    @Autowired
    private AccountRepository accountRepository;

    //appRoleRepository
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> userOpt = accountRepository.FindByEmail(email);

        if (userOpt.isEmpty()) {
            System.out.println("Email not found! " + email);
            throw new UsernameNotFoundException("Email: " + email + " was not found in the database");
        }

        System.out.println("Found User: " + userOpt.get());
        Optional<RoleAccount> urole = roleAccountRepository.findByAccount_Id(Long.valueOf(userOpt.get().getId()));
        Optional<Role> arole = roleRepository.findById(urole.get().getRole().getId());

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = new SimpleGrantedAuthority(arole.get().getName());
        grantList.add(authority);

        System.out.println(arole.get().getName());

        UserDetails userDetails = (UserDetails) new User(userOpt.get().getEmail(),
                userOpt.get().getPassword().trim(), grantList);

        return userDetails;
    }
}
