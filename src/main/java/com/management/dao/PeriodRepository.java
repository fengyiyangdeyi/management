package com.management.dao;

import com.management.domain.Account;
import com.management.domain.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeriodRepository extends JpaRepository<Period, Long> {
    @Query("from Period a where keyword=:keyword")
    Period findByKeyword(@Param("keyword")String keyword);
////    @Query("from Account a where a.endDate>now()")
//    @Query("from Account a where a.status=1")
//    List<Account> findA();
//
//    @Query("from Account a where vipAccount=:vipAccount")
//    Account getByAccount(@Param("vipAccount") String vipAccount);
}