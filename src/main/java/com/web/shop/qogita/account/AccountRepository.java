package com.web.shop.qogita.account;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE GTS_SHOP.SHOP.ACCOUNTS SET password = :newPassword, updated_on = :updatedTime WHERE email = :email", nativeQuery = true)
    void updatePasswordByEmail(@Param("email") String email, @Param("newPassword") String newPassword, @Param("updatedTime") LocalDateTime updatedTime);

}
