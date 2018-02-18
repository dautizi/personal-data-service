package com.danieleautizi.manager;

import static com.danieleautizi.utility.ArticleConverter.entitiesToPresentation;
import static com.danieleautizi.utility.ArticleConverter.entityToPresentation;
import static com.danieleautizi.utility.ArticleConverter.presentationToEntity;
import static com.danieleautizi.utility.DateTimeUtil.utcLocalDateTimeNow;
import static com.danieleautizi.utility.MongoUtils.stringToObject;

import com.danieleautizi.exception.BadRequestException;
import com.danieleautizi.exception.NotFoundException;
import com.danieleautizi.model.presentation.Article;
import com.danieleautizi.repository.ArticleRepository;
import com.danieleautizi.utility.PersonalDataUtil;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleManagerImpl implements ArticleManager {

    @NonNull
    private ArticleRepository articleRepository;

    @NonNull
    private final PersonalDataUtil personalDataUtil;

    public static final String ARTICLE_NOT_FOUND_MESSAGE = "article.error.notfound";
    public static final String ARTICLE_BAD_REQUEST_MESSAGE = "article.error.badrequest";

    /**
     * Find an Article by its id
     * @param articleId
     * @return Article
     */
    @Override
    public Article getArticleById(final String articleId) {

        return entityToPresentation(articleRepository.findOne(stringToObject(articleId)));
    }

    /**
     * Find Articles by a bunch of ids
     * @param articleIds
     * @return List<Article>
     */
    @Override
    public List<Article> getArticleByIds(final List<String> articleIds) {

        return entitiesToPresentation(articleRepository.findArticlesByIdIn(stringToObject(articleIds)));
    }

    /**
     * Find all Articles
     * @return List<Article>
     */
    @Override
    public List<Article> getArticles() {

        return entitiesToPresentation(articleRepository.findAll());
    }

    /**
     * Find all active Articles
     * @return List<Article>
     */
    @Override
    public List<Article> getActiveArticles() {

        return entitiesToPresentation(articleRepository.findArticlesByActive(true));
    }

    /**
     * Create a new Article
     * @param article
     * @return Article created
     */
    @Override
    public Article create(final Article article) {

        val now = utcLocalDateTimeNow();
        val articleEntity = presentationToEntity(article);
        articleEntity.setDatetime(now);
        articleEntity.setLastUpdate(now);

        return entityToPresentation(articleRepository.save(articleEntity));
    }

    /**
     * Create new Articles
     * @param articles
     * @return List<Article> created
     */
    public List<Article> create(final List<Article> articles) {

        return articles.stream()
                         .map(article -> create(article))
                         .collect(Collectors.toList());
    }

    /**
     * Update an Article
     * @param article
     * @return Article updated
     */
    public Article update(final Article article) {

        if (article == null) {

            throw new BadRequestException(personalDataUtil.getMessage(ARTICLE_BAD_REQUEST_MESSAGE, article));
        }

        if (getArticleById(article.getId()) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(ARTICLE_NOT_FOUND_MESSAGE, article.getId()));
        }

        val articleEntity = presentationToEntity(article);
        articleEntity.setLastUpdate(utcLocalDateTimeNow());
        return entityToPresentation(articleRepository.save(articleEntity));
    }

    /**
     * Delete an existing Article by its id
     * @param articleId
     */
    public void delete(final String articleId) {

        if (getArticleById(articleId) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(ARTICLE_NOT_FOUND_MESSAGE, articleId));
        }

        articleRepository.delete(stringToObject(articleId));
    }

}