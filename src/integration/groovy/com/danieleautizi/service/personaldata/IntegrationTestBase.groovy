package com.danieleautizi.service.personaldata

import com.danieleautizi.service.personaldata.controller.AdventureController
import com.danieleautizi.service.personaldata.controller.AdventureMediaController
import com.danieleautizi.service.personaldata.controller.ArticleController
import com.danieleautizi.service.personaldata.controller.BlogController
import com.danieleautizi.service.personaldata.controller.EducationController
import com.danieleautizi.service.personaldata.controller.ImageController
import com.danieleautizi.service.personaldata.controller.SkillController
import com.danieleautizi.service.personaldata.controller.WorkExperienceController
import com.danieleautizi.service.personaldata.manager.AdventureManager
import com.danieleautizi.service.personaldata.manager.AdventureMediaManager
import com.danieleautizi.service.personaldata.manager.ArticleManager
import com.danieleautizi.service.personaldata.manager.BlogManager
import com.danieleautizi.service.personaldata.manager.EducationManager
import com.danieleautizi.service.personaldata.manager.ImageManager
import com.danieleautizi.service.personaldata.manager.SkillManager
import com.danieleautizi.service.personaldata.manager.WorkExperienceManager
import com.danieleautizi.service.personaldata.utility.DateTimeUtil
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import io.restassured.RestAssured
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

@Slf4j(value = "LOG")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [PersonalDataServiceApplication])
class IntegrationTestBase extends Specification {

    @LocalServerPort
    private final int serverPort

    @Autowired
    protected final ObjectMapper objectMapper

    @Autowired
    protected final AdventureController adventureController
    @Autowired
    protected final AdventureManager adventureManager

    @Autowired
    protected final AdventureMediaController adventureMediaController
    @Autowired
    protected final AdventureMediaManager adventureMediaManager

    @Autowired
    protected final ArticleController articleController
    @Autowired
    protected final ArticleManager articleManager

    @Autowired
    protected final BlogController blogController
    @Autowired
    protected final BlogManager blogManager

    @Autowired
    protected final EducationController educationController
    @Autowired
    protected final EducationManager educationManager

    @Autowired
    protected final ImageController imageController
    @Autowired
    protected final ImageManager imageManager

    @Autowired
    protected final SkillController skillController
    @Autowired
    protected final SkillManager skillManager

    @Autowired
    protected final WorkExperienceController workExperienceController
    @Autowired
    protected final WorkExperienceManager workExperienceManager

    protected static final ZonedDateTime FIXED_TODAY = DateTimeUtil.fixClockAt(ZonedDateTime.of(LocalDate.of(2018, 3, 25), LocalTime.MIN, ZoneOffset.UTC))

    void setup() {

        RestAssured.port = serverPort
    }

}
