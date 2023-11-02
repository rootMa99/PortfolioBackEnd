package com.portfolio.appPortfolio.service.impl;

import com.portfolio.appPortfolio.entity.ArticleEntity;
import com.portfolio.appPortfolio.entity.FileEntity;
import com.portfolio.appPortfolio.entity.ProjectEntity;
import com.portfolio.appPortfolio.exception.FileStorageException;
import com.portfolio.appPortfolio.repositories.ArticleRepository;
import com.portfolio.appPortfolio.repositories.FilesRepository;
import com.portfolio.appPortfolio.repositories.ProjectRepository;
import com.portfolio.appPortfolio.service.FileService;
import com.portfolio.appPortfolio.shared.FileDto;
import com.portfolio.appPortfolio.shared.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    FilesRepository filesRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    Utils utils;

    @Override
    public FileEntity storeFile(MultipartFile file){
        //normalise fileName
        String fileName= StringUtils.cleanPath(file.getOriginalFilename());
        try{
            //check for invalide character
            if (fileName.contains("..")){
                throw new FileStorageException("File Name Contain A Invalid Path Sequence");
            }
            String fileId= utils.generateProjectId(22);
            String fileDownloadUri=
                    ServletUriComponentsBuilder.fromCurrentContextPath().path("/files").path("/downloadFile/").path(fileId).toUriString();

            FileEntity fileEntity= new FileEntity(fileId, fileName, file.getContentType(), file.getBytes() ,
                    fileDownloadUri);
            return filesRepository.save(fileEntity);
        }catch (
                IOException e
        ){
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!");
        }
    }
    @Override
    public FileEntity uploadFile(MultipartFile file){
        //normalise fileName
        String fileName= StringUtils.cleanPath(file.getOriginalFilename());
        try{
            //check for invalide character
            if (fileName.contains("..")){
                throw new FileStorageException("File Name Contain A Invalid Path Sequence");
            }
            String fileId=utils.generateProjectId(22);
            String fileDownloadUri=
                    ServletUriComponentsBuilder.fromCurrentContextPath().path("/files").path("/downloadFile/").path(fileId).toUriString();

            FileEntity fileEntity= new FileEntity( fileId, fileName, file.getContentType(), file.getBytes(),
                    fileDownloadUri);

            return fileEntity;
        }catch (
                IOException e
        ){
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!");
        }
    }


    @Override
    public FileEntity getFileByFileId(String fileId) throws FileNotFoundException {
        FileEntity fe=filesRepository.findByFileId(fileId);
        if (fe ==null){
            throw new FileNotFoundException("File not found with id " + fileId);
        }


        return fe;
    }

    @Override
    public void deleteFile(String fileId) {
        FileEntity file= filesRepository.findByFileId(fileId);
        if (file==null)throw new FileStorageException("File not Found ID is: "+fileId);
        filesRepository.delete(file);
    }

    @Override
    public List<FileDto> addFiletoProject(String projectId, MultipartFile[] files) {
        List<FileEntity> entities= Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        ProjectEntity project= projectRepository.findByProjectId(projectId);
        if (project==null)throw new FileStorageException("project not found id is "+projectId);
        List<FileDto> fileDtos=new ArrayList<>();
        for (FileEntity file:entities){
            FileDto fileDto=new FileDto();
            file.setProjectDetails(project);
            FileEntity result= filesRepository.save(file);
            BeanUtils.copyProperties(result, fileDto);
            fileDtos.add(fileDto);
        }
        return fileDtos;
    }

    @Override
    public List<FileDto> addFileToArticle(String articleId, MultipartFile[] files) {
        List<FileEntity> entities= Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
        ArticleEntity articleEntity= articleRepository.findByArticleId(articleId);
        if (articleEntity==null)throw new FileStorageException("article not found ID is: "+articleId);
        List<FileDto> fileDtos=new ArrayList<>();
        for (FileEntity file:entities){
            FileDto fileDto=new FileDto();
            file.setArticleDetails(articleEntity);
            FileEntity fileEntity=filesRepository.save(file);
            BeanUtils.copyProperties(fileEntity, fileDto);
            fileDtos.add(fileDto);
        }
        return fileDtos;
    }


}
