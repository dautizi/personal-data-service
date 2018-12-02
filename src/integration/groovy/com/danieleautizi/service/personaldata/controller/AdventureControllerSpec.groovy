package com.danieleautizi.service.personaldata.controller

import com.danieleautizi.service.personaldata.IntegrationTestBase
import com.danieleautizi.service.personaldata.model.presentation.Adventure
import com.danieleautizi.service.personaldata.model.presentation.AdventureMedia
import com.danieleautizi.service.personaldata.utility.AdventureConverter
import com.fasterxml.jackson.core.type.TypeReference
import io.restassured.http.ContentType
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import spock.lang.Unroll

import static io.restassured.RestAssured.given

@Unroll
class AdventureControllerSpec extends IntegrationTestBase {

    private static final String MEDIA_TYPE = "image"
    private static final String MEDIA_CSS_CLASS = "style-media-adventure"

    // MEDIA 1
    private static final ObjectId MEDIA_ID_1 = ObjectId.get()
    private static final String MEDIA_PATH_1 = "/pages/adventure/location/picture-1.jpg"
    private static final String MEDIA_URL_1 = "http://test.com/images/pages/adventure/location/picture-1.jpg"
    private static final String MEDIA_TITLE_1 = "Media title 1"
    private static final String MEDIA_ALT_1 = "Media alt 1"

    // MEDIA 2
    private static final ObjectId MEDIA_ID_2 = ObjectId.get()
    private static final String MEDIA_PATH_2 = "/pages/adventure/location/picture-2.jpg"
    private static final String MEDIA_URL_2 = "http://test.com/images/pages/adventure/location/picture-2.jpg"
    private static final String MEDIA_TITLE_2 = "Media title 2"
    private static final String MEDIA_ALT_2 = "Media alt 2"

    // MEDIA 3
    private static final ObjectId MEDIA_ID_3 = ObjectId.get()
    private static final String MEDIA_PATH_3 = "/pages/adventure/location/picture-3.jpg"
    private static final String MEDIA_URL_3 = "http://test.com/images/pages/adventure/location/picture-3.jpg"
    private static final String MEDIA_TITLE_3 = "Media title 3"
    private static final String MEDIA_ALT_3 = "Media alt 3"


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
            def adventureTypeRef = new TypeReference<Adventure>(){}
            def adventureListTypeRef = new TypeReference<List<Adventure>>(){}

            wipeAdventures()
            createAdventureMedia()

            // create adventure 1
            def adventure1 = com.danieleautizi.service.personaldata.model.entity.Adventure.builder()
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
                                                                                          .adventureMediaIds([MEDIA_ID_1] as List)
                                                                                          .datetime(FIXED_TODAY.toLocalDateTime())
                                                                                          .lastUpdate(FIXED_TODAY.toLocalDateTime())
                                                                                          .build()
            def savedAdventure1 = adventureRepository.save(adventure1)

            // create adventure 2
            def adventure2 = com.danieleautizi.service.personaldata.model.entity.Adventure.builder()
                                                                                          .articleUniquePath("/adventure/adventure-2.html")
                                                                                          .title("Adventure 2 title")
                                                                                          .category("adventure")
                                                                                          .section("section")
                                                                                          .tag("tag")
                                                                                          .keywords("keyword1, keyword2, keyword3")
                                                                                          .cssClass("style-adventure")
                                                                                          .image("http://test.com/images/pages/adventure/adventure-2-cover.jpg")
                                                                                          .icon("http://test.com/images/pages/adventure/icon-adventure-2-cover.jpg")
                                                                                          .altImage("Alt adventure 2")
                                                                                          .articleUrl("http://test.com/adventure/rafting/adventure-2.html")
                                                                                          .description("Article 2 description")
                                                                                          .adventureType("")
                                                                                          .staticUrl("http://test.com/static/pages/adventure/adventure-2.html")
                                                                                          .viewType("article")
                                                                                          .mediaCssClass("article-style")
                                                                                          .active(true)
                                                                                          .prg(2)
                                                                                          .adventureMediaIds([MEDIA_ID_2] as List)
                                                                                          .datetime(FIXED_TODAY.toLocalDateTime())
                                                                                          .lastUpdate(FIXED_TODAY.toLocalDateTime())
                                                                                          .build()
            def savedAdventure2 = adventureRepository.save(adventure2)

            // create adventure 3
            def adventure3 = com.danieleautizi.service.personaldata.model.entity.Adventure.builder()
                                                                                .articleUniquePath("/adventure/adventure-3.html")
                                                                                .title("Adventure 3 title")
                                                                                .category("adventure")
                                                                                .section("section")
                                                                                .tag("tag")
                                                                                .keywords("keyword1, keyword2, keyword3")
                                                                                .cssClass("style-adventure")
                                                                                .image("http://test.com/images/pages/adventure/adventure-3-cover.jpg")
                                                                                .icon("http://test.com/images/pages/adventure/icon-adventure-3-cover.jpg")
                                                                                .altImage("Alt adventure 3")
                                                                                .articleUrl("http://test.com/adventure/rafting/adventure-3.html")
                                                                                .description("Article 3 description")
                                                                                .adventureType("")
                                                                                .staticUrl("http://test.com/static/pages/adventure/adventure-3.html")
                                                                                .viewType("article")
                                                                                .mediaCssClass("article-style")
                                                                                .active(true)
                                                                                .prg(3)
                                                                                .adventureMediaIds([MEDIA_ID_3] as List)
                                                                                .datetime(FIXED_TODAY.toLocalDateTime())
                                                                                .lastUpdate(FIXED_TODAY.toLocalDateTime())
                                                                                .build()
            def savedAdventure3 = adventureRepository.save(adventure3)
            def adventures = AdventureConverter.entitiesToPresentation([savedAdventure1, savedAdventure2, savedAdventure3] as List)

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

