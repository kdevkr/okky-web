package kr.okky.web.api;

import lombok.Data;

import java.util.List;

/**
 * dmAllowed: 뉴스레터 및 마케팅 메일 수신
 */
@Data
public class OkkyUserDetail {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private Avatar avatar;
    private List<Tag> tags;
    private List<OauthId> oauthIdList;
    private Boolean emailVerified;
    private Boolean dmAllowed;
    private Boolean createdByOAuth;

    @Data
    public static class Avatar {
        private Long id;
        private String nickname;
        private String picture;
        private String pictureType;
        private Long activityPoint;
        private Boolean withdraw;
    }

    @Data
    private static class Tag {
        private String name;
    }

    @Data
    private static class OauthId {
        private Long id;
        private String provider;
        private String socialId;
        private String picture;
        private String email;
    }
}
