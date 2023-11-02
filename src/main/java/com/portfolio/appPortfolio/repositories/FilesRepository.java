package com.portfolio.appPortfolio.repositories;

import com.portfolio.appPortfolio.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepository extends JpaRepository<FileEntity, Long> {

    FileEntity findByFileName(String fileId);

    FileEntity findByFileId(String fileId);
}
