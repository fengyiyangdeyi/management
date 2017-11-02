package com.management.dao;

import com.management.domain.Period;
import com.management.domain.PeriodInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface PeriodInfoRepository extends JpaRepository<PeriodInfo, Long> ,JpaSpecificationExecutor<PeriodInfo> {
    @Query("from PeriodInfo a where a.keyword=:keyword and a.ip=:ip order by a.addTime desc")
    List<PeriodInfo> findByKeywordAndIp(@Param("keyword") String keyword, @Param("ip") String ip);

    @Query("select count(1) from PeriodInfo a where a.keyword=:keyword")
    Long countByKeyword(@Param("keyword") String keyword);

    @Query(value = "SELECT count(DISTINCT(p.ip)) from period_info p where p.keyword=:keyword",nativeQuery = true)
    long countGroupId(@Param("keyword") String keyword);

    @Query(value = "SELECT p.ip, p.keyword, count(1) FROM period_info p GROUP BY p.keyword, p.ip HAVING p.keyword=:keyword ORDER BY count(1) DESC limit :pageIndex,:pageSize",nativeQuery = true)
    List<Object[]> findGroupId(@Param("keyword") String keyword,@Param("pageIndex") Integer pageIndex,@Param("pageSize") Integer pageSize);
////    @Query("from Account a where a.endDate>now()")
//    @Query("from Account a where a.status=1")
//    List<Account> findA();
//
//    @Query("from Account a where vipAccount=:vipAccount")
//    Account getByAccount(@Param("vipAccount") String vipAccount);
}