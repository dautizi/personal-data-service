package com.danieleautizi.service.personaldata.controller

import com.danieleautizi.service.personaldata.IntegrationTestBase
import com.danieleautizi.service.personaldata.model.presentation.Image
import com.danieleautizi.service.personaldata.model.presentation.Skill
import com.fasterxml.jackson.core.type.TypeReference
import io.restassured.http.ContentType
import org.springframework.http.HttpStatus
import spock.lang.Unroll

import static io.restassured.RestAssured.given

@Unroll
class SkillControllerSpec extends IntegrationTestBase {

    def 'Create a new skill through #endpoint and check if it has been successfully created'() {

        given:
            def skillToCreate = Skill.builder()
                                     .groupName("Languages")
                                     .title("Java")
                                     .progress(100)
                                     .percent(100)
                                     .years(7)
                                     .since(FIXED_TODAY.minusYears(7))
                                     .imageUrl("http://test.com/product-cover.png")
                                     .prg(1)
                                     .active(true)
                                     .build()

        expect:
            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(skillToCreate))
                                .post(endpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def skillTypeRef = new TypeReference<Skill>(){}
            def skillCreated = (Skill) objectMapper.readValue(actual, skillTypeRef)

            // search the skill we created
            def stored = skillManager.getSkillById(skillCreated.getId())
            def expected = objectMapper.writeValueAsString(stored)

            expected == actual

        where:
            endpoint = "/skills/"
            statusCode = HttpStatus.OK.value()
    }

    def 'Create a new skill, get and delete it by service'() {

        given:
            def skillTypeRef = new TypeReference<Skill>(){}

            def skillToCreate = Skill.builder()
                                     .groupName("Languages")
                                     .title("Java")
                                     .progress(100)
                                     .percent(100)
                                     .years(7)
                                     .since(FIXED_TODAY.minusYears(7))
                                     .imageUrl("http://test.com/product-cover.png")
                                     .prg(1)
                                     .active(true)
                                     .build()

            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(skillToCreate))
                                .post(createEndpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def skillCreated = (Skill) objectMapper.readValue(actual, skillTypeRef)
            def idCreated = skillCreated.getId()

            def getAndDeleteEndpoint = "${createEndpoint}${idCreated}"

        expect:
            def getResponse = given().contentType(ContentType.JSON)
                                     .when()
                                     .get(getAndDeleteEndpoint)
                                     .then()
                                     .statusCode(statusCode)
                                     .extract().body().asString()

            // search the skill we created
            getResponse == actual

            // delete it
            given().contentType(ContentType.JSON)
                   .when()
                   .delete(getAndDeleteEndpoint)
                   .then()
                   .statusCode(statusCode)

            def stored = skillManager.getSkillById(idCreated)

            !stored

        where:
            createEndpoint = "/skills/"
            statusCode = HttpStatus.OK.value()
    }

    def 'Create and update a skill'() {

        given:
            def skillTypeRef = new TypeReference<Skill>(){}

            def skillToCreate = Skill.builder()
                                     .groupName("Languages")
                                     .title("Java")
                                     .progress(70)
                                     .percent(70)
                                     .years(7)
                                     .since(FIXED_TODAY.minusYears(7))
                                     .imageUrl("http://test.com/product-cover.png")
                                     .prg(1)
                                     .active(true)
                                     .build()

            def actual = given().contentType(ContentType.JSON)
                                .when()
                                .body(objectMapper.writeValueAsString(skillToCreate))
                                .post(endpoint)
                                .then()
                                .statusCode(statusCode)
                                .extract().body().asString()

            def skillCreated = (Skill) objectMapper.readValue(actual, skillTypeRef)
            def idCreated = skillCreated.getId()

        expect:
            // search the skill we created
            def stored = skillManager.getSkillById(idCreated)
            def created = objectMapper.writeValueAsString(stored)

            created == actual

            def updated = skillToCreate
            updated.setId(idCreated)
            updated.setPercent(100)
            updated.setProgress(100)

            def updateResponse = given().contentType(ContentType.JSON)
                                        .when()
                                        .body(objectMapper.writeValueAsString(updated))
                                        .put(endpoint)
                                        .then()
                                        .statusCode(statusCode)
                                        .extract().body().asString()

            // search again the skill we created and updated later
            def skillStored = skillManager.getSkillById(idCreated)
            def skillUpdated = objectMapper.writeValueAsString(skillStored)

            updateResponse == skillUpdated

        where:
            endpoint = "/skills/"
            statusCode = HttpStatus.OK.value()
    }
}
