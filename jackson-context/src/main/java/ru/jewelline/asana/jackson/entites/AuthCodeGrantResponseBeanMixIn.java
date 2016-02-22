package ru.jewelline.asana.jackson.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AuthCodeGrantResponseBeanMixIn {

    @JsonProperty("access_token")
    public void setAccessToken(String accessToken) {
    }

    @JsonProperty("refresh_token")
    public void setRefreshToken(String refreshToken) {
    }

    @JsonProperty("token_type")
    public void setTokenType(String tokenType) {
    }

    @JsonProperty("expires_in")
    public void setExpiresIn(int expiresIn) {
    }

    @JsonProperty("state")
    public void setState(String state) {
    }
}
