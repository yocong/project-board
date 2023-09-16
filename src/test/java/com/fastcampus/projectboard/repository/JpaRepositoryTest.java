package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.config.JpaConfig;
import com.fastcampus.projectboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class) // Auditing 기능을 가져오기 위해
@DataJpaTest
class JpaRepositoryTest {
    // 테스트 대상
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    // Autowired를 통한 의존성 주입 (생성자 주입 방법을 사용)
    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given

        // When
        List<Article> articles = articleRepository.findAll(); // 모든 Article 엔티티를 검색해서 List에 넣고, articles를 통해 사용

        // Then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }


    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        // Given
        long previousCount = articleRepository.count(); // 카운트

        // When
        Article savedArticle = articleRepository.save(Article.of("new article", "new content", "#spring"));

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1); // insert후 count 증가 확인
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow(); // id 불러옴
        String updatedHashtag = "#springboot"; // hashtag 값 수정
        article.setHashtag(updatedHashtag); // 수정된 hashtag로 초기화

        // When
        Article savedArticle = articleRepository.saveAndFlush(article);

        // Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag); // hashtag가 updatedHashtag로 업데이트 되었는가?
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow(); // id 불러옴
        long previousArticleCount = articleRepository.count(); // 글 삭제 전 count
        long previousArticleCommentCount = articleCommentRepository.count(); // (글 삭제하면 댓글도 삭제) 댓글 삭제 전 count
        int deletedCommentsSize = article.getArticleComments().size(); // 삭제된 댓글 count

        // When
        articleRepository.delete(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1); // 글 삭제 후 count 동일 여부
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize); // 댓글 삭제 후 count 동일 여부
    }
}
