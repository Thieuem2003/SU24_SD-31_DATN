package com.backend.repository;

import com.backend.entity.RoleAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleAccountRepository extends JpaRepository<RoleAccount, Long> {
    @Query(value = "select top 1 * from roleaccount where account_id = ?", nativeQuery = true)
    Optional<RoleAccount> findByAccount_Id(Long accountId);
}
