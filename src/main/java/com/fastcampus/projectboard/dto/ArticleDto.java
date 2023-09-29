package com.fastcampus.projectboard.dto;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.UserAccount;

import java.time.LocalDateTime;

// ArticleDto와 ArticleResponse가 모두 필요한 이유
// 클라이언트와의 데이터 교환을 효율적으로 관리하고, 데이터 노출과 관련된 보안 및 개인 정보 보호 문제를 다루기가 더 쉬워짐.
// ArticleDto: 엔티티에서 필요한 정보만을 가져옴
// ArticleResponse: 클라이언트에게 보여줄 최종 버전, 엔티티와 ArticleDto에서 가져온 정보 중에서 클라이언트에게 적합한 형태로 가공

public record ArticleDto( // DTO : 데이터 전달 객체, 이 클래스는 불변성을 가지며, 생성자에 필드 값을 전달 가능
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) { // ArticleDto 객체 생성 (DTO 간편하게 생성)
    public static ArticleDto of(UserAccountDto userAccountDto, String title, String content, String hashtag) {
        return new ArticleDto(null, userAccountDto, title, content, hashtag, null, null, null, null);
    }
    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, String hashtag, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article entity) { //  엔티티를 DTO로 변환
        return new ArticleDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public Article toEntity(UserAccount userAccount) {// DTO를 엔티티로 복원
        return Article.of(
                userAccount,
                title,
                content,
                hashtag
        );
    }

}