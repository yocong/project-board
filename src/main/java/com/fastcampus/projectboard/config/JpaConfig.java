package com.fastcampus.projectboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing // 메타데이터들을 자동으로 업데이트 (@CreatedDate, @CreatedBy 등의 어노테이션 생성하여 사용)
@Configuration // 여러가지 필요한 설정들을 적음
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() { // 엔티티의 생성자와 수정자를 추적하고 기록하는 데 사용
        return () -> Optional.of("yocong"); // TODO: 스프링 시큐리티로 인증 기능을 붙이게 될 때, 수정하자
    }
}
