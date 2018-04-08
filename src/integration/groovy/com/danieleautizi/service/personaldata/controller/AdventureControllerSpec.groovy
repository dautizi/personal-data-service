package com.danieleautizi.service.personaldata.controller

import com.danieleautizi.service.personaldata.IntegrationTestBase
import com.danieleautizi.service.personaldata.model.presentation.Adventure
import com.danieleautizi.service.personaldata.model.presentation.AdventureMedia
import com.fasterxml.jackson.core.type.TypeReference
import io.restassured.http.ContentType
import org.springframework.http.HttpStatus
import spock.lang.Unroll

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
            def actualCreated = (Adventure) objectMapper.readValue(actual, adventureTypeRef)

            // search the adventureMedia we created
            def stored = adventureManager.getAdventureById(actualCreated.getId())
            def expected = objectMapper.writeValueAsString(stored)

            expected == actual

        where:
            endpoint = "/adventures/"
            createMediaEndpoint = "/adventures/media/bulk"
            statusCode = HttpStatus.OK.value()
    }

    def 'Create 3 adventures and check if prev/next are properly set'() {

        given:
            def adventureMediaTypeRef = new TypeReference<AdventureMedia>(){}
            def adventureTypeRef = new TypeReference<Adventure>(){}
            def adventureListTypeRef = new TypeReference<List<Adventure>>(){}

            // create 1 media per adventure
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

            // save media
            def createdMedia1 = given().contentType(ContentType.JSON)
                                       .when()
                                       .body(objectMapper.writeValueAsString(media1))
                                       .post(createMediaEndpoint)
                                       .then()
                                       .statusCode(statusCode)
                                       .extract().body().asString()
            def adventureMediaCreated = objectMapper.readValue(createdMedia1, adventureMediaTypeRef)

            // create adventure 1
            def adventure1 = Adventure.builder()
                                      .articleUniquePath("/adventure/adventure-1.html")
                                      .title("Adventure 1 title")
                                      .category("adventure")
                                      .section("section")
                                      .tag("tag")
                                      .keywords("keyword1, keyword2, keyword3")
                                      .cssClass("style-adventure")
                                      .image("http://test.com/images/pages/adventure/adventure-1-cover.jpg")
                                      .icon("http://test.com/images/pages/adventure/icon-adventure-1-cover.jpg")
                                      .altImage("Alt adventure 1")
                                      .articleUrl("http://test.com/adventure/rafting/adventure-1.html")
                                      .description("Article 1 description")
                                      .adventureType("")
                                      .staticUrl("http://test.com/static/pages/adventure/adventure-1.html")
                                      .viewType("article")
                                      .mediaCssClass("article-style")
                                      .active(true)
                                      .prg(1)
                                      .adventureMedia([adventureMediaCreated] as List)
                                      .build()

            // create media 2
            def media2 = AdventureMedia.builder()
                                       .mediaType("image")
                                       .mediaPath("/pages/adventure/location/picture-2.jpg")
                                       .mediaUrl("http://test.com/images/pages/adventure/location/picture-2.jpg")
                                       .title("Media title 2")
                                       .alt("Media alt 2")
                                       .cssClass("style-media-adventure")
                                       .prg(1)
                                       .active(true)
                                       .build()

            // save media 2
            def createdMedia2 = given().contentType(ContentType.JSON)
                                       .when()
                                       .body(objectMapper.writeValueAsString(media2))
                                       .post(createMediaEndpoint)
                                       .then()
                                       .statusCode(statusCode)
                                       .extract().body().asString()
            def adventureMedia2Created = objectMapper.readValue(createdMedia2, adventureMediaTypeRef)

            // create adventure 2
            def adventure2 = Adventure.builder()
                                      .articleUniquePath("/adventure/adventure-2.html")
                                      .title("Adventure 2 title")
                                      .category("adventure")
                                      .section("section")
                                      .tag("tag")
                                      .keywords("keyword1, keyword2, keyword3")
                                      .cssClass("style-adventure")
                                      .image("http://test.com/images/pages/adventure/adventure-2-cover.jpg")
                                      .icon("http://test.com/images/pages/adventure/icon-adventure-2-cover.jpg")
                                      .altImage("Alt article 2")
                                      .articleUrl("http://test.com/adventure/rafting/adventure-2.html")
                                      .description("Article 2 description")
                                      .adventureType("")
                                      .staticUrl("http://test.com/static/pages/adventure/adventure-2.html")
                                      .viewType("article")
                                      .mediaCssClass("article-style")
                                      .active(true)
                                      .prg(2)
                                      .adventureMedia([adventureMedia2Created] as List)
                                      .build()

            // create media 3
            def media3 = AdventureMedia.builder()
                                       .mediaType("image")
                                       .mediaPath("/pages/adventure/location/picture-3.jpg")
                                       .mediaUrl("http://test.com/images/pages/adventure/location/picture-3.jpg")
                                       .title("Media title 3")
                                       .alt("Media alt 3")
                                       .cssClass("style-media-adventure")
                                       .prg(1)
                                       .active(true)
                                       .build()

            // save media 3
            def createdMedia3 = given().contentType(ContentType.JSON)
                                       .when()
                                       .body(objectMapper.writeValueAsString(media2))
                                       .post(createMediaEndpoint)
                                       .then()
                                       .statusCode(statusCode)
                                       .extract().body().asString()
            def adventureMedia3Created = objectMapper.readValue(createdMedia3, adventureMediaTypeRef)

            // create adventure 3
            def adventure3 = Adventure.builder()
                                      .articleUniquePath("/adventure/adventure-3.html")
                                      .title("Adventure 3 title")
                                      .category("adventure")
                                      .section("section")
                                      .tag("tag")
                                      .keywords("keyword1, keyword2, keyword3")
                                      .cssClass("style-adventure")
                                      .image("http://test.com/images/pages/adventure/adventure-3-cover.jpg")
                                      .icon("http://test.com/images/pages/adventure/icon-adventure-3-cover.jpg")
                                      .altImage("Alt article 3")
                                      .articleUrl("http://test.com/adventure/rafting/adventure-3.html")
                                      .description("Article 3 description")
                                      .adventureType("")
                                      .staticUrl("http://test.com/static/pages/adventure/adventure-3.html")
                                      .viewType("article")
                                      .mediaCssClass("article-style")
                                      .active(true)
                                      .prg(3)
                                      .adventureMedia([adventureMedia3Created] as List)
                                      .build()
            def adventures = [adventure1, adventure2, adventure3] as List

        expect:
            def createdAdventures = given().contentType(ContentType.JSON)
                                           .when()
                                           .body(objectMapper.writeValueAsString(adventures))
                                           .post(endpoint)
                                           .then()
                                           .statusCode(statusCode)
                                           .extract().body().asString()
            def actualCreated = (List<Adventure>) objectMapper.readValue(createdAdventures, adventureListTypeRef)

            actualCreated.size() == 3

            // get the second adventure that has been created
            def createdAdventure2 = actualCreated.get(1)
            def getAdventureByIdEndpoint = "${getAdventureEndpoint}${createdAdventure2.getId()}"

            // search the createdAdventure2 by specific endpoint
            def adv2 = given().contentType(ContentType.JSON)
                              .when()
                              .get(getAdventureByIdEndpoint)
                              .then()
                              .statusCode(statusCode)
                              .extract().body().asString()
            def actualAdv2 = (Adventure) objectMapper.readValue(adv2, adventureTypeRef)

            def stored1 = adventureManager.getAdventureById(actualCreated.get(0).getId())
            def expectedPrev = objectMapper.writeValueAsString(stored1)

            def stored3 = adventureManager.getAdventureById(actualCreated.get(2).getId())
            def expectedNext = objectMapper.writeValueAsString(stored3)

            actualAdv2.getPrev() == expectedPrev
            actualAdv2.getNext() == expectedNext

        where:
            endpoint = "/adventures/bulk"
            createMediaEndpoint = "/adventures/media/"
            getAdventureEndpoint = "/adventures/"
            statusCode = HttpStatus.OK.value()
    }
}
