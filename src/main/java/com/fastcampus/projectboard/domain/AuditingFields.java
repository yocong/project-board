package com.fastcampus.projectboard.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@ToString
@EntityListeners(AuditingEntityListener.class) // Config에서 Auditing한 것 동작하려면 필요
@MappedSuperclass // 엔티티 클래스 간에 공통 매핑 정보를 공유할 때 사용 (여기서는 공통인 metadata 부분을 가져옴)
public class AuditingFields {

    // metadata(not null)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // 날짜와 시간 형식을 지정하고 데이터 바인딩을 제어하는 데 사용
    @CreatedDate
    @Column(nullable = false, updatable = false) // update 불가
    private LocalDateTime createdAt; // 생성일시

    @CreatedBy
    @Column(nullable = false, updatable = false, length = 100) // update 불가
    private String createdBy; // 생성자

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일시

    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy; // 수정자
}
