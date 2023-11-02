package com.portfolio.appPortfolio.service;

import com.portfolio.appPortfolio.entity.FileEntity;
import com.portfolio.appPortfolio.shared.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileService {
    FileEntity storeFile(MultipartFile file);

    FileEntity uploadFile(MultipartFile file);



    FileEntity getFileByFileId(String fileId) throws FileNotFoundException;

    void deleteFile(String fileId);

    List<FileDto> addFiletoProject(String projectId, MultipartFile[] files);

    List<FileDto> addFileToArticle(String articleId, MultipartFile[] files);

}
