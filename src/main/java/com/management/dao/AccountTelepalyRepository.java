package com.management.dao;

import com.management.domain.Account;
import com.management.domain.AccountTeleplay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface AccountTelepalyRepository extends JpaRepository<AccountTeleplay, Long> {
    @Query("from AccountTeleplay at where at.account=:account and at.name=:name")
    AccountTeleplay findAT(@Param("account") String account,@Param("name") String name);
}