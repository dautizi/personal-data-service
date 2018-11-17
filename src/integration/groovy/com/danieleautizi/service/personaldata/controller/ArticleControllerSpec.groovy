package com.danieleautizi.service.personaldata.controller

import com.danieleautizi.service.personaldata.IntegrationTestBase
import com.danieleautizi.service.personaldata.model.presentation.Adventure
import com.danieleautizi.service.personaldata.model.presentation.Article
import com.danieleautizi.service.personaldata.model.presentation.Image
import com.fasterxml.jackson.core.type.TypeReference
import io.restassured.http.ContentType
import org.springframework.http.HttpStatus
import spock.lang.Unroll

import static io.restassured.RestAssured.given

@Unroll
class ArticleControllerSpec extends IntegrationTestBase {

    def 'Create a new article through #endpoint and check if it has been successfully created'() {

        given:
            // create a couple of images for the article
            def image1ToCreate = Image.builder()
                                      .title("Picture 1")
                                      .url("http://test.com/images/picture-1.jpg")
                                      .type("picture")
                                      .active(true)
                                      .prg(1)
                                      .build()

            def image2ToCreate = Image.builder()
                                      .title("Picture 2")
                                      .url("http://test.com/images/picture-2.jpg")
                                      .type("picture")
                                      .active(true)
                                      .prg(2)
                                      .build()

            def images = [image1ToCreate, image2ToCreate] as List

            // save images
            def createdImages = given().contentType(ContentType.JSON)
                                       .when()
                                       .body(objectMapper.writeValueAsString(images))
                                       .post(createImageEndpoint)
                                       .then()
                                       .statusCode(statusCode)
                                       .extract().body().asString()
            def imageListTypeRef = new TypeReference<List<Image>>(){}
            def imageListCreated = objectMapper.readValue(createdImages, imageListTypeRef)

            // create article
            def article = Article.builder()
                                 .detailPath("/adventure/article-1.html")
                                 .url("http://test.com/adventure/rafting/article-1.html")
                                 .header("")
                                 .title("Article title")
                                 .image("http://test.com/images/pages/adventure/article-1-cover.jpg")
                                 .thumb("http://test.com/images/pages/adventure/thumb-article-1.jpg")
                                 .summary("Summary")
                                 .body("")
                                 .author("Daniele Autizi")
                                 .active(true)
                                 .prg(1)
                                 .images(imageListCreated)
                                 .build()

        expect:
            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(article))
                                .post(endpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()
            def adventureTypeRef = new TypeReference<Adventure>(){}
            def actualCreated = (Adventure) objectMapper.readValue(actual, adventureTypeRef)

            // search the article we created
            def stored = articleManager.getArticleById(actualCreated.getId())
            def expected = objectMapper.writeValueAsString(stored)

            expected == actual

        where:
            endpoint = "/articles/"
            createImageEndpoint = "/images/bulk"
            statusCode = HttpStatus.OK.value()
    }

}
