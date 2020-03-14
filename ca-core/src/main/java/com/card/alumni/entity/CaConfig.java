package com.card.alumni.entity;

public class CaConfig {
    private Integer id;

    private String key;

    private String value;

    private String info;

    public CaConfig(Integer id, String key, String value, String info) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.info = info;
    }

    public CaConfig() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }
}