package com.jiambao.ruule.core.service;

import com.jiambao.ruule.core.model.Rule;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RuleService {
    private final Map<Long, Rule> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    @PostConstruct
    public void init() {
        Rule r = new Rule(idGen.getAndIncrement(), "示例规则", "这是一个示例规则", "<rules/>");
        store.put(r.getId(), r);
    }

    public List<Rule> list(String q, int page, int size) {
        List<Rule> all = new ArrayList<>(store.values());
        if (q != null && !q.isEmpty()) {
            all.removeIf(r -> !r.getName().toLowerCase().contains(q.toLowerCase()));
        }
        int from = Math.max(0, (page - 1) * size);
        int to = Math.min(all.size(), from + size);
        if (from >= all.size()) {
            return Collections.emptyList();
        }
        return all.subList(from, to);
    }

    public int total(String q) {
        if (q == null || q.isEmpty()) return store.size();
        return (int) store.values().stream().filter(r -> r.getName().toLowerCase().contains(q.toLowerCase())).count();
    }

    public Rule get(Long id) {
        return store.get(id);
    }

    public Rule create(Rule rule) {
        long id = idGen.getAndIncrement();
        rule.setId(id);
        store.put(id, rule);
        return rule;
    }

    public Rule update(Long id, Rule rule) {
        rule.setId(id);
        store.put(id, rule);
        return rule;
    }

    public void delete(Long id) {
        store.remove(id);
    }
}
