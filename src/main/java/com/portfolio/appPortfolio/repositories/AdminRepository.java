package com.portfolio.appPortfolio.repositories;

import com.portfolio.appPortfolio.entity.AdminEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource(exported = false)
public interface AdminRepository extends CrudRepository<AdminEntity, Long> {
    AdminEntity save(AdminEntity adminEntity);

    Optional<AdminEntity> findByAdminName(String adminName);
}
