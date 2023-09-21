package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.type.SearchType;
import com.fastcampus.projectboard.dto.ArticleDto;
import com.fastcampus.projectboard.dto.ArticleUpdateDto;
import com.fastcampus.projectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    // Mock 주입 대상에 @InjectMocks 어노테이션, 나머지는 @Mock
    @InjectMocks private ArticleService sut; // 테스트 해야 할 service

    @Mock private ArticleRepository articleRepository;

    /*
        게시판 페이지 테스트 기능 구현

        - 검색
        - 각 게시글 페이지로 이동
        - 페이지네이션
        - 홈 버튼 -> 게시판 페이지로 리다이렉션
        - 정렬 기능
     */

    // 게시글 검색 시 게시글 리스트, 게시글 페이지네이션, 정렬 기능 모두 구현됨 (Page)
    @DisplayName("게시글 검색 시, 게시글 리스트 반환")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList() {
        // Given

        // When
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); // 제목, 본문, ID, 닉네임, 해시태그

        // Then
        assertThat(articles).isNotNull();
    }

    // 각 게시글 페이지로 이동
    @DisplayName("게시글을 조회 시, 게시글을 반환")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
        // Given

        // When
        ArticleDto articles = sut.searchArticle(1L);

        // Then
        assertThat(articles).isNotNull();
    }


    /*
        게시글 페이지 테스트 기능 구현
        - 게시글 CRUD
     */

    @DisplayName("게시글 정보를 입력하면, 게시글 생성") // Create
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
        // Given
        given(articleRepository.save(any(Article.class))).willReturn(null); // Article 클래스 아무거나(any) 저장시켜주면 null 반환

        // When
        sut.saveArticle(ArticleDto.of(LocalDateTime.now(), "yocong", "title", "content", "#java")); // 정보 저장

        // Then
        then(articleRepository).should().save(any(Article.class)); // save를 articleRepositoy에서 한 번 호출했는가

    }

    @DisplayName("게시글 ID와 수정 정보를 입력하면, 게시글 수정") // Update
    @Test
    void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdatesArticle() {
        // Given
        given(articleRepository.save(any(Article.class))).willReturn(null); // Article 클래스 아무거나(any) 저장시켜주면 null 반환

        // When
        sut.updateArticle(1L, ArticleUpdateDto.of("title", "content", "#java")); // ID, 수정 가능한 정보들만 수정

        // Then
        then(articleRepository).should().save(any(Article.class)); // save를 articleRepositoy에서 한 번 호출했는가

    }

    @DisplayName("게시글 ID를 입력하면, 게시글 삭제") // Delete
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle() {
        // Given
        willDoNothing().given(articleRepository).delete(any(Article.class));

        // When
        sut.deleteArticle(1L);

        // Then
        then(articleRepository).should().delete(any(Article.class));
    }

}
