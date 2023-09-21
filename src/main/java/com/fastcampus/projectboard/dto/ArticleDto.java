package com.fastcampus.projectboard.dto;

import java.time.LocalDateTime;

/**
 * DTO for {@link com.fastcampus.projectboard.domain.Article}
 */
public record ArticleDto( // DTO : 데이터 전달 객체, 이 클래스는 불변성을 가지며, 생성자에 필드 값을 전달 가능
                          LocalDateTime createdAt,
                          String createdBy,
                          String title,
                          String content,
                          String hashtag
) {
    public static ArticleDto of(LocalDateTime createdAt, String createdBy, String title, String content, String hashtag) {
        return new ArticleDto(createdAt, createdBy, title, content, hashtag);
    }
}