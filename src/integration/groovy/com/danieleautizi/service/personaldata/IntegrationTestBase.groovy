package com.danieleautizi.service.personaldata

import com.danieleautizi.service.personaldata.controller.AdventureController
import com.danieleautizi.service.personaldata.controller.AdventureMediaController
import com.danieleautizi.service.personaldata.controller.ArticleController
import com.danieleautizi.service.personaldata.controller.BlogController
import com.danieleautizi.service.personaldata.controller.ImageController
import com.danieleautizi.service.personaldata.controller.SkillController
import com.danieleautizi.service.personaldata.manager.AdventureMediaManager
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import io.restassured.RestAssured
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@Slf4j(value = "LOG")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [PersonalDataServiceApplication])
class IntegrationTestBase extends Specification {

    @LocalServerPort
    private final int serverPort

    @Autowired
    protected final ObjectMapper objectMapper

    @Autowired
    protected final AdventureMediaController adventureMediaController
    @Autowired
    protected final AdventureMediaManager adventureMediaManager

    @Autowired
    protected final AdventureController adventureController

    @Autowired
    protected final ArticleController articleController

    @Autowired
    protected final BlogController blogController

    @Autowired
    protected final ImageController imageController

    @Autowired
    protected final SkillController skillController

    void setup() {

        RestAssured.port = serverPort
    }

}
