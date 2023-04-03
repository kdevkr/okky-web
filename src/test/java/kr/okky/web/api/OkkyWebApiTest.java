package kr.okky.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@DisplayName("OKKY 웹 API 테스트")
class OkkyWebApiTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OkkyWebApi okkyWebApi;

    @Value("${okky.id}")
    String id;

    @Value("${okky.password}")
    String password;

    private String accessToken;

    @BeforeEach
    void loadAccessToken() {
        // NOTE: Require apply OKKY_ID and OKKY_PASSWORD using environment variables.
        this.accessToken = okkyWebApi.loginAndGetAccessToken(id, password);
    }

    @Order(0)
    @DisplayName("Top Writers")
    @Test
    void TestGetTopWriters() {
        List<OkkyTopWriter> topWriters = okkyWebApi.getTopWriters();
        Assertions.assertNotNull(topWriters);
        Assertions.assertTrue(topWriters.size() > 0);
    }

    @Order(1)
    @DisplayName("프로필 조회")
    @Test
    void TestGetProfile() {
        Assertions.assertNotNull(accessToken);

        OkkyUserDetail myProfile = okkyWebApi.getUserProfile("me", accessToken);
        Assertions.assertNotNull(myProfile);
    }

    @Order(2)
    @DisplayName("토큰 갱신")
    @Test
    void TestAuth() {
        Assertions.assertNotNull(accessToken);

        String newAccessToken = okkyWebApi.auth(accessToken);
        Assertions.assertNotEquals(accessToken, newAccessToken);
    }

    @Order(3)
    @DisplayName("Q&A")
    @Test
    void TestGetQuestions() {
        Assertions.assertNotNull(accessToken);

        OkkyArticleList questions = okkyWebApi.getQuestions(new OkkyArticleList.Params());
        Assertions.assertNotNull(questions);
        Assertions.assertTrue(questions.getTotalPages() > 0);
        Assertions.assertTrue(questions.getTotalElements() > 0);
        Assertions.assertFalse(questions.getContent().isEmpty());
    }

    @Order(4)
    @DisplayName("사는얘기")
    @Test
    void TestGetLife() {
        Assertions.assertNotNull(accessToken);

        OkkyArticleList life = okkyWebApi.getLife(new OkkyArticleList.Params());
        Assertions.assertNotNull(life);
        Assertions.assertTrue(life.getTotalPages() > 0);
        Assertions.assertTrue(life.getTotalElements() > 0);
        Assertions.assertFalse(life.getContent().isEmpty());
    }
}
