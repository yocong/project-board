package com.fastcampus.projectboard.config;

import com.fastcampus.projectboard.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing // 메타데이터들을 자동으로 업데이트 (@CreatedDate, @CreatedBy 등의 어노테이션 생성하여 사용)
@Configuration // 여러가지 필요한 설정들을 적음
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() { // 엔티티의 생성자와 수정자를 추적하고 기록하는 데 사용
        return () -> Optional.ofNullable(SecurityContextHolder.getContext()) // SecurityContextHolder : 인증 정보를 가진 클래스
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated) // 인증 되는지 확인
                .map(Authentication::getPrincipal) // 인증이 되는게 확인됐으니 principal 꺼내옴
                .map(BoardPrincipal.class::cast)
                .map(BoardPrincipal::getUsername);
    }
}
