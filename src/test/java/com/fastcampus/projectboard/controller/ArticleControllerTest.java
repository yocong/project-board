package com.fastcampus.projectboard.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시글")
@WebMvcTest(ArticleController.class) // WebMvcTest는 입력되어있는 클래스만 test함 - 가벼워짐
class ArticleControllerTest {

    private final MockMvc mvc;

    public ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상 호출") // `/articles` - 게시판 페이지
    @Test
    public void givenNothing_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/articles")) // '/articles' 엔드포인트로 HTTP GET 요청을 생성하고, 해당 요청에 대한 응답을 검증
                .andExpect(status().isOk()) // 특정 HTTP 요청에 대한 응답이 성공적으로 이루어졌는지
                .andExpect(content().contentType(MediaType.TEXT_HTML)) // 응답의 콘텐츠 유형이 "text/html"임을 검증
                .andExpect(model().attributeExists("articles")); // 모델에 "articles"라는 속성이 존재하는지 확인
    }

    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상 호출") // `/articles{article-id}` - 게시글 페이지
    @Test
    public void givenNothing_whenRequestingArticleView_thenReturnsArticleView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("article")); // article(게시글) 존재 하는지
    }

    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상 호출") // `/articles/search` - 게시판 검색 전용 페이지
    @Test
    public void givenNothing_whenRequestingArticleSearchView_thenReturnsArticleSearchView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML));
    }

    @DisplayName("[view][GET] 게시글 검색 페이지 - 정상 호출") // `/articles/search-hashtag` - 게시판 해시태그 검색 페이지
    @Test
    public void givenNothing_whenRequestingArticleHashtagSearchView_thenReturnsArticleHashtagSearchView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML));
    }
}