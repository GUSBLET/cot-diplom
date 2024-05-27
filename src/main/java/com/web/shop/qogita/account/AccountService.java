package com.web.shop.qogita.account;

import com.web.shop.qogita.account.dto.RegistrationDTO;
import com.web.shop.qogita.role.RoleService;
import com.web.shop.qogita.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No such login exists"));
    }

    public String getUserEmailFromSession() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Account findAccountByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow();
    }

    public boolean registerNewAccount(RegistrationDTO dto) {
        if (!emailIsValid(dto.getEmail()) || accountRepository.findByEmail(dto.getEmail()).isPresent())
            return false;

        Account account = dto.toEntity(dto);
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        account.setRoles(roleService.chooseRole(dto.getAuthority()));
        accountRepository.save(account);
        return true;
    }


    private boolean emailIsValid(String email) {
        String[] parts = email.split("@");
        return parts.length == 2;
    }
}
