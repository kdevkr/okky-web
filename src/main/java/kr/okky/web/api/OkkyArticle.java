package kr.okky.web.api;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OkkyArticle {
    private Long id;
    private String title;
    private List<Tag> tags = new ArrayList<>();
    private Category category;
    private OkkyUserDetail.Avatar displayAuthor;
    private Long noteCount;
    private Long scrapCount;
    private Long viewCount;
    private Long voteCount;
    private Boolean choice;
    private Boolean anonymity;
    private Boolean enabled;
    private String dateCreated;

    @Data
    public static class Tag {
        private String name;
    }

    @Data
    public static class Category {
        private String code;
        private String labelCode;
        private String defaultLabel;
    }
}
