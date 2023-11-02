package com.portfolio.appPortfolio.repositories;

import com.portfolio.appPortfolio.entity.ProjectEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<ProjectEntity, Long> {
    ProjectEntity save(ProjectEntity project);

    ProjectEntity findByProjectId(String id);
}
