package com.portfolio.appPortfolio.repositories;

import com.portfolio.appPortfolio.entity.ArticleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<ArticleEntity, Long> {

    ArticleEntity findByArticleId(String articleId);
}
