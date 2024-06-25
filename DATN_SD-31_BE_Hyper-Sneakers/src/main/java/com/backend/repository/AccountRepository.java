package com.backend.repository;

import com.backend.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByName(String tenTaiKhoan);

    Account findByEmail(String email);

    Boolean existsByName(String tenTaiKhoan);

    Boolean existsByEmail(String email);

    //
    Page<Account> findByNameContaining(String name, Pageable pageable);


    @Query(value = "select top 1 * from account where email = ? and status = 1", nativeQuery = true)
    Optional<Account> FindByEmail(String email);

//    @Modifying
//    @Query(value = "update Account u set u.status = false where u.id = ?1")
//    void SetStatus(int id);
}

