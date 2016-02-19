package ru.jewelline.asana.jackson.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AuthCodeGrantErrorResponseMixIn {
    public AuthCodeGrantErrorResponseMixIn(@JsonProperty("error_description") String message) {
    }
}
