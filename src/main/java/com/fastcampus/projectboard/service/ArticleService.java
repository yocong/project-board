package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.domain.type.SearchType;
import com.fastcampus.projectboard.dto.ArticleDto;
import com.fastcampus.projectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 게시글 관련 비즈니스 로직을 처리하는 Service 클래스
@RequiredArgsConstructor // 필수 필드를 가지고 있는 생성자를 자동으로 생성
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true) // 읽기 전용 모드
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) { // 게시글 검색 메서드
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) { // 게시글 조회 메서드, articleId로 검색, ArticleWithCommentsDto 형태로 반환
        return null; // ArticleWithCommentsDto 형태로 반환하는 이유: 특정 게시글을 조회할 때 그 게시글에 대한 댓글 정보를 포함하여 반환하기 때문
    }

    public void saveArticle(ArticleDto dto) { // 새로운 게시글을 저장하는 메서드, dto에 저장할 게시글 정보 포함
    }

    public void updateArticle(ArticleDto dto) { // 게시글을 업데이트하는 메서드, dto에 업데이트 할 게시글 정보 포함
    }

    public void deleteArticle(long articleId) { // 게시글을 삭제하는 메서드
    }
}
