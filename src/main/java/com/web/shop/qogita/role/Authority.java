package com.web.shop.qogita.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Authority {
    ADMIN("Admin"),
//    MODERATOR("Moderator"),
    CUSTOMER("Customer");


    private final String displayName;
}
