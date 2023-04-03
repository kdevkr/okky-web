package kr.okky.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Component
public class OkkyWebApi {
    private static final String HOST = "https://okky.kr";

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    private String refreshToken;

    public OkkyWebApi(WebClient.Builder builder, ObjectMapper objectMapper) {
        this.webClient = builder.baseUrl(HOST).build();
        this.objectMapper = objectMapper;
    }

    public String loginAndGetAccessToken(@NotEmpty String id, @NotEmpty String password) {
        Map<String, String> reqBody = new HashMap<>();
        reqBody.put("id", id);
        reqBody.put("password", password);

        ClientResponse response = webClient.post()
                .uri(uri -> uri.path("/api/okky-web/auth/login").build())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(reqBody))
                .exchange()
                .block();


        if (response != null && response.cookies().containsKey("refresh_token")) {
            ResponseCookie refreshToken = response.cookies().getFirst("refresh_token");
            if (refreshToken != null) {
                this.refreshToken = refreshToken.getValue();
            }
        }

        if (response != null && response.cookies().containsKey("access_token")) {
            ResponseCookie accessToken = response.cookies().getFirst("access_token");
            if (accessToken != null) {
                return accessToken.getValue();
            }
        }

        return null;
    }

    public String auth(String accessToken) {
        if (refreshToken == null) {
            throw new RuntimeException("Required refresh_token");
        }

        ClientResponse response = webClient.get()
                .uri(uri -> uri.path("api/okky-web/auth/token").build())
                .cookie("access_token", accessToken)
                .cookie("refresh_token", refreshToken)
                .exchange()
                .block();

        if (response != null) {
            try {
                Map<String, String> responseBody = objectMapper.readValue(response.bodyToMono(String.class).block(),
                        new TypeReference<HashMap<String, String>>() {
                        });
                if (responseBody.containsKey("access_token")) {
                    return responseBody.get("access_token");
                }
            } catch (JsonProcessingException e) {
                return null;
            }
        }

        return null;
    }

    public OkkyUserDetail getUserProfile(String id, String accessToken) {
        String response = webClient.get()
                .uri(uri -> uri.path("/api/okky-web/users/{id}").build(id))
                .accept(MediaType.APPLICATION_JSON)
                .cookie("access_token", accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (response != null) {
            try {
                return objectMapper.readValue(response, OkkyUserDetail.class);
            } catch (JsonProcessingException e) {
                return null;
            }
        }

        return null;
    }

    public List<OkkyTopWriter> getTopWriters() {
        String response = webClient.get()
                .uri(uri -> uri.path("/api/okky-web/widgets/top-writers").build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            return objectMapper.readValue(response, new TypeReference<ArrayList<OkkyTopWriter>>() {});
        } catch (JsonProcessingException e) {
            return Collections.emptyList();
        }
    }

    public OkkyArticleList getArticles(OkkyArticleList.Params params) {
        String categoryCode = params.getCategoryCode();
        String api = "questions".equals(categoryCode) ? "/api/okky-web/questions" : "/api/okky-web/articles";

        String response = webClient.get()
                .uri(uri -> uri.path(api)
                        .queryParam("page", params.getPage())
                        .queryParam("size", params.getSize())
                        .queryParam("order", params.getOrder().name())
                        .queryParam("keyword", params.getKeyword() == null ? "" : params.getKeyword())
                        .queryParam("categoryCode", categoryCode)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        try {
            return objectMapper.readValue(response, OkkyArticleList.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public OkkyArticleList getLife(OkkyArticleList.Params params) {
        params.setCategoryCode("life");
        return getArticles(params);
    }

    public OkkyArticleList getQuestions(OkkyArticleList.Params params) {
        params.setCategoryCode("questions");
        return getArticles(params);
    }

}
