package com.danieleautizi.manager;

import com.danieleautizi.model.presentation.Article;

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
    public Article getArticleById(final String articleId);

    /**
     * Find Articles by a bunch of ids
     * @param articleIds
     * @return List<Article>
     */
    public List<Article> getArticleByIds(final List<String> articleIds);

    /**
     * Find all Articles
     * @return List<Article>
     */
    public List<Article> getArticles();

    /**
     * Find all the active Articles
     * @return List<Article>
     */
    public List<Article> getActiveArticles();

    /**
     * Create a new Article
     * @param article
     * @return Article created
     */
    public Article create(final Article article);

    /**
     * Create new Articles
     * @param articles
     * @return List<Article> created
     */
    public List<Article> create(final List<Article> articles);

    /**
     * Update an Article
     * @param article
     * @return Article updated
     */
    public Article update(final Article article);

    /**
     * Delete an existing Article by its id
     * @param articleId
     */
    public void delete(final String articleId);

}
