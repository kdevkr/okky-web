package kr.okky.web.api;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OkkyArticleList {
    private List<OkkyArticle> content = new ArrayList<>();
    private Integer size;
    private Integer totalElements;
    private Integer totalPages;

    @Data
    public static class Params {
        private int page = 0;
        private int size = 20;
        private Order order = Order.DESC;
        private Sort sort = Sort.ID;
        private String keyword;
        private String categoryCode;

        public enum Order {
            ASC, DESC
        }

        public enum Sort {
            ID, // 최신순
            VOTE_COUNT, // 추천순
            NOTE_COUNT, // 댓글순
            SCRAP_COUNT, // 스크랩순
            VIEW_COUNT // 조회순
        }
    }
}
