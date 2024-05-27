package com.web.shop.qogita.token;

import com.web.shop.qogita.account.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final TokenRepository tokenRepository;
    @Value("${host.link}")
    private String HOST_LINK;
    @Value("${application.link}")
    private String APPLICATION_LINK;

    public String generateHostUrl(Token token, String url) {
        return HOST_LINK + url + "?token=" + token.getToken();
    }

    public String generateApplicationUrl(Token token, String url) {
        return APPLICATION_LINK + url + "?token=" + token.getToken();
    }

    public Token generateAndSaveToken(Account account, ZonedDateTime lifeTime, TokenOperationType operationType) {
        return tokenRepository.save(Token.builder()
                .id(UUID.randomUUID().toString())
                .token(UUID.randomUUID().toString())
                .creationTime(ZonedDateTime.now().toLocalDateTime())
                .lifetime(lifeTime.toLocalDateTime())
                .account(account)
                .operationType(operationType.name())
                .build());
    }

    public boolean verifyToken(Token token) {
        if (token == null) {
            log.error("token confirmation", new IllegalArgumentException());
            return false;
        }
        if (token.getLifetime().isBefore(ZonedDateTime.now().toLocalDateTime())) {
            log.error("the life of the link is over", new IllegalArgumentException());
            return false;
        }
        tokenRepository.deleteById(token.getId());
        return true;
    }

    public Token findToken(String token) {
        return tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException(token + " not exist"));
    }


}
