package com.danieleautizi.service.personaldata.controller;

import com.danieleautizi.service.personaldata.manager.BlogManager;
import com.danieleautizi.service.personaldata.model.presentation.Blog;

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
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogManager blogManager;

    @GetMapping(value = "/{blogId}")
    public Blog getBlog(@PathVariable("blogId") final String blogId) {

        LOG.debug("Get blog by id {} ", blogId);
        return blogManager.getBlogById(blogId);
    }

    @GetMapping("/")
    public List<Blog> getBlogs() {

        LOG.debug("Get all blogs. ");
        return blogManager.getBlogs();
    }

    @GetMapping("/active")
    public List<Blog> getActiveBlogs() {

        LOG.debug("Get all active blogs. ");
        return blogManager.getActiveBlogs();
    }

    @PostMapping(value = "/")
    public Blog create(@RequestBody final Blog blog) {

        LOG.debug("Create Blog {} ", blog);
        return blogManager.create(blog);
    }

    @PostMapping(value = "/bulk")
    public List<Blog> create(@RequestBody final List<Blog> blogs) {

        LOG.debug("Create Blogs: {} ", blogs);
        return blogManager.create(blogs);
    }

    @PutMapping(value = "/")
    public void update(@RequestBody final Blog blog) {

        LOG.debug("Update Blog: {} ", blog);
        blogManager.update(blog);
    }

    @DeleteMapping(value = "/{blogId}")
    public void delete(@PathVariable("blogId") final String blogId) {

        LOG.debug("Delete Blog by id {} ", blogId);
        blogManager.delete(blogId);
    }

}
