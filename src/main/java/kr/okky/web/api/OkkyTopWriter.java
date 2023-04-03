package kr.okky.web.api;

import lombok.Data;

@Data
public class OkkyTopWriter {
    private Long id;
    private String nickname;
    private String picture;
    private String pictureType;
    private Integer gotPoints;
}
