package com.web.shop.qogita.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Set<Role> chooseRole(Authority role) {
        switch (role) {
            case ADMIN -> {
                return getAdminRights();
            }
            case CUSTOMER -> {
                return getCustomerRights();
            }
            default -> {
                return Set.of(getRole(Authority.CUSTOMER.name()));
            }
        }
    }

    private Set<Role> getAdminRights() {
        return Set.of(getRole(Authority.ADMIN.name()));
    }

    private Set<Role> getCustomerRights() {
        return Set.of(getRole(Authority.CUSTOMER.name()));
    }

    private Role getRole(String role) {
        return roleRepository.findByRole(role).orElse(null);
    }
}
