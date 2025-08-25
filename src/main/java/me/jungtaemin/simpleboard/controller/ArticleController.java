package me.jungtaemin.simpleboard.controller;

import lombok.RequiredArgsConstructor;
import me.jungtaemin.simpleboard.dto.ArticleRequestDto;
import me.jungtaemin.simpleboard.dto.ArticleResponseDto;
import me.jungtaemin.simpleboard.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleResponseDto> create(@RequestBody ArticleRequestDto dto) {
        return ResponseEntity.ok(articleService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponseDto>> findAll() {
        return ResponseEntity.ok(articleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> update(@PathVariable Long id, @RequestBody ArticleRequestDto dto) {
        return ResponseEntity.ok(articleService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.ok().build();
    }
}
