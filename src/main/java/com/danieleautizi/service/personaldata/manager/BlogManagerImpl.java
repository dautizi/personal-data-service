package com.danieleautizi.service.personaldata.manager;

import static com.danieleautizi.service.personaldata.utility.BlogConverter.entitiesToPresentation;
import static com.danieleautizi.service.personaldata.utility.BlogConverter.entityToPresentation;
import static com.danieleautizi.service.personaldata.utility.BlogConverter.presentationToEntity;
import static com.danieleautizi.service.personaldata.utility.DateTimeUtil.utcZonedLocalDateTimeNow;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.stringToObject;

import com.danieleautizi.service.personaldata.exception.BadRequestException;
import com.danieleautizi.service.personaldata.exception.NotFoundException;
import com.danieleautizi.service.personaldata.model.presentation.Blog;
import com.danieleautizi.service.personaldata.repository.BlogRepository;
import com.danieleautizi.service.personaldata.utility.BlogConverter;
import com.danieleautizi.service.personaldata.utility.PersonalDataUtil;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogManagerImpl implements BlogManager {

    @NonNull
    private BlogRepository blogRepository;

    @NonNull
    private final PersonalDataUtil personalDataUtil;

    public static final String BLOG_NOT_FOUND_MESSAGE = "blog.error.notfound";
    public static final String BLOG_BAD_REQUEST_MESSAGE = "blog.error.badrequest";

    /**
     * Find an Blog by its id
     * @param blogId
     * @return Blog
     */
    @Override
    public Blog getBlogById(final String blogId) {

        val blogEntity = blogRepository.findOne(stringToObject(blogId));
        if (blogEntity == null) {

            throw new NotFoundException(personalDataUtil.getMessage(BLOG_NOT_FOUND_MESSAGE, blogId));
        }

        return convertAndEnrichBlog(blogEntity);
    }

    /**
     * Find Blogs by a bunch of ids
     * @param blogIds
     * @return List<Blog>
     */
    @Override
    public List<Blog> getBlogByIds(final List<String> blogIds) {

        return entitiesToPresentation(blogRepository.findBlogsByIdInOrderByPrgAsc(stringToObject(blogIds)));
    }

    /**
     * Find all Blogs
     * @return List<Blog>
     */
    @Override
    public List<Blog> getBlogs() {

        return entitiesToPresentation(blogRepository.findAll());
    }

    /**
     * Find all active Blogs
     * @return List<Blog>
     */
    @Override
    public List<Blog> getActiveBlogs() {

        val blogEntities = blogRepository.findBlogsByActiveOrderByPrgAsc(true);
        if (blogEntities == null) {

            return null;
        }

        return blogEntities.stream()
                           .map(blogEntity -> convertAndEnrichBlog(blogEntity))
                           .collect(Collectors.toList());
    }

    /**
     * Create a new Blog
     * @param blog
     * @return Blog created
     */
    @Override
    public Blog create(final Blog blog) {

        val now = utcZonedLocalDateTimeNow();
        blog.setDatetime(now);
        blog.setLastUpdate(now);
        val blogEntity = presentationToEntity(blog);

        return entityToPresentation(blogRepository.save(blogEntity));
    }

    /**
     * Create new Blogs
     * @param blogs
     * @return List<Blog> created
     */
    public List<Blog> create(final List<Blog> blogs) {

        return blogs.stream()
                         .map(blog -> create(blog))
                         .collect(Collectors.toList());
    }

    /**
     * Update an Blog
     * @param blog
     * @return Blog updated
     */
    public Blog update(final Blog blog) {

        if (blog == null) {

            throw new BadRequestException(personalDataUtil.getMessage(BLOG_BAD_REQUEST_MESSAGE, blog));
        }

        if (getBlogById(blog.getId()) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(BLOG_NOT_FOUND_MESSAGE, blog.getId()));
        }

        blog.setLastUpdate(utcZonedLocalDateTimeNow());
        val blogEntity = presentationToEntity(blog);

        return entityToPresentation(blogRepository.save(blogEntity));
    }

    /**
     * Delete an existing Blog by its id
     * @param blogId
     */
    public void delete(final String blogId) {

        if (getBlogById(blogId) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(BLOG_NOT_FOUND_MESSAGE, blogId));
        }

        blogRepository.delete(stringToObject(blogId));
    }

    private Blog convertAndEnrichBlog(final com.danieleautizi.service.personaldata.model.entity.Blog blogEntity) {

        val blogPresentation = BlogConverter.entityToPresentation(blogEntity);
        val prg = blogEntity.getPrg();
        if (prg > 1) {

            val prev = BlogConverter.entityToPresentation(blogRepository.findFirstByActiveIsTrueAndPrg(prg - 1));
            blogPresentation.setPrev(prev);
        }

        val next = BlogConverter.entityToPresentation(blogRepository.findFirstByActiveIsTrueAndPrg(prg + 1));
        blogPresentation.setNext(next);

        return blogPresentation;
    }
}
