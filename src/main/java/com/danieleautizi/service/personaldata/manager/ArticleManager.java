package com.danieleautizi.service.personaldata.manager;

import com.danieleautizi.service.personaldata.model.presentation.Article;

import java.util.List;

/**
 * Manager for {@link Article} operations.
 */
public interface ArticleManager {

    /**
     * Find an Article by its id
     * @param articleId
     * @return Article
     */
    Article getArticleById(final String articleId);

    /**
     * Find Articles by a bunch of ids
     * @param articleIds
     * @return List<Article>
     */
    List<Article> getArticleByIds(final List<String> articleIds);

    /**
     * Find all Articles
     * @return List<Article>
     */
    List<Article> getArticles();

    /**
     * Find all the active Articles
     * @return List<Article>
     */
    List<Article> getActiveArticles();

    /**
     * Create a new Article
     * @param article
     * @return Article created
     */
    Article create(final Article article);

    /**
     * Create new Articles
     * @param articles
     * @return List<Article> created
     */
    List<Article> create(final List<Article> articles);

    /**
     * Update an Article
     * @param article
     * @return Article updated
     */
    Article update(final Article article);

    /**
     * Delete an existing Article by its id
     * @param articleId
     */
    void delete(final String articleId);

}
