package com.jiambao.ruule.core.service;

import com.jiambao.ruule.core.model.Flow;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FlowService {
    private final Map<Long, Flow> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    @PostConstruct
    public void init() {
        Flow f = new Flow();
        f.setId(idGen.getAndIncrement());
        f.setNodes(Collections.emptyList());
        f.setEdges(Collections.emptyList());
        store.put(f.getId(), f);
    }

    public Flow save(Flow f) {
        if (f.getId() == null) f.setId(idGen.getAndIncrement());
        store.put(f.getId(), f);
        return f;
    }

    public Flow get(Long id) { return store.get(id); }

    public List<Flow> list() { return new ArrayList<>(store.values()); }
}
