package me.jungtaemin.simpleboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArticleStatsService {

    private final StringRedisTemplate redis;

    private String viewsKey(Long id) { return "articles:views:" + id; }
    private String rankKey() { return "articles:rank"; }

    public long incrementView(Long id) {
        Long v = redis.opsForValue().increment(viewsKey(id));
        redis.opsForZSet().incrementScore(rankKey(), id.toString(), 1.0);
        return v == null ? 0L : v;
    }

    public long getViews(Long id) {
        String v = redis.opsForValue().get(viewsKey(id));
        return v == null ? 0L : Long.parseLong(v);
    }

    public List<Long> topN(int n) {
        Set<String> ids = redis.opsForZSet().reverseRange(rankKey(), 0, n - 1);
        if (ids == null || ids.isEmpty()) return List.of();
        return ids.stream().map(Long::valueOf).toList();
    }
}
