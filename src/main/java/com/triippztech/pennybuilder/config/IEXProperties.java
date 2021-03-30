package com.triippztech.pennybuilder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "iex", ignoreUnknownFields = false)
public class IEXProperties {

    private Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public static class Token {
        private String pub;
        private String secret;

        public String getPub() {
            return pub;
        }

        public void setPub(String pub) {
            this.pub = pub;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }
}
