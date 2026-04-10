package com.jiambao.ruule.core.controller;

import com.jiambao.ruule.core.model.DecisionTable;
import com.jiambao.ruule.core.service.DecisionTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class DecisionTableController {

    @Autowired
    private DecisionTableService service;

    @GetMapping("/decision-tables")
    public List<DecisionTable> list() {
        return service.list();
    }

    @GetMapping("/decision-tables/{id}")
    public DecisionTable get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @PostMapping("/decision-tables")
    public DecisionTable save(@RequestBody DecisionTable dt) {
        return service.save(dt);
    }

    @GetMapping("/decision-tables/{id}/export")
    public String exportCsv(@PathVariable("id") Long id) {
        DecisionTable dt = service.get(id);
        if (dt == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(",", dt.getColumns())).append("\n");
        for (Map<String,String> r : dt.getRows()) {
            List<String> cells = new ArrayList<>();
            for (int i = 0; i < dt.getColumns().size(); i++) {
                String v = r.getOrDefault("c"+i, "");
                if (v.contains(",")) v = "\"" + v.replace("\"","\"\"") + "\"";
                cells.add(v);
            }
            sb.append(String.join(",", cells)).append("\n");
        }
        return sb.toString();
    }
}
