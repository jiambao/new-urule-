package com.jiambao.ruule.core.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/rules")
public class ExecuteController {

    @PostMapping("/execute")
    public Map<String,Object> executeRule(@RequestBody Map<String,Object> body) {
        String xml = (String) body.getOrDefault("xml", "<rules/>");
        Map<String,Object> result = new HashMap<>();
        result.put("status", "ok");
        result.put("message", "PoC 执行成功");
        result.put("ruleLength", xml.length());
        result.put("timestamp", System.currentTimeMillis());
        return result;
    }
}
