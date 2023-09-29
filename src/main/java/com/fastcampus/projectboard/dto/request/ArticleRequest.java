package com.fastcampus.projectboard.dto.request;

import com.fastcampus.projectboard.dto.ArticleDto;
import com.fastcampus.projectboard.dto.UserAccountDto;

public record ArticleRequest( //  게시글 생성 또는 수정 요청을 처리하기 위한 DTO
        String title,
        String content,
        String hashtag
) {

    public static ArticleRequest of(String title, String content, String hashtag) {
        return new ArticleRequest(title, content, hashtag);
    }

    public ArticleDto toDto(UserAccountDto userAccountDto) { // ArticleRequest 객체 -> ArticleDto 객체로 변환 (UserAccountDto - 사용자 정보) 포함
        return ArticleDto.of(
                userAccountDto,
                title,
                content,
                hashtag
        );
    }

}