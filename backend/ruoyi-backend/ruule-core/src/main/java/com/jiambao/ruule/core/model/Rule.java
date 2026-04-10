package com.jiambao.ruule.core.model;

public class Rule {
    private Long id;
    private String name;
    private String description;
    private String xml;

    public Rule() {}

    public Rule(Long id, String name, String description, String xml) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.xml = xml;
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getXml() { return xml; }
    public void setXml(String xml) { this.xml = xml; }
}
