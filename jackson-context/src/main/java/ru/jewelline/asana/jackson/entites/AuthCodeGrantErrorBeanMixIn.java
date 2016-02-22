package ru.jewelline.asana.jackson.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AuthCodeGrantErrorBeanMixIn {
    @JsonProperty("error_description")
    public void setMessage(String message) {
    }
}
