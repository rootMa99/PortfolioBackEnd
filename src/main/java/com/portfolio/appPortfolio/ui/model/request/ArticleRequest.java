package com.portfolio.appPortfolio.ui.model.request;

import com.portfolio.appPortfolio.ui.model.rest.FileProjectRest;

import java.util.List;

public class ArticleRequest {

    private String articleId;
    private String title;
    private String description;
    private List<FileProjectRest> files;

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

    public List<FileProjectRest> getFiles() {
        return files;
    }

    public void setFiles(List<FileProjectRest> files) {
        this.files = files;
    }
}
