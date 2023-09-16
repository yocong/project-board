package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.domain.Article;
import jakarta.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// JpaRepository를 이용하여 데이터베이스를 조작하기 위한 메서드들을 제공 (findAll(), findById(), save()) 등의 메서드 사용 가능
// 복잡한 JDBC(Java DataBase Connectivity) 코드를 작성하지 않아도 간단하게 DB와의 데이터 접근 작업을 처리할 수 있다
@RepositoryRestResource // 명시해 준 것만 rest api로 노출 시킴
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
