package com.jiambao.ruule.core.controller;

import com.jiambao.ruule.core.model.Rule;
import com.jiambao.ruule.core.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @GetMapping("/resources")
    public List<Map<String,Object>> getResources() {
        Map<String, Object> node11 = new HashMap<>();
        node11.put("id", 11);
        node11.put("name", "规则库");
        node11.put("children", Collections.emptyList());
        Map<String, Object> node12 = new HashMap<>();
        node12.put("id", 12);
        node12.put("name", "决策流");
        node12.put("children", Collections.emptyList());
        return Arrays.asList(
                new HashMap<String,Object>() {{ put("id", 1); put("name","项目A"); put("children", Arrays.asList(node11, node12)); }}
        );
    }

    @GetMapping("/rules")
    public Map<String,Object> listRules(@RequestParam(value="q", required=false) String q,
                                       @RequestParam(value="page", defaultValue="1") int page,
                                       @RequestParam(value="size", defaultValue="10") int size) {
        List<Rule> items = ruleService.list(q, page, size);
        int total = ruleService.total(q);
        Map<String,Object> result = new HashMap<>();
        result.put("items", items);
        result.put("total", total);
        return result;
    }

    @GetMapping("/rules/{id}")
    public Rule getRule(@PathVariable("id") Long id) {
        return ruleService.get(id);
    }

    @PostMapping("/rules")
    public Rule createRule(@RequestBody Rule r) {
        return ruleService.create(r);
    }

    @PutMapping("/rules/{id}")
    public Rule updateRule(@PathVariable("id") Long id, @RequestBody Rule r) {
        return ruleService.update(id, r);
    }

    @DeleteMapping("/rules/{id}")
    public void deleteRule(@PathVariable("id") Long id) {
        ruleService.delete(id);
    }
}
