package me.jungtaemin.simpleboard.repository;

import me.jungtaemin.simpleboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
