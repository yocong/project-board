package com.fastcampus.projectboard.dto;

import com.fastcampus.projectboard.domain.Article;

import java.time.LocalDateTime;

// ArticleDto�� ArticleResponse�� ��� �ʿ��� ����
// Ŭ���̾�Ʈ���� ������ ��ȯ�� ȿ�������� �����ϰ�, ������ ����� ���õ� ���� �� ���� ���� ��ȣ ������ �ٷ�Ⱑ �� ������.
// ArticleDto: ��ƼƼ���� �ʿ��� �������� ������
// ArticleResponse: Ŭ���̾�Ʈ���� ������ ���� ����, ��ƼƼ�� ArticleDto���� ������ ���� �߿��� Ŭ���̾�Ʈ���� ������ ���·� ����

public record ArticleDto( // DTO : ������ ���� ��ü, �� Ŭ������ �Һ����� ������, �����ڿ� �ʵ� ���� ���� ����
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) { // ArticleDto ��ü ���� (DTO �����ϰ� ����)
    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, String hashtag, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article entity) { //  ��ƼƼ�� DTO�� ��ȯ
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

    public Article toEntity() { // DTO�� ��ƼƼ�� ����
        return Article.of(
                userAccountDto.toEntity(),
                title,
                content,
                hashtag
        );
    }

}