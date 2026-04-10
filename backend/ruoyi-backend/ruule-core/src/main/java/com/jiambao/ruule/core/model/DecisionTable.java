package com.jiambao.ruule.core.model;

import java.util.List;
import java.util.Map;

public class DecisionTable {
    private Long id;
    private List<String> columns;
    private List<Map<String,String>> rows;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public List<String> getColumns() { return columns; }
    public void setColumns(List<String> columns) { this.columns = columns; }
    public List<Map<String,String>> getRows() { return rows; }
    public void setRows(List<Map<String,String>> rows) { this.rows = rows; }
}
