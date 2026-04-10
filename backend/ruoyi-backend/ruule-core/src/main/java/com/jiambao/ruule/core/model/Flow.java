package com.jiambao.ruule.core.model;

import java.util.List;
import java.util.Map;

public class Flow {
    private Long id;
    private List<Map<String,Object>> nodes;
    private List<Map<String,Object>> edges;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public List<Map<String,Object>> getNodes() { return nodes; }
    public void setNodes(List<Map<String,Object>> nodes) { this.nodes = nodes; }
    public List<Map<String,Object>> getEdges() { return edges; }
    public void setEdges(List<Map<String,Object>> edges) { this.edges = edges; }
}
