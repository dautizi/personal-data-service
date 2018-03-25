package com.danieleautizi.service.personaldata.controller

import com.danieleautizi.service.personaldata.IntegrationTestBase
import com.danieleautizi.service.personaldata.model.presentation.Image
import com.fasterxml.jackson.core.type.TypeReference
import io.restassured.http.ContentType
import org.springframework.http.HttpStatus
import spock.lang.Unroll

import static io.restassured.RestAssured.given

@Unroll
class ImageControllerSpec extends IntegrationTestBase {

    def 'Create a new image through #endpoint and check if it has been successfully created'() {

        given:
            def url = "http://test.com/images/picture-1.jpg"
            def type = "picture"

            def imageToCreate = Image.builder()
                                     .url(url)
                                     .type(type)
                                     .active(true)
                                     .build()

        expect:
            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(imageToCreate))
                                .post(endpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def imageTypeRef = new TypeReference<Image>(){}
            def imageCreated = (Image) objectMapper.readValue(actual, imageTypeRef)

            // search the image we created
            def stored = imageManager.getImageById(imageCreated.getId())
            def expected = objectMapper.writeValueAsString(stored)

            expected == actual

        where:
            endpoint = "/images/"
            statusCode = HttpStatus.OK.value()
    }

    def 'Create a new image, get and delete it by service'() {

        given:
            def imageTypeRef = new TypeReference<Image>(){}

            def url = "http://test.com/images/picture-1.jpg"
            def type = "picture"

            def imageToCreate = Image.builder()
                                     .url(url)
                                     .type(type)
                                     .active(true)
                                     .build()

            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(imageToCreate))
                                .post(createEndpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def imageCreated = (Image) objectMapper.readValue(actual, imageTypeRef)
            def idCreated = imageCreated.getId()

            def getAndDeleteEndpoint = "${createEndpoint}${idCreated}"

        expect:
            def getResponse = given().contentType(ContentType.JSON)
                                     .when()
                                     .get(getAndDeleteEndpoint)
                                     .then()
                                     .statusCode(statusCode)
                                     .extract().body().asString()

            // search the image we created
            getResponse == actual

            // delete it
            given().contentType(ContentType.JSON)
                   .when()
                   .delete(getAndDeleteEndpoint)
                   .then()
                   .statusCode(statusCode)

            def stored = imageManager.getImageById(idCreated)

            !stored

        where:
            createEndpoint = "/images/"
            statusCode = HttpStatus.OK.value()
    }

    def 'Create and update an image'() {

        given:
            def imageTypeRef = new TypeReference<Image>(){}

            def url = "http://test.com/images/picture-1.jpg"
            def type = "picture"

            def imageToCreate = Image.builder()
                                     .url(url)
                                     .type(type)
                                     .active(true)
                                     .build()

            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(imageToCreate))
                                .post(endpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def imageCreated = (Image) objectMapper.readValue(actual, imageTypeRef)
            def idCreated = imageCreated.getId()

        expect:
            // search the image we created
            def stored = imageManager.getImageById(idCreated)
            def created = objectMapper.writeValueAsString(stored)

            created == actual

            def updated = imageToCreate
            updated.setId(idCreated)
            updated.setType("Cover")

            def updateResponse = given().contentType(ContentType.JSON)
                                        .when()
                                        .body(objectMapper.writeValueAsString(updated))
                                        .put(endpoint)
                                        .then()
                                        .statusCode(statusCode)
                                        .extract().body().asString()

            // search again the image we created and updated later
            def imageStored = imageManager.getImageById(idCreated)
            def imageUpdated = objectMapper.writeValueAsString(imageStored)

            updateResponse == imageUpdated

        where:
            endpoint = "/images/"
            statusCode = HttpStatus.OK.value()
    }
}
