package com.fastcampus.projectboard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter // 모든 필드에 접근 가능 (Lombok에서 제공)
@ToString // 객체를 문자열로 표현하여, 객체의 필드 값을 쉽게 확인 (Lombok에서 제공)
@Table(indexes = { // 클래스가 데이터베이스 테이블을 나타내는 데 사용
        @Index(columnList = "title"), // 빠르게 서칭 가능 하도록 indexing
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class) // Config에서 Auditing한 것 동작하려면 필요
@Entity
public class Article extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // not null (nullable = false, 필수)
    @Setter @Column(nullable = false) private String title; // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문

    // null (필수 아님)
    @Setter private String hashtag; // 해시태그

    @ToString.Exclude
    @OrderBy("id") // id 기준으로 정렬
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // ArticleComment는 Article에 의해 매핑되므로 mappedBy = "article" , cascade = CascadeType.ALL : 부모 엔티티 변경 되면 자식도 변경
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    protected Article() {
    }

    private Article(String title, String content, String hashtag) { // 외부에서 호출 할 수 없으므로(private) 팩토리 메서드 이용
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) { // 정적 팩토리 메서드
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
