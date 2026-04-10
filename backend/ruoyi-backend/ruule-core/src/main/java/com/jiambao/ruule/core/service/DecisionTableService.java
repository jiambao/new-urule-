package com.jiambao.ruule.core.service;

import com.jiambao.ruule.core.model.DecisionTable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DecisionTableService {
    private final Map<Long, DecisionTable> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    @PostConstruct
    public void init() {
        DecisionTable dt = new DecisionTable();
        dt.setId(idGen.getAndIncrement());
        dt.setColumns(Arrays.asList("条件A","结果"));
        Map<String,String> r = new HashMap<>();
        r.put("c0","x>0");
        r.put("c1","ALLOW");
        dt.setRows(Arrays.asList(r));
        store.put(dt.getId(), dt);
    }

    public DecisionTable save(DecisionTable dt) {
        if (dt.getId() == null) dt.setId(idGen.getAndIncrement());
        store.put(dt.getId(), dt);
        return dt;
    }

    public DecisionTable get(Long id) {
        return store.get(id);
    }

    public List<DecisionTable> list() {
        return new ArrayList<>(store.values());
    }
}
