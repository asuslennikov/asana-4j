package ru.jewelline.asana.jackson.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface WorkspaceMixIn {

    @JsonSetter("id")
    long getId();

    @JsonSetter("id")
    String getName();

    @JsonSetter("is_organization")
    boolean isOrganisation();
}
