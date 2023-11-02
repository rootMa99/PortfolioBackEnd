package com.portfolio.appPortfolio.service;

import com.portfolio.appPortfolio.shared.ArticleDto;

import java.util.List;

public interface ArticleService {
    ArticleDto createArticle(ArticleDto articleDto);

    List<ArticleDto> getArticles(int page, int limit);

    ArticleDto getArticleById(String articleId);

    ArticleDto updateArticle(String articleId, ArticleDto articleDto);

    void deleteArticle(String articleId);

}
