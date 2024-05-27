package com.web.shop.qogita.account.dto;

import com.web.shop.qogita.account.Account;
//import com.web.shop.qogita.role.Authority;
import com.web.shop.qogita.role.Authority;
import com.web.shop.qogita.technical.mapper.Mapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationDTO implements Mapper<RegistrationDTO, Account> {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private Authority authority;

    @Override
    public RegistrationDTO toDto(Account entity) {
        return null;
    }

    @Override
    public Account toEntity(RegistrationDTO dto) {
        return Account.builder()
                .id(UUID.randomUUID())
                .email(dto.email)
                .password("")
                .isEnabled(true)
                .isNonLocked(true)
                .createdOn(LocalDateTime.now())
                .build();
    }
}
