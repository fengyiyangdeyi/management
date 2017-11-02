package com.management.dao;

import com.management.domain.AccountTeleplay;
import com.management.domain.Teleplay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TelepalyRepository extends JpaRepository<Teleplay, Long> {

    @Query("from Teleplay t")
    List<Teleplay> findT();

    @Query("from Teleplay t where t.name=:name")
    Teleplay findTByName(@Param("name") String name);
}