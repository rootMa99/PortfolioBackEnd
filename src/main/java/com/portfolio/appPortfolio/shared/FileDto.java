package com.portfolio.appPortfolio.shared;

import javax.persistence.Lob;

public class FileDto {

    private long id;
    private String fileId;

    private String fileName;
    private String fileDownloadUri;

    private String fileType;
    private long size;
    @Lob
    private byte[] data;
    private ProjectDto projectDetails;

    private ArticleDto articleDetails;

    public FileDto(String fileId, String fileName, String fileDownloadUri, String fileType, long size,
                   byte[] data,
                   ProjectDto projectDetails, ArticleDto articleDetails) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
        this.data = data;
        this.projectDetails = projectDetails;
        this.articleDetails = articleDetails;
    }

    public FileDto() {
    }


    public ArticleDto getArticleDetails() {
        return articleDetails;
    }

    public void setArticleDetails(ArticleDto articleDetails) {
        this.articleDetails = articleDetails;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public ProjectDto getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(ProjectDto projectDetails) {
        this.projectDetails = projectDetails;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
