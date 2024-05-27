package com.web.shop.qogita.role;

import com.web.shop.qogita.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROLES")
public class Role implements GrantedAuthority  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "roles_seq", allocationSize = 1)
    @Column(name = "ID", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "ROLE", nullable = false, length = 50)
    private String role;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.PERSIST)
    private List<Account> accountList;

    @Override
    public String getAuthority() {
        return role;
    }
}