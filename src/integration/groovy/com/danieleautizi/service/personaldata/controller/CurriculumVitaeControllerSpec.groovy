package com.danieleautizi.service.personaldata.controller

import com.danieleautizi.service.personaldata.IntegrationTestBase
import com.danieleautizi.service.personaldata.model.presentation.CurriculumVitae
import io.restassured.http.ContentType
import org.springframework.http.HttpStatus
import spock.lang.Unroll

import static io.restassured.RestAssured.given

@Unroll
class CurriculumVitaeControllerSpec extends IntegrationTestBase {

    def 'Create a new cv through #endpoint and check if it has been successfully created'() {

        given:
            def cvToCreate = CurriculumVitae.builder()
                                            .path("/cv/personal-cv-1.pdf")
                                            .title("CV - today")
                                            .filename("personal-cv-1.pdf")
                                            .description("description")
                                            .prg(1)
                                            .active(true)
                                            .build()

        expect:
            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(cvToCreate))
                                .post(endpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def cvCreated = objectMapper.readValue(actual, CurriculumVitae.class)

            // search the curriculum vitae created right now
            def stored = curriculumVitaeManager.getCurriculumVitaeById(cvCreated.getId())
            def expected = objectMapper.writeValueAsString(stored)

            expected == actual

        where:
            endpoint = "/curricula-vitae/"
            statusCode = HttpStatus.OK.value()
    }

    def 'Create a couple of cv and fetch last active'() {

        given:
            // Create the first curriculum vitae
            def cvToCreate = CurriculumVitae.builder()
                                            .path("/cv/personal-cv-1.pdf")
                                            .title("CV - today")
                                            .filename("personal-cv-1.pdf")
                                            .description("description")
                                            .prg(1)
                                            .active(false)
                                            .build()

            given().contentType(ContentType.JSON)
                   .when()
                   .body(objectMapper.writeValueAsString(cvToCreate))
                   .post(endpoint)
                   .then()
                   .statusCode(statusCode)
                   .extract().body().asString()

            // Create the second curriculum vitae
            def cv2ToCreate = CurriculumVitae.builder()
                                             .path("/cv/personal-cv-2.pdf")
                                             .title("CV - today")
                                             .filename("personal-cv-2.pdf")
                                             .description("description")
                                             .prg(2)
                                             .active(true)
                                             .build()

            def expectedLast =  given().contentType(ContentType.JSON)
                                       .when()
                                       .body(objectMapper.writeValueAsString(cv2ToCreate))
                                       .post(endpoint)
                                       .then()
                                       .statusCode(statusCode)
                                       .extract().body().asString()
            def expected = objectMapper.readValue(expectedLast, CurriculumVitae.class)

        expect:
            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .get(getEndpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def lastCv = objectMapper.readValue(actual, CurriculumVitae.class)

            expected == lastCv

        where:
            endpoint = "/curricula-vitae/"
            getEndpoint = "/curricula-vitae/last"
            statusCode = HttpStatus.OK.value()
    }

    def 'Create a new curriculum vitae, get and delete it by service'() {

        given:
            def cvToCreate = CurriculumVitae.builder()
                                            .path("/cv/personal-cv-1.pdf")
                                            .title("CV - today")
                                            .filename("personal-cv-1.pdf")
                                            .description("description")
                                            .prg(1)
                                            .active(true)
                                            .build()

            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(cvToCreate))
                                .post(createEndpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def cvCreated = objectMapper.readValue(actual, CurriculumVitae.class)
            def idCreated = cvCreated.getId()

            def getAndDeleteEndpoint = "${createEndpoint}${idCreated}"

        expect:
            def getResponse = given().contentType(ContentType.JSON)
                                     .when()
                                     .get(getAndDeleteEndpoint)
                                     .then()
                                     .statusCode(statusCode)
                                     .extract().body().asString()

            // search the curriculum vitae created right now
            getResponse == actual

            // delete it
            given().contentType(ContentType.JSON)
                   .when()
                   .delete(getAndDeleteEndpoint)
                   .then()
                   .statusCode(statusCode)

            def stored = curriculumVitaeManager.getCurriculumVitaeById(idCreated)

            !stored

        where:
            createEndpoint = "/curricula-vitae/"
            statusCode = HttpStatus.OK.value()
    }

    def 'Create and update a curriculum vitae'() {

        given:
            def cvToCreate = CurriculumVitae.builder()
                                            .path("/cv/personal-cv-1.pdf")
                                            .title("CV - today")
                                            .filename("personal-cv-1.pdf")
                                            .description("description")
                                            .prg(1)
                                            .active(true)
                                            .build()

            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(cvToCreate))
                                .post(endpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def cvCreated = objectMapper.readValue(actual, CurriculumVitae.class)
            def idCreated = cvCreated.getId()

        expect:
            // search the curriculum vitae we created
            def stored = curriculumVitaeManager.getCurriculumVitaeById(idCreated)
            def created = objectMapper.writeValueAsString(stored)

            created == actual

            def updated = cvCreated
            updated.setDescription("Updated description")
            updated.setTitle("Updated title")

            def updateResponse = given().contentType(ContentType.JSON)
                                        .when()
                                        .body(objectMapper.writeValueAsString(updated))
                                        .put(endpoint)
                                        .then()
                                        .statusCode(statusCode)
                                        .extract().body().asString()

            // search again the skill we created and updated later
            def cvStored = curriculumVitaeManager.getCurriculumVitaeById(idCreated)
            def cvUpdated = objectMapper.writeValueAsString(cvStored)

            updateResponse == cvUpdated

        where:
            endpoint = "/curricula-vitae/"
            statusCode = HttpStatus.OK.value()
    }

}