            // get previous expected adventure and wipe next and prev for it
            def expectedPrev = adventureManager.getAdventureById(actualCreated.get(0).getId())
            expectedPrev.setPrev(null)
            expectedPrev.setNext(null)

            def expectedNext = adventureManager.getAdventureById(actualCreated.get(2).getId())
            expectedNext.setPrev(null)
            expectedNext.setNext(null)

            objectMapper.writeValueAsString(actualAdv2.getPrev()) == objectMapper.writeValueAsString(expectedPrev)
            objectMapper.writeValueAsString(actualAdv2.getNext()) == objectMapper.writeValueAsString(expectedNext)

        where:
            endpoint = "/adventures/bulk"
            createMediaEndpoint = "/adventures/media/"
            getAdventureEndpoint = "/adventures/"
            statusCode = HttpStatus.OK.value()
    }

    def 'Create adventures with different types and check all stored types'() {

        given:
            wipeAdventures()

            // adventure type
            def adventureType1 = "article"
            def adventureType2 = "video"
            def adventureType3 = "photogallery"
            def expected = [adventureType1, adventureType2, adventureType3] as Set

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
                                     .adventureType(adventureType1)
                                     .staticUrl("http://test.com/static/pages/adventure/article-1.html")
                                     .viewType("article")
                                     .mediaCssClass("article-style")
                                     .active(true)
                                     .prg(1)
                                     .adventureMedia(adventureMediaListCreated)
                                     .build()
        and:
            // create a new adventure re-using same previous media
            def adventure2 = Adventure.builder()
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
                                      .adventureType(adventureType2)
                                      .staticUrl("http://test.com/static/pages/adventure/article-1.html")
                                      .viewType("article")
                                      .mediaCssClass("article-style")
                                      .active(true)
                                      .prg(1)
                                      .adventureMedia(adventureMediaListCreated)
                                      .build()
        and:
            // create a new adventure re-using same previous media
            def adventure3 = Adventure.builder()
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
                                      .adventureType(adventureType3)
                                      .staticUrl("http://test.com/static/pages/adventure/article-1.html")
                                      .viewType("article")
                                      .mediaCssClass("article-style")
                                      .active(true)
                                      .prg(1)
                                      .adventureMedia(adventureMediaListCreated)
                                      .build()

            def adventures = [adventure, adventure2, adventure3] as List

            // save all of them
            given().contentType(ContentType.JSON)
                   .when()
                   .body(objectMapper.writeValueAsString(adventures))
                   .post(adventureBulkEndpoint)
                   .then()
                   .statusCode(statusCode)
                   .extract().body().asString()

        expect:
            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .get(endpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()
            def adventureTypesTypeRef = new TypeReference<Set<String>>(){}
            def actualAdventureTypes = (Set<String>) objectMapper.readValue(actual, adventureTypesTypeRef)

            expected == actualAdventureTypes

        where:
            adventureBulkEndpoint = "/adventures/bulk"
            endpoint = "/adventures/types"
            createMediaEndpoint = "/adventures/media/bulk"
            statusCode = HttpStatus.OK.value()
    }

    void createAdventureMedia() {

        def media1 = com.danieleautizi.service.personaldata.model.entity.AdventureMedia.builder()
                                                                                       .id(MEDIA_ID_1)
                                                                                       .mediaType(MEDIA_TYPE)
                                                                                       .mediaPath(MEDIA_PATH_1)
                                                                                       .mediaUrl(MEDIA_URL_1)
                                                                                       .title(MEDIA_TITLE_1)
                                                                                       .alt(MEDIA_ALT_1)
                                                                                       .cssClass(MEDIA_CSS_CLASS)
                                                                                       .prg(1)
                                                                                       .active(true)
                                                                                       .datetime(FIXED_TODAY.toLocalDateTime())
                                                                                       .lastUpdate(FIXED_TODAY.toLocalDateTime())
                                                                                       .build()

        def media2 = com.danieleautizi.service.personaldata.model.entity.AdventureMedia.builder()
                                                                                       .id(MEDIA_ID_2)
                                                                                       .mediaType(MEDIA_TYPE)
                                                                                       .mediaPath(MEDIA_PATH_2)
                                                                                       .mediaUrl(MEDIA_URL_2)
                                                                                       .title(MEDIA_TITLE_2)
                                                                                       .alt(MEDIA_ALT_2)
                                                                                       .cssClass(MEDIA_CSS_CLASS)
                                                                                       .prg(1)
                                                                                       .active(true)
                                                                                       .datetime(FIXED_TODAY.toLocalDateTime())
                                                                                       .lastUpdate(FIXED_TODAY.toLocalDateTime())
                                                                                       .build()

        def media3 = com.danieleautizi.service.personaldata.model.entity.AdventureMedia.builder()
                                                                                       .id(MEDIA_ID_3)
                                                                                       .mediaType(MEDIA_TYPE)
                                                                                       .mediaPath(MEDIA_PATH_3)
                                                                                       .mediaUrl(MEDIA_URL_3)
                                                                                       .title(MEDIA_TITLE_3)
                                                                                       .alt(MEDIA_ALT_3)
                                                                                       .cssClass(MEDIA_CSS_CLASS)
                                                                                       .prg(1)
                                                                                       .active(true)
                                                                                       .datetime(FIXED_TODAY.toLocalDateTime())
                                                                                       .lastUpdate(FIXED_TODAY.toLocalDateTime())
                                                                                       .build()

        adventureMediaRepository.save(media1)
        adventureMediaRepository.save(media2)
        adventureMediaRepository.save(media3)
    }

    void wipeAdventures() {

        adventureRepository.deleteAll()
    }
}
