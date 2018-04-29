package com.danieleautizi.service.personaldata.controller

import com.danieleautizi.service.personaldata.IntegrationTestBase
import com.danieleautizi.service.personaldata.model.presentation.Blog
import com.fasterxml.jackson.core.type.TypeReference
import io.restassured.http.ContentType
import org.springframework.http.HttpStatus
import spock.lang.Unroll

import static io.restassured.RestAssured.given

@Unroll
class BlogControllerSpec extends IntegrationTestBase {

    def 'Create a new blog through #endpoint and check if it has been successfully created'() {

        given:
            // create blog
            def blog = Blog.builder()
                           .title("Title")
                           .category("Category 1")
                           .section("Section")
                           .tag("Tag1")
                           .keywords("Key1, Key2, Key3")
                           .cssClass("style-blog")
                           .image("http://test.com/images/picture-1.jpg")
                           .icon("http://test.com/images/picture-1-icon.jpg")
                           .altImage("Alt image")
                           .articleUrl("http://test.com/adventure/blog-1.html")
                           .description("Description about blog.")
                           .staticUrl("http://test.com/static/blog/blog-1.html")
                           .blogType("Blog type")
                           .viewType("view")
                           .mediaCssClass("media-css-style")
                           .prg(1)
                           .active(true)
                           .build()

        expect:
            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(blog))
                                .post(endpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()
            def blogTypeRef = new TypeReference<Blog>(){}
            def actualCreated = (Blog) objectMapper.readValue(actual, blogTypeRef)

            // search the blogMedia we created
            def stored = blogManager.getBlogById(actualCreated.getId())
            def expected = objectMapper.writeValueAsString(stored)

            expected == actual

        where:
            endpoint = "/blogs/"
            statusCode = HttpStatus.OK.value()
    }

    def 'Create 3 blogs and check if prev/next are properly set'() {

        given:
            def blogTypeRef = new TypeReference<Blog>(){}
            def blogListTypeRef = new TypeReference<List<Blog>>(){}

            // create blog 1
            def blog1 = Blog.builder()
                            .title("Title")
                            .category("Category 1")
                            .section("Section")
                            .tag("Tag1")
                            .keywords("Key1, Key2, Key3")
                            .cssClass("style-blog")
                            .image("http://test.com/images/picture-1.jpg")
                            .icon("http://test.com/images/picture-1-icon.jpg")
                            .altImage("Alt image")
                            .articleUrl("http://test.com/adventure/blog-1.html")
                            .description("Description about blog.")
                            .staticUrl("http://test.com/static/blog/blog-1.html")
                            .blogType("Blog type")
                            .viewType("view")
                            .mediaCssClass("media-css-style")
                            .prg(1)
                            .active(true)
                            .build()

            // create blog 2
            def blog2 = Blog.builder()
                            .title("Title 2")
                            .category("Category 2")
                            .section("Section")
                            .tag("Tag2")
                            .keywords("Key1, Key2, Key3")
                            .cssClass("style-blog")
                            .image("http://test.com/images/picture-2.jpg")
                            .icon("http://test.com/images/picture-2-icon.jpg")
                            .altImage("Alt image")
                            .articleUrl("http://test.com/adventure/blog-2.html")
                            .description("Description about blog 2.")
                            .staticUrl("http://test.com/static/blog/blog-2.html")
                            .blogType("Blog type")
                            .viewType("view")
                            .mediaCssClass("media-css-style")
                            .prg(2)
                            .active(true)
                            .build()

            // create blog 3
            def blog3 = Blog.builder()
                            .title("Title 3")
                            .category("Category 3")
                            .section("Section")
                            .tag("Tag2")
                            .keywords("Key1, Key2, Key3")
                            .cssClass("style-blog")
                            .image("http://test.com/images/picture-3.jpg")
                            .icon("http://test.com/images/picture-3-icon.jpg")
                            .altImage("Alt image")
                            .articleUrl("http://test.com/adventure/blog-3.html")
                            .description("Description about blog 3.")
                            .staticUrl("http://test.com/static/blog/blog-3.html")
                            .blogType("Blog type")
                            .viewType("view")
                            .mediaCssClass("media-css-style")
                            .prg(3)
                            .active(true)
                            .build()

            def blogs = [blog1, blog2, blog3] as List

        expect:
            def createdBlogs = given().contentType(ContentType.JSON)
                                      .when()
                                      .body(objectMapper.writeValueAsString(blogs))
                                      .post(endpoint)
                                      .then()
                                      .statusCode(statusCode)
                                      .extract().body().asString()
            def actualCreated = (List<Blog>) objectMapper.readValue(createdBlogs, blogListTypeRef)

            actualCreated.size() == 3

            // get the second blog that has been created
            def createdBlog2 = actualCreated.get(1)
            def getBlogByIdEndpoint = "${getBlogEndpoint}${createdBlog2.getId()}"

            // search the createdBlog2 by specific endpoint
            def blg2 = given().contentType(ContentType.JSON)
                              .when()
                              .get(getBlogByIdEndpoint)
                              .then()
                              .statusCode(statusCode)
                              .extract().body().asString()
            def actualBlog2 = (Blog) objectMapper.readValue(blg2, blogTypeRef)

            // get previous expected blog and wipe next and prev for it
            def expectedPrev = blogManager.getBlogById(actualCreated.get(0).getId())
            expectedPrev.setPrev(null)
            expectedPrev.setNext(null)

            def expectedNext = blogManager.getBlogById(actualCreated.get(2).getId())
            expectedNext.setPrev(null)
            expectedNext.setNext(null)

            objectMapper.writeValueAsString(actualBlog2.getPrev()) == objectMapper.writeValueAsString(expectedPrev)
            objectMapper.writeValueAsString(actualBlog2.getNext()) == objectMapper.writeValueAsString(expectedNext)

        where:
            endpoint = "/blogs/bulk"
            createMediaEndpoint = "/blogs/media/"
            getBlogEndpoint = "/blogs/"
            statusCode = HttpStatus.OK.value()
    }

}
