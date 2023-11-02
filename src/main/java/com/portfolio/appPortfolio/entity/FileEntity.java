package com.portfolio.appPortfolio.entity;


import com.portfolio.appPortfolio.shared.ProjectDto;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity(name = "files")
public class FileEntity {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String fileId;
    private String fileName;
    private String fileType;
    @Lob
    private byte[] data;

    private String fileDownloadUri;
    @ManyToOne
    @JoinColumn(name = "projectId")
    private ProjectEntity projectDetails;
    @ManyToOne
    @JoinColumn(name = "articleId")
    private ArticleEntity articleDetails;

    public FileEntity() {
    }

    public FileEntity(String fileId, String fileName, String fileType, byte[] data, String fileDownloadUri) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.fileDownloadUri= fileDownloadUri;
    }

    public ArticleEntity getArticleDetails() {
        return articleDetails;
    }

    public void setArticleDetails(ArticleEntity articleDetails) {
        this.articleDetails = articleDetails;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public ProjectEntity getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(ProjectEntity projectDetails) {
        this.projectDetails = projectDetails;
    }

    public long getId() {
        return id;
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

    public void setId(long id) {
        this.id = id;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }
}
