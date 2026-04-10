package com.jiambao.ruule.core.controller;

import com.jiambao.ruule.core.model.Flow;
import com.jiambao.ruule.core.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flows")
public class FlowController {

    @Autowired
    private FlowService flowService;

    @GetMapping
    public List<Flow> list() { return flowService.list(); }

    @GetMapping("/{id}")
    public Flow get(@PathVariable("id") Long id) { return flowService.get(id); }

    @PostMapping
    public Flow save(@RequestBody Flow f) { return flowService.save(f); }
}
