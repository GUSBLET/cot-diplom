package com.web.shop.qogita.token;


import com.web.shop.qogita.account.Account;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TOKENS")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "uuid")
    private String id;

    @Column(name = "TOKEN", nullable = false, unique = true, length = 36)
    private String token;

    @CreationTimestamp
    @Column(name = "CREATION_TIME", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime creationTime;

    @Column(name = "LIFETIME")
    private LocalDateTime lifetime;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 50)
    private String operationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;
}
