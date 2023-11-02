package com.portfolio.appPortfolio.ui;

import com.portfolio.appPortfolio.entity.FileEntity;
import com.portfolio.appPortfolio.service.FileService;
import com.portfolio.appPortfolio.shared.FileDto;
import com.portfolio.appPortfolio.ui.model.rest.FileRest;
import com.portfolio.appPortfolio.ui.model.rest.OperationStatus;
import com.portfolio.appPortfolio.ui.model.rest.OperationStatusResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("files")
public class FilesController {

    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public FileRest uploadFile(@RequestParam("file") MultipartFile file){

        FileEntity fileName= fileService.storeFile(file);
        //String fileDownloadUri=ServletUriComponentsBuilder.fromCurrentContextPath().path("/files").path("/downloadFile/").path(fileName.getFileName()).toUriString();
        String fileDownloadUri=
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/files").path("/downloadFile/").path(fileName.getFileId()).toUriString();
        return new FileRest(fileName.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultiFile")
    public List<FileRest> uploadFiles(@RequestParam("files") MultipartFile[] files){



        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }


    @GetMapping("/downloadFile/{fileId:.+}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileId, HttpServletRequest request) throws FileNotFoundException {
        //load file as Ressource
        FileEntity fileEntity = fileService.getFileByFileId(fileId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileEntity.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +fileEntity.getFileName()+"\"").body(new ByteArrayResource(fileEntity.getData()));
    }

    @DeleteMapping("/{fileId}")
    public OperationStatusResult deleteFile(@PathVariable String fileId){
        OperationStatusResult operation= new OperationStatusResult();
        operation.setOperationName("DELETE");
        fileService.deleteFile(fileId);
        operation.setOperationResult(OperationStatus.SUCCESS.name());
        return operation;
    }
    @PostMapping("/project/{projectId}")
    public List<FileRest> addFileToProject(@PathVariable String projectId ,
                                     @RequestParam(value="files") MultipartFile[] files){
       List<FileDto> filesResult = fileService.addFiletoProject(projectId, files);
       List<FileRest> fileRestList=new ArrayList<>();
       for (FileDto file:filesResult){
           FileRest fileRest= new FileRest();
           BeanUtils.copyProperties(file,fileRest);
           fileRestList.add(fileRest);
       }
       return fileRestList;
    }

    @PostMapping("/article/{articleId}")
    public List<FileRest> addFileToArticle(@PathVariable String articleId,
                                           @RequestParam(value = "files") MultipartFile[] files){
        List<FileDto> fileResult= fileService.addFileToArticle(articleId,files);
        List<FileRest> fileRestList=new ArrayList<>();
        for (FileDto file:fileResult){
            FileRest fileRest=new FileRest();
            BeanUtils.copyProperties(file, fileRest);
            fileRestList.add(fileRest);
        }
        return fileRestList;
    }
}
