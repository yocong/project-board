package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// JpaRepository를 이용하여 데이터베이스를 조작하기 위한 메서드들을 제공 (findAll(), findById(), save()) 등의 메서드 사용 가능
// 복잡한 JDBC(Java DataBase Connectivity) 코드를 작성하지 않아도 간단하게 DB와의 데이터 접근 작업을 처리할 수 있다
@RepositoryRestResource // 명시해 준 것만 rest api로 노출 시킴
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>, // Article에 대한 검색 기능 추가
        QuerydslBinderCustomizer<QArticle> {
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true); // 리스트에 포함되지 않은 것은 검색 안되게 해줌 (검색하고 싶은 것만 할 수 있게 만듦)
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // 대소문자 구분 안하고 검색
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
