package com.portfolio.appPortfolio.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "article")
public class ArticleEntity {

    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String articleId;
    private String title;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "articleDetails")
    private List<FileEntity> files;

    public ArticleEntity() {
    }

    public ArticleEntity(String articleId, String title, String description, List<FileEntity> files) {
        this.articleId = articleId;
        this.title = title;
        this.description = description;
        this.files = files;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntity> files) {
        this.files = files;
    }
}
