package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// JPA를 사용하여 Article 엔티티를 관리하는 데 필요한 Repository를 정의
// 이 Repository는 Article 엔티티와 관련된 다양한 데이터베이스 조작 작업을 수행
@RepositoryRestResource // 명시해 준 것만 rest api로 노출
public interface ArticleRepository extends
        JpaRepository<Article, Long>, // Article 엔티티를 관리하기 위한 기본적인 CRUD메서드 제공
        QuerydslPredicateExecutor<Article>, // Article에 대한 검색 기능 추가
        QuerydslBinderCustomizer<QArticle> { // 검색 조건 설정
    Page<Article> findByTitleContaining(String title, Pageable pageable); // Containing : 부분 검색
    Page<Article> findByContentContaining(String content, Pageable pageable); // 부분 검색
    Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pageable); // 부분 검색
    Page<Article> findByUserAccount_NicknameContaining(String nickname, Pageable pageable); // 부분 검색
    Page<Article> findByHashtag(String hashtag, Pageable pageable);
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true); // true로 리스팅하지 않은 필드에 대해서는 검색 기능 열지 않게 함
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy); // 검색 조건 포함
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // 대소문자 구분 안하고 검색
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // 날짜 일치 검색
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
