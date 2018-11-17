package com.danieleautizi.service.personaldata.controller

import com.danieleautizi.service.personaldata.IntegrationTestBase
import com.danieleautizi.service.personaldata.model.presentation.Education
import com.danieleautizi.service.personaldata.model.presentation.Image
import com.fasterxml.jackson.core.type.TypeReference
import io.restassured.http.ContentType
import org.springframework.http.HttpStatus
import spock.lang.Unroll

import static io.restassured.RestAssured.given

@Unroll
class EducationControllerSpec extends IntegrationTestBase {

    def 'Create a new education through #endpoint and check if it has been successfully created'() {

        given:
            def title = "Software Engineering"
            def school = "La Sapienza University"
            def description = "Description for a specific education experience."
            def schoolImage = "http://test.com/images/school/university-1.jpg"
            def schoolThumb = "http://test.com/images/school/university-1-thumb.jpg"
            def startYear = "2001"
            def endYear = "2006"
            def active = true
            def prg = 1

            def educationToCreate = Education.builder()
                                             .title(title)
                                             .school(school)
                                             .description(description)
                                             .schoolImage(schoolImage)
                                             .schoolThumb(schoolThumb)
                                             .startYear(startYear)
                                             .endYear(endYear)
                                             .active(active)
                                             .prg(prg)
                                             .build()

        expect:
            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(educationToCreate))
                                .post(endpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def educationTypeRef = new TypeReference<Education>(){}
            def educationCreated = (Education) objectMapper.readValue(actual, educationTypeRef)

            // search the education we created
            def stored = educationManager.getEducationById(educationCreated.getId())
            def expected = objectMapper.writeValueAsString(stored)

            expected == actual

        where:
            endpoint = "/educations/"
            statusCode = HttpStatus.OK.value()
    }

    def 'Create a new education, get and delete it by service'() {

        given:
            def educationTypeRef = new TypeReference<Education>(){}

            def title = "Software Engineering"
            def school = "La Sapienza University"
            def description = "Description for a specific education experience."
            def schoolImage = "http://test.com/images/school/university-1.jpg"
            def schoolThumb = "http://test.com/images/school/university-1-thumb.jpg"
            def startYear = "2001"
            def endYear = "2006"
            def active = true
            def prg = 1

            def educationToCreate = Education.builder()
                                             .title(title)
                                             .school(school)
                                             .description(description)
                                             .schoolImage(schoolImage)
                                             .schoolThumb(schoolThumb)
                                             .startYear(startYear)
                                             .endYear(endYear)
                                             .active(active)
                                             .prg(prg)
                                             .build()

            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(educationToCreate))
                                .post(createEndpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def educationCreated = (Education) objectMapper.readValue(actual, educationTypeRef)
            def idCreated = educationCreated.getId()

            def getAndDeleteEndpoint = "${createEndpoint}${idCreated}"

        expect:
            def getResponse = given().contentType(ContentType.JSON)
                                     .when()
                                     .get(getAndDeleteEndpoint)
                                     .then()
                                     .statusCode(statusCode)
                                     .extract().body().asString()

            // search the education we created
            getResponse == actual

            // delete it
            given().contentType(ContentType.JSON)
                   .when()
                   .delete(getAndDeleteEndpoint)
                   .then()
                   .statusCode(statusCode)

            def stored = educationManager.getEducationById(idCreated)

            !stored

        where:
            createEndpoint = "/educations/"
            statusCode = HttpStatus.OK.value()
    }

    def 'Create and update an education'() {

        given:
            def educationTypeRef = new TypeReference<Education>(){}

            def title = "Software Engineering"
            def school = "La Sapienza University"
            def description = "Description for a specific education experience."
            def schoolImage = "http://test.com/images/school/university-1.jpg"
            def schoolThumb = "http://test.com/images/school/university-1-thumb.jpg"
            def startYear = "2001"
            def endYear = "2006"
            def active = true
            def prg = 1

            def educationToCreate = Education.builder()
                                             .title(title)
                                             .school(school)
                                             .description(description)
                                             .schoolImage(schoolImage)
                                             .schoolThumb(schoolThumb)
                                             .startYear(startYear)
                                             .endYear(endYear)
                                             .active(active)
                                             .prg(prg)
                                             .build()

            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(educationToCreate))
                                .post(endpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def educationCreated = (Education) objectMapper.readValue(actual, educationTypeRef)
            def idCreated = educationCreated.getId()

        expect:
            // search the education we created
            def stored = educationManager.getEducationById(idCreated)
            def created = objectMapper.writeValueAsString(stored)

            created == actual

            def updated = educationCreated
            updated.setId(idCreated)
            updated.setSchool("University Tor Vergata")

            def updateResponse = given().contentType(ContentType.JSON)
                                        .when()
                                        .body(objectMapper.writeValueAsString(updated))
                                        .put(endpoint)
                                        .then()
                                        .statusCode(statusCode)
                                        .extract().body().asString()

            // search again the education we created and updated later
            def educationStored = educationManager.getEducationById(idCreated)
            def educationUpdated = objectMapper.writeValueAsString(educationStored)

            updateResponse == educationUpdated

        where:
            endpoint = "/educations/"
            statusCode = HttpStatus.OK.value()
    }
}
