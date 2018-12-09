package com.danieleautizi.service.personaldata.controller

import com.danieleautizi.service.personaldata.IntegrationTestBase
import com.danieleautizi.service.personaldata.model.presentation.WorkExperience
import io.restassured.http.ContentType
import org.springframework.http.HttpStatus
import spock.lang.Unroll

import static io.restassured.RestAssured.given

@Unroll
class WorkExperienceControllerSpec extends IntegrationTestBase {

    def 'Create a new workExperience through #endpoint and check if it has been successfully created'() {

        given:
            def company = "L'Espresso Group"
            def role = "Fullstack Developer"
            def description = "Description for a specific work experience."
            def period = "2013 - 2015"
            def companyImage = "http://test.com/images/company/company-1.jpg"
            def companyThumb = "http://test.com/images/company/company-1-thumb.jpg"
            def startYear = "2013"
            def endYear = "2015"
            def active = true
            def prg = 1

            def workExperienceToCreate = WorkExperience.builder()
                                                       .company(company)
                                                       .role(role)
                                                       .description(description)
                                                       .period(period)
                                                       .companyImage(companyImage)
                                                       .companyThumb(companyThumb)
                                                       .startYear(startYear)
                                                       .endYear(endYear)
                                                       .active(active)
                                                       .prg(prg)
                                                       .build()

        expect:
            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(workExperienceToCreate))
                                .post(endpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def workExperienceCreated = objectMapper.readValue(actual, WorkExperience.class)

            // search the workExperience we created
            def stored = workExperienceManager.getWorkExperienceById(workExperienceCreated.getId())
            def expected = objectMapper.writeValueAsString(stored)

            expected == actual

        where:
            endpoint = "/work-experiences/"
            statusCode = HttpStatus.OK.value()
    }

    def 'Create a new workExperience, get and delete it by service'() {

        given:
            def company = "L'Espresso Group"
            def role = "Fullstack Developer"
            def description = "Description for a specific work experience."
            def period = "2013 - 2015"
            def companyImage = "http://test.com/images/company/company-1.jpg"
            def companyThumb = "http://test.com/images/company/company-1-thumb.jpg"
            def startYear = "2013"
            def endYear = "2015"
            def active = true
            def prg = 1

            def workExperienceToCreate = WorkExperience.builder()
                                                       .company(company)
                                                       .role(role)
                                                       .description(description)
                                                       .period(period)
                                                       .companyImage(companyImage)
                                                       .companyThumb(companyThumb)
                                                       .startYear(startYear)
                                                       .endYear(endYear)
                                                       .active(active)
                                                       .prg(prg)
                                                       .build()

            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(workExperienceToCreate))
                                .post(createEndpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def workExperienceCreated = objectMapper.readValue(actual, WorkExperience)
            def idCreated = workExperienceCreated.getId()

            def getAndDeleteEndpoint = "${createEndpoint}${idCreated}"

        expect:
            def getResponse = given().contentType(ContentType.JSON)
                                     .when()
                                     .get(getAndDeleteEndpoint)
                                     .then()
                                     .statusCode(statusCode)
                                     .extract().body().asString()

            // search the workExperience we created
            getResponse == actual

            // delete it
            given().contentType(ContentType.JSON)
                   .when()
                   .delete(getAndDeleteEndpoint)
                   .then()
                   .statusCode(statusCode)

            def stored = workExperienceManager.getWorkExperienceById(idCreated)

            !stored

        where:
            createEndpoint = "/work-experiences/"
            statusCode = HttpStatus.OK.value()
    }

    def 'Create and update an workExperience'() {

        given:
            def company = "L'Espresso Group"
            def role = "Fullstack Developer"
            def description = "Description for a specific work experience."
            def period = "2013 - 2015"
            def companyImage = "http://test.com/images/company/company-1.jpg"
            def companyThumb = "http://test.com/images/company/company-1-thumb.jpg"
            def startYear = "2013"
            def endYear = "2015"
            def active = true
            def prg = 1

            def workExperienceToCreate = WorkExperience.builder()
                                                       .company(company)
                                                       .role(role)
                                                       .description(description)
                                                       .period(period)
                                                       .companyImage(companyImage)
                                                       .companyThumb(companyThumb)
                                                       .startYear(startYear)
                                                       .endYear(endYear)
                                                       .active(active)
                                                       .prg(prg)
                                                       .build()

            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(workExperienceToCreate))
                                .post(endpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def workExperienceCreated = objectMapper.readValue(actual, WorkExperience.class)
            def idCreated = workExperienceCreated.getId()

        expect:
            // search the workExperience we created
            def stored = workExperienceManager.getWorkExperienceById(idCreated)
            def created = objectMapper.writeValueAsString(stored)

            created == actual

            def updated = workExperienceCreated
            updated.setDescription("Updated description for work experience.")

            def updateResponse = given().contentType(ContentType.JSON)
                                        .when()
                                        .body(objectMapper.writeValueAsString(updated))
                                        .put(endpoint)
                                        .then()
                                        .statusCode(statusCode)
                                        .extract().body().asString()

            // search again the workExperience we created and updated later
            def workExperienceStored = workExperienceManager.getWorkExperienceById(idCreated)
            def workExperienceUpdated = objectMapper.writeValueAsString(workExperienceStored)

            updateResponse == workExperienceUpdated

        where:
            endpoint = "/work-experiences/"
            statusCode = HttpStatus.OK.value()
    }
}
