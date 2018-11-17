package com.danieleautizi.service.personaldata.controller;

import com.danieleautizi.service.personaldata.manager.ArticleManager;
import com.danieleautizi.service.personaldata.model.presentation.Article;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleManager articleManager;

    @GetMapping(value = "/{articleId}")
    public Article getArticle(@PathVariable("articleId") final String articleId) {

        LOG.debug("Get article by id {} ", articleId);
        return articleManager.getArticleById(articleId);
    }

    @GetMapping("/")
    public List<Article> getArticles() {

        LOG.debug("Get all articles. ");
        return articleManager.getArticles();
    }

    @GetMapping("/active")
    public List<Article> getActiveArticles() {

        LOG.debug("Get all active articles. ");
        return articleManager.getActiveArticles();
    }

    @PostMapping(value = "/")
    public Article create(@RequestBody final Article article) {

        LOG.debug("Create Article {} ", article);
        return articleManager.create(article);
    }

    @PostMapping(value = "/bulk")
    public List<Article> create(@RequestBody final List<Article> articles) {

        LOG.debug("Create Articles: {} ", articles);
        return articleManager.create(articles);
    }

    @PutMapping(value = "/")
    public void update(@RequestBody final Article article) {

        LOG.debug("Update Article: {} ", article);
        articleManager.update(article);
    }

    @DeleteMapping(value = "/{articleId}")
    public void delete(@PathVariable("articleId") final String articleId) {

        LOG.debug("Delete Article by id {} ", articleId);
        articleManager.delete(articleId);
    }

}
