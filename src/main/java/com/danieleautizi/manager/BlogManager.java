package com.danieleautizi.manager;

import com.danieleautizi.model.presentation.Blog;

import java.util.List;

/**
 * Manager for {@link Blog} operations.
 */
public interface BlogManager {

    /**
     * Find an Blog by its id
     * @param blogId
     * @return Blog
     */
    public Blog getBlogById(final String blogId);

    /**
     * Find Blogs by a bunch of ids
     * @param blogIds
     * @return List<Blog>
     */
    public List<Blog> getBlogByIds(final List<String> blogIds);

    /**
     * Find all Blogs
     * @return List<Blog>
     */
    public List<Blog> getBlogs();

    /**
     * Find all the active Blogs
     * @return List<Blog>
     */
    public List<Blog> getActiveBlogs();

    /**
     * Create a new Blog
     * @param blog
     * @return Blog created
     */
    public Blog create(final Blog blog);

    /**
     * Create new Blogs
     * @param blogs
     * @return List<Blog> created
     */
    public List<Blog> create(final List<Blog> blogs);

    /**
     * Update an Blog
     * @param blog
     * @return Blog updated
     */
    public Blog update(final Blog blog);

    /**
     * Delete an existing Blog by its id
     * @param blogId
     */
    public void delete(final String blogId);

}
