package com.backend.repository;

import com.backend.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IEmailTemplateRepository extends JpaRepository<EmailTemplate,Long> {
    @Query("SELECT x " +
            "FROM EmailTemplate x " +
            "WHERE x.id IN (" +
            "    SELECT xn.id " +
            "    FROM EmailTemplate xn " +
            "    WHERE xn.mailType = :mailType" +
            ")")
    Optional<EmailTemplate> findByMailType(@Param("mailType") Integer mailType);
}
