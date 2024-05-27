package com.web.shop.qogita.account.dto;

import com.web.shop.qogita.account.Account;
import com.web.shop.qogita.technical.mapper.Mapper;

public class LoginDTO implements Mapper<LoginDTO, Account> {

    private String email;
    private String password;

    @Override
    public LoginDTO toDto(Account entity) {
        return null;
    }

    @Override
    public Account toEntity(LoginDTO dto) {
        return null;
    }
}
