package me.jungtaemin.simpleboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.jungtaemin.simpleboard.dto.ArticleRequestDto;
import me.jungtaemin.simpleboard.dto.ArticleResponseDto;
import me.jungtaemin.simpleboard.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleResponseDto> create(@Valid @RequestBody ArticleRequestDto dto) {
        return ResponseEntity.ok(articleService.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<ArticleResponseDto>> findAll(
            @PageableDefault(size=10, sort="createdAt", direction= Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(articleService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> update(@PathVariable Long id, @Valid @RequestBody ArticleRequestDto dto) {
        return ResponseEntity.ok(articleService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/top")
    public ResponseEntity<List<ArticleResponseDto>> top(@RequestParam(defaultValue = "10") int n) {
        return ResponseEntity.ok(articleService.topN(n));
    }

    @GetMapping("/{id}/views")
    public ResponseEntity<Map<String, Long>> views(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("id", id, "views", articleService.getViews(id)));
    }
}
