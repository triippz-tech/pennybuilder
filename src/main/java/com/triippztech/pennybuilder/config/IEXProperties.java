package com.triippztech.pennybuilder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "iex", ignoreUnknownFields = false)
public class IEXProperties {
    private String pubicToken;
    private String secretToken;

    public String getPubicToken() {
        return pubicToken;
    }

    public void setPubicToken(String pubicToken) {
        this.pubicToken = pubicToken;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }
}
