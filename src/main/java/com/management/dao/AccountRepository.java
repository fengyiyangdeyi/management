package com.management.dao;

import com.management.domain.Account;
import com.management.domain.AccountTeleplay;
import com.management.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
//    @Query("from Account a where a.endDate>now()")
    @Query("from Account a where a.status=1")
    List<Account> findA();

    @Query("from Account a where vipAccount=:vipAccount")
    Account getByAccount(@Param("vipAccount")String vipAccount);
}