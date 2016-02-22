package ru.jewelline.asana.jackson.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import ru.jewelline.asana4j.api.entities.User;
import ru.jewelline.asana4j.api.entities.Workspace;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface UserMixIn {

    @JsonSetter("id")
    void setId(long id);

    @JsonSetter("name")
    void setName(String name);

    @JsonSetter("email")
    void setEmail(String email);

    @JsonSetter("photo")
    void setPhotoUrl(Map<User.PhotoSize, String> photoUrl);

    @JsonSetter("workspaces")
    void setWorkspaces(List<Workspace> workspaces);
}
