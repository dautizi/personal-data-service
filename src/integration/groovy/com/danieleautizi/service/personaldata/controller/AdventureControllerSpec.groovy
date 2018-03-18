package com.danieleautizi.service.personaldata.controller

import com.danieleautizi.service.personaldata.IntegrationTestBase
import com.danieleautizi.service.personaldata.model.presentation.Adventure
import com.danieleautizi.service.personaldata.model.presentation.AdventureMedia
import com.fasterxml.jackson.core.type.TypeReference
import io.restassured.http.ContentType
import org.springframework.http.HttpStatus
import spock.lang.Unroll

import java.time.ZonedDateTime

import static io.restassured.RestAssured.given

@Unroll
class AdventureControllerSpec extends IntegrationTestBase {

    def 'Create a new adventure through #endpoint and check if it has been successfully created'() {

        given:
            // create a couple of media for the adventure
            def media1 = AdventureMedia.builder()
                                       .mediaType("image")
                                       .mediaPath("/pages/adventure/location/picture-1.jpg")
                                       .mediaUrl("http://test.com/images/pages/adventure/location/picture-1.jpg")
                                       .title("Media title")
                                       .alt("Media alt")
                                       .cssClass("style-media-adventure")
                                       .prg(1)
                                       .active(true)
                                       .build()

            def media2 = AdventureMedia.builder()
                                       .mediaType("image")
                                       .mediaPath("/pages/adventure/location/picture-2.jpg")
                                       .mediaUrl("http://test.com/images/pages/adventure/location/picture-2.jpg")
                                       .title("Media title 2")
                                       .alt("Media alt 2")
                                       .cssClass("style-media-adventure")
                                       .prg(2)
                                       .active(true)
                                       .build()

            def media = [media1, media2] as List

            // save media
            def createdMedia = given().contentType(ContentType.JSON)
                                      .when()
                                      .body(objectMapper.writeValueAsString(media))
                                      .post(createMediaEndpoint)
                                      .then()
                                      .statusCode(statusCode)
                                      .extract().body().asString()
            def adventureMediaListTypeRef = new TypeReference<List<AdventureMedia>>(){}
            def adventureMediaListCreated = objectMapper.readValue(createdMedia, adventureMediaListTypeRef)

            // create adventure
            def adventure = Adventure.builder()
                                     .articleUniquePath("/adventure/article-1.html")
                                     .title("Adventure title")
                                     .category("adventure")
                                     .section("section")
                                     .tag("tag")
                                     .keywords("keyword1, keyword2, keyword3")
                                     .cssClass("style-adventure")
                                     .image("http://test.com/images/pages/adventure/article-1-cover.jpg")
                                     .icon("http://test.com/images/pages/adventure/icon-article-1-cover.jpg")
                                     .altImage("Alt article")
                                     .articleUrl("http://test.com/adventure/rafting/article-1.html")
                                     .description("Article description")
                                     .adventureType("")
                                     .staticUrl("http://test.com/static/pages/adventure/article-1.html")
                                     .viewType("article")
                                     .mediaCssClass("article-style")
                                     .active(true)
                                     .prg(1)
                                     .adventureMedia(adventureMediaListCreated)
                                     .build()

        expect:
            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(adventure))
                                .post(endpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()
            def adventureTypeRef = new TypeReference<Adventure>(){}
            def actualCreated = objectMapper.readValue(actual, adventureTypeRef)

            // search the adventureMedia we created
            def stored = adventureManager.getAdventureById(((Adventure) actualCreated).getId())
            def expected = objectMapper.writeValueAsString(stored)

            expected == actual

        where:
            endpoint = "/adventures/"
            createMediaEndpoint = "/adventures/media/bulk"
            statusCode = HttpStatus.OK.value()
    }

}
