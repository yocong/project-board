package com.fastcampus.projectboard.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    @ToString.Exclude // 순환참조 막음
    @OrderBy("id") // id 기준으로 정렬
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // ArticleComment는 Article에 의해 매핑되므로 mappedBy = "article" , cascade = CascadeType.ALL : 부모 엔티티 변경 되면 자식도 변경
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>(); // 양방향 바인딩

    // Entity는 기본적으로 기본생성자를 가지고 있음
    protected Article() {
    }

    // 도메인과 관련있는 것들만 생성자 만듦 (메타데이터는 자동으로 생성되기 때문에 만들어 줄 필요 X)
    private Article(String title, String content, String hashtag) { // 외부에서 호출 할 수 없으므로(private) 팩토리 메서드 이용
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) { // 위 생성자를 정적 팩토리 메서드로 호출
        return new Article(title, content, hashtag);
    }

    // 위 내용을 List or Collection에 넣어야 할 때 중복요소 제거할 때나 비교해야할 때 동일성 검정을 해줄 수 있는 equals() & hashcode() 필요
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
