package com.fastcampus.projectboard.repository.querydsl;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom {

    public ArticleRepositoryCustomImpl() {
        super(Article.class); // Article 엔티티와 관련된 Querydsl 생성 가능
    }

    @Override
    public List<String> findAllDistinctHashtags() {
        QArticle article = QArticle.article; // QArticle : Article 엔티티와 관련된 쿼리를 작성하기 위한 클래스

        return from(article)// Article 엔티티를 대상으로 한 Querydsl 쿼리를 시작
                .distinct()// 중복 제거
                .select(article.hashtag) // Article 엔티티의 hashtag 속성을 선택
                .where(article.hashtag.isNotNull()) // hashtag 속성이 null이 아닌 경우에 대한 조건 추가
                .fetch(); // 쿼리를 실행하고 결과를 리스트로 반환
    }
}
