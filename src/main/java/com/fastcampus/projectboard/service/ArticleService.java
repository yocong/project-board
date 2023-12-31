package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.UserAccount;
import com.fastcampus.projectboard.domain.constant.SearchType;
import com.fastcampus.projectboard.dto.ArticleDto;
import com.fastcampus.projectboard.dto.ArticleWithCommentsDto;
import com.fastcampus.projectboard.repository.ArticleRepository;
import com.fastcampus.projectboard.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

// 게시글 관련 비즈니스 로직을 처리하는 Service 클래스
@Slf4j // log 찍어볼 때 사용
@RequiredArgsConstructor // 필수 필드를 가지고 있는 생성자를 자동으로 생성
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true) // 읽기 전용 모드
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) { // 게시글 검색 메서드
        if (searchKeyword == null || searchKeyword.isBlank()) { // 검색 키워드가 없을 경우
            return articleRepository.findAll(pageable).map(ArticleDto::from);
            // ArticleDto 클래스의 from 메소드 값, 즉 Entity -> Dto화한 것을 매핑
        }

        return switch (searchType) {
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID ->
                    articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME ->
                    articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag("#" + searchKeyword, pageable).map(ArticleDto::from);
        };
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) { // 게시글 조회 메서드, articleId로 검색, ArticleWithCommentsDto로 반환 : 게시글에 대한 댓글 정보를 포함
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto dto) { // 새로운 게시글을 저장하는 메서드, dto에 저장할 게시글 정보 포함, void라 return x
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
        articleRepository.save(dto.toEntity(userAccount));
    }

    public void updateArticle(Long articleId, ArticleDto dto) { // 게시글을 업데이트하는 메서드, dto에 업데이트 할 게시글 정보 포함
        try {
            Article article = articleRepository.getReferenceById(articleId);
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());

            if (article.getUserAccount().equals(userAccount)) { // 게시글 사용자와 인증된 사용자가 같을 때 수행
                if (dto.title() != null) { article.setTitle(dto.title()); }
                if (dto.content() != null) { article.setContent(dto.content()); }
                article.setHashtag(dto.hashtag());
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 수정하는데 필요한 정보를 찾을 수 없습니다 - {} ", e.getLocalizedMessage());
        }
    }

    public void deleteArticle(long articleId, String userId) { // 게시글을 삭제하는 메서드
        articleRepository.deleteByIdAndUserAccount_UserId(articleId, userId);
    }

    public long getArticleCount() {
        return articleRepository.count();
    }

    // 해시태그 검색 기능
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtag(hashtag, pageable).map(ArticleDto::from);
    }

    // 해시태그 리스트 조회 기능
    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }
}
