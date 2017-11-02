package com.management.dao;

import com.management.domain.Account;
import com.management.domain.ImportAccountLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountImportLogRepository extends JpaRepository<ImportAccountLog, Long> {
}