package com.fastcampus.projectboard.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter // 모든 필드에 접근 가능 (Lombok에서 제공)
@ToString // 객체를 문자열로 표현하여, 객체의 필드 값을 쉽게 확인 (Lombok에서 제공)
@Table(indexes = { // 클래스가 데이터베이스 테이블을 나타내는 데 사용
        @Index(columnList = "content"), // 빠르게 서칭 가능 하도록 indexing
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class ArticleComment extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // not null (nullable = false, 필수)
    @Setter @ManyToOne(optional = false) Article article; // 게시글(ID), Article article: ArticleComment 엔티티가 Article 엔티티 참조, N:1 관계(ArticleComment 입장에서), optional = false : null값 x
    @Setter @Column(nullable = false, length = 500) String content; // 본문


    protected ArticleComment() {
    }
    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }
    public static ArticleComment of(Article article, String content) {
        return new ArticleComment(article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
