package me.jungtaemin.simpleboard.service;

import lombok.RequiredArgsConstructor;
import me.jungtaemin.simpleboard.domain.Article;
import me.jungtaemin.simpleboard.dto.ArticleRequestDto;
import me.jungtaemin.simpleboard.dto.ArticleResponseDto;
import me.jungtaemin.simpleboard.messaging.ArticleCreatedEvent;
import me.jungtaemin.simpleboard.messaging.ArticleEventPublisher;
import me.jungtaemin.simpleboard.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleStatsService articleStatsService;
    private final ArticleEventPublisher eventPublisher;

    private String currentEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !StringUtils.hasText(auth.getName())) {
            throw new SecurityException("인증이 필요합니다.");
        }
        return auth.getName();
    }

    @Transactional
    public ArticleResponseDto create(ArticleRequestDto dto) {
        String email = currentEmail();
        Article article = Article.builder()
                .title(dto.title())
                .content(dto.content())
                .author(email)
                .build();

        Article saved = articleRepository.save(article);

        eventPublisher.publishArticleCreated(
                new ArticleCreatedEvent(
                        saved.getId(),
                        saved.getTitle(),
                        saved.getAuthor(),
                        Instant.now()
                )
        );

        return new ArticleResponseDto(saved.getId(), saved.getTitle(), saved.getContent(), saved.getAuthor());
    }

    @Transactional(readOnly = true)
    public Page<ArticleResponseDto> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable)
                .map(a -> new ArticleResponseDto(a.getId(), a.getTitle(), a.getContent(), a.getAuthor()));
    }

    @Transactional(readOnly = true)
    public ArticleResponseDto findById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        articleStatsService.incrementView(id);
        return new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent(), article.getAuthor());
    }

    @Transactional
    public ArticleResponseDto update(Long id, ArticleRequestDto dto) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        String email = currentEmail();
        if (!email.equals(article.getAuthor())) {
            throw new SecurityException("작성자만 수정할 수 있습니다.");
        }
        article.update(dto.title(), dto.content());
        return new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent(), article.getAuthor());
    }

    @Transactional
    public void delete(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        String email = currentEmail();
        if (!email.equals(article.getAuthor())) {
            throw new SecurityException("작성자만 삭제할 수 있습니다.");
        }
        articleRepository.delete(article);
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> topN(int n) {
        List<Long> ids = articleStatsService.topN(n);
        if (ids.isEmpty()) return List.of();

        List<Article> articles = articleRepository.findAllById(ids);
        Map<Long, Article> byId = articles.stream().collect(Collectors.toMap(Article::getId, a -> a));

        return ids.stream()
                .map(byId::get)
                .filter(Objects::nonNull)
                .map(a -> new ArticleResponseDto(a.getId(), a.getTitle(), a.getContent(), a.getAuthor()))
                .toList();
    }

    @Transactional
    public long getViews(Long id) {
        return articleStatsService.getViews(id);
    }
}