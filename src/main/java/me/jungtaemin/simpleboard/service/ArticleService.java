package me.jungtaemin.simpleboard.service;

import lombok.RequiredArgsConstructor;
import me.jungtaemin.simpleboard.domain.Article;
import me.jungtaemin.simpleboard.dto.ArticleRequestDto;
import me.jungtaemin.simpleboard.dto.ArticleResponseDto;
import me.jungtaemin.simpleboard.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleResponseDto create(ArticleRequestDto dto) {
        Article article = Article.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(dto.getAuthor())
                .build();

        Article saved = articleRepository.save(article);
        return new ArticleResponseDto(saved.getId(), saved.getTitle(), saved.getContent(), saved.getAuthor());
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> findAll() {
        return articleRepository.findAll().stream()
                .map(a -> new ArticleResponseDto(a.getId(), a.getTitle(), a.getContent(), a.getAuthor()))
                .toList();
    }

    @Transactional(readOnly = true)
    public ArticleResponseDto findById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent(), article.getAuthor());
    }

    @Transactional
    public ArticleResponseDto update(Long id, ArticleRequestDto dto) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        article.update(dto.getTitle(), dto.getContent());
        return new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent(), article.getAuthor());
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
