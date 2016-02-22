package ru.jewelline.asana4j.impl.clients;

import ru.jewelline.asana4j.api.beans.StoryBean;
import ru.jewelline.asana4j.api.clients.StoriesClient;
import ru.jewelline.asana4j.api.entities.Story;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.impl.entity.common.ApiEntityContext;
import ru.jewelline.asana4j.impl.entity.io.SimpleFieldsUpdater;
import ru.jewelline.request.api.PagedList;
import ru.jewelline.request.api.entity.EntityDeserializer;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpMethod;

public class StoriesClientImpl extends ApiClientImpl implements StoriesClient {

    public StoriesClientImpl(RequestFactory requestFactory, ApiEntityContext entityContext) {
        super(requestFactory, entityContext);
    }

    private EntityDeserializer<StoryBean> getStoryDeserializer() {
        return getEntityContext().getDeserializer(StoryBean.class);
    }

    @Override
    public Story getStoryById(long storyId, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("stories/" + String.valueOf(storyId))
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiObject(getStoryDeserializer());
    }

    @Override
    public PagedList<Story> getTaskStories(long taskId, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("tasks/" + taskId + "/stories")
                .buildAs(HttpMethod.GET)
                .execute()
                .asApiCollection(getStoryDeserializer());
    }

    @Override
    public Story createTaskComment(long taskId, String text, RequestModifier... requestModifiers) {
        return apiRequest(requestModifiers)
                .path("tasks/" + taskId + "/stories")
                .setEntity(new SimpleFieldsUpdater()
                        .setField("text", text)
                        .wrapFieldsAsEntity())
                .buildAs(HttpMethod.POST)
                .execute()
                .asApiObject(getStoryDeserializer());
    }
}
