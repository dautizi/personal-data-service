package com.danieleautizi.service.personaldata.controller

import com.danieleautizi.service.personaldata.IntegrationTestBase
import com.danieleautizi.service.personaldata.model.presentation.AdventureMedia
import com.fasterxml.jackson.core.type.TypeReference
import io.restassured.http.ContentType
import org.springframework.http.HttpStatus
import spock.lang.Unroll

import static io.restassured.RestAssured.given

@Unroll
class AdventureMediaControllerSpec extends IntegrationTestBase {

    def 'Create a new adventure media through #endpoint and check if it has been successfully created'() {

        given:
            def mediaType = "image"
            def mediaPath = "/pages/adventure/location/picture-1.jpg"
            def mediaUrl = "http://test.com/images/pages/adventure/location/picture-1.jpg"
            def title = "Media title"
            def alt = "Media alt"
            def cssClass = "style-media-adventure"
            def prg = 1

            def adventureMediaToCreate = AdventureMedia.builder()
                                                       .mediaType(mediaType)
                                                       .mediaPath(mediaPath)
                                                       .mediaUrl(mediaUrl)
                                                       .title(title)
                                                       .alt(alt)
                                                       .cssClass(cssClass)
                                                       .prg(prg)
                                                       .active(true)
                                                       .build()

        expect:
            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(adventureMediaToCreate))
                                .post(endpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            // search the adventureMedia we created
            def stored = adventureMediaManager.getAdventureMediaByPath(mediaPath)
            def expected = objectMapper.writeValueAsString(stored)

            expected == actual

        where:
            endpoint = "/adventures/media/"
            statusCode = HttpStatus.OK.value()
    }

    def 'Create a new adventure media, get and delete it by service'() {

        given:
            def adventureMediaTypeRef = new TypeReference<AdventureMedia>(){}

            def mediaType = "image"
            def mediaPath = "/pages/adventure/location/picture-1.jpg"
            def mediaUrl = "http://test.com/images/pages/adventure/location/picture-1.jpg"
            def title = "Media title"
            def alt = "Media alt"
            def cssClass = "style-media-adventure"
            def prg = 1

            def adventureMediaToCreate = AdventureMedia.builder()
                                                       .mediaType(mediaType)
                                                       .mediaPath(mediaPath)
                                                       .mediaUrl(mediaUrl)
                                                       .title(title)
                                                       .alt(alt)
                                                       .cssClass(cssClass)
                                                       .prg(prg)
                                                       .active(true)
                                                       .build()

            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(adventureMediaToCreate))
                                .post(createEndpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def adventureMediaCreated = objectMapper.readValue(actual, adventureMediaTypeRef)
            def idCreated = ((AdventureMedia) adventureMediaCreated).getId()

            def getAndDeleteEndpoint = "${createEndpoint}${idCreated}"

        expect:
            def getResponse = given().contentType(ContentType.JSON)
                                     .when()
                                     .get(getAndDeleteEndpoint)
                                     .then()
                                     .statusCode(statusCode)
                                     .extract().body().asString()

            // search the adventureMedia we created
            getResponse == actual

            // delete it
            given().contentType(ContentType.JSON)
                   .when()
                   .delete(getAndDeleteEndpoint)
                   .then()
                   .statusCode(statusCode)
                   .extract().body().asString()

            def stored = adventureMediaManager.getAdventureMediaById(idCreated)

            !stored

        where:
            createEndpoint = "/adventures/media/"
            statusCode = HttpStatus.OK.value()
    }

    def 'Create and update an adventure media'() {

        given:
            def adventureMediaTypeRef = new TypeReference<AdventureMedia>(){}

            def mediaType = "image"
            def mediaPath = "/pages/adventure/location/picture-1.jpg"
            def mediaUrl = "http://test.com/images/pages/adventure/location/picture-1.jpg"
            def title = "Media title"
            def alt = "Media alt"
            def cssClass = "style-media-adventure"
            def prg = 1

            def adventureMediaToCreate = AdventureMedia.builder()
                                                       .mediaType(mediaType)
                                                       .mediaPath(mediaPath)
                                                       .mediaUrl(mediaUrl)
                                                       .title(title)
                                                       .alt(alt)
                                                       .cssClass(cssClass)
                                                       .prg(prg)
                                                       .active(true)
                                                       .build()

            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(adventureMediaToCreate))
                                .post(endpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def adventureMediaCreated = objectMapper.readValue(actual, adventureMediaTypeRef)
            def idCreated = ((AdventureMedia) adventureMediaCreated).getId()

        expect:
            // search the adventureMedia we created
            def stored = adventureMediaManager.getAdventureMediaById(idCreated)
            def created = objectMapper.writeValueAsString(stored)

            created == actual

            def updated = adventureMediaToCreate
            updated.setId(idCreated)
            updated.setTitle("Title Updated")
            updated.setCssClass("latest-style")

            def updateResponse = given().contentType(ContentType.JSON)
                                        .when()
                                        .body(objectMapper.writeValueAsString(updated))
                                        .put(endpoint)
                                        .then()
                                        .statusCode(statusCode)
                                        .extract().body().asString()

            // search again the adventureMedia we created and updated later
            def adventureMediaStored = adventureMediaManager.getAdventureMediaById(idCreated)
            def adventureMediaUpdated = objectMapper.writeValueAsString(adventureMediaStored)

            updateResponse == adventureMediaUpdated

        where:
            endpoint = "/adventures/media/"
            statusCode = HttpStatus.OK.value()
    }
}
