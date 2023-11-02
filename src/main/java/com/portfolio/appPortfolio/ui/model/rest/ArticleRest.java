package com.portfolio.appPortfolio.ui.model.rest;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class ArticleRest extends RepresentationModel<ArticleRest> {

    private String articleId;
    private String title;
    private String description;
    private List<FileRest> files;

    public ArticleRest(String articleId, String title, String description, List<FileRest> files) {
        this.articleId = articleId;
        this.title = title;
        this.description = description;
        this.files = files;
    }

    public ArticleRest() {
    }

    public List<FileRest> getFiles() {
        return files;
    }

    public void setFiles(List<FileRest> files) {
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
}
