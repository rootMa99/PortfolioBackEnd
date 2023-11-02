package com.portfolio.appPortfolio.service.impl;

import com.portfolio.appPortfolio.entity.ArticleEntity;
import com.portfolio.appPortfolio.exception.ServiceException;
import com.portfolio.appPortfolio.repositories.ArticleRepository;
import com.portfolio.appPortfolio.service.ArticleService;
import com.portfolio.appPortfolio.shared.ArticleDto;
import com.portfolio.appPortfolio.shared.FileDto;
import com.portfolio.appPortfolio.shared.Utils;
import com.portfolio.appPortfolio.ui.model.rest.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    Utils utils;
    @Autowired
    ArticleRepository articleRepository;
    @Override
    public ArticleDto createArticle(ArticleDto articleDto) {
        ModelMapper mp= new ModelMapper();
        mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        for (int i=0; i<articleDto.getFiles().size(); i++){
            FileDto fileDto= articleDto.getFiles().get(i);
            fileDto.setArticleDetails(articleDto);
            articleDto.getFiles().set(i, fileDto);
        }

        ArticleEntity article=mp.map(articleDto, ArticleEntity.class);
        article.setArticleId(utils.generateProjectId(23));
        ArticleEntity result = articleRepository.save(article);
        ArticleDto returnedResult= mp.map(result, ArticleDto.class);


        return returnedResult;
    }

    @Override
    public List<ArticleDto> getArticles(int page, int limit) {
        ModelMapper mp= new ModelMapper();
        mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<ArticleDto> returnedResult= new ArrayList<>();
        Pageable pageable= PageRequest.of(page, limit);
        Page<ArticleEntity> articlePages=articleRepository.findAll(pageable);
        List<ArticleEntity> articles= articlePages.getContent();
        if (articles.size()==0)throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()+" articles list is Empty" );
        for (ArticleEntity article: articles){
            ArticleDto articleDto = mp.map(article, ArticleDto.class);
            returnedResult.add(articleDto);
        }


        return returnedResult;
    }

    @Override
    public ArticleDto getArticleById(String articleId) {
        ModelMapper mp= new ModelMapper();
        mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ArticleEntity articleEntity=articleRepository.findByArticleId(articleId);
        if (articleEntity==null) throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()+" ID is: "+articleId);
        ArticleDto articleDto= mp.map(articleEntity, ArticleDto.class);

        return articleDto;
    }

    @Override
    public ArticleDto updateArticle(String articleId, ArticleDto articleDto) {
        ModelMapper mp= new ModelMapper();
        mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ArticleEntity articleEntity= articleRepository.findByArticleId(articleId);
        if (articleEntity==null) throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()+" Id is: "+articleId);
        if (articleDto.getDescription()!=null){
            articleEntity.setDescription(articleDto.getDescription());
        }
        if(articleDto.getTitle()!=null){
            articleEntity.setTitle(articleDto.getTitle());
        }
        ArticleEntity returnedArticle= articleRepository.save(articleEntity);
        ArticleDto returnedDto = mp.map(returnedArticle,  ArticleDto.class);

        return returnedDto;
    }

    @Override
    public void deleteArticle(String articleId) {
        ArticleEntity articleEntity=articleRepository.findByArticleId(articleId);
        if (articleEntity==null)throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()+" ID is: "+articleId);
        try {
            articleRepository.delete(articleEntity);
        }catch (Exception e){
            throw new ServiceException(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessage());
        }
    }
}
