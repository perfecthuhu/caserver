package com.card.alumni.entity;

public class CaConfig {
    private Integer id;

    private String configKey;

    private String configValue;

    private String configInfo;

    public CaConfig(Integer id, String configKey, String configValue, String configInfo) {
        this.id = id;
        this.configKey = configKey;
        this.configValue = configValue;
        this.configInfo = configInfo;
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

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey == null ? null : configKey.trim();
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue == null ? null : configValue.trim();
    }

    public String getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(String configInfo) {
        this.configInfo = configInfo == null ? null : configInfo.trim();
    }
}