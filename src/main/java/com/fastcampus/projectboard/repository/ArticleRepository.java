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
    Page<Article> findByTitle(String title, Pageable pageable); // 제목을 기반으로 Article을 페이징하여 검색하는 메서드를 정의
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true); // 리스트에 포함되지 않은 것은 검색 안되게 해줌 (검색하고 싶은 것만 할 수 있게 만듦)
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy); // 검색 조건 포함
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // 대소문자 구분 안하고 검색
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // 날짜 일치 검색
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
