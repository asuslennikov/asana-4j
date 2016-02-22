package ru.jewelline.asana.json.im.entity.common;

import ru.jewelline.asana4j.api.beans.AttachmentBean;
import ru.jewelline.asana4j.api.beans.ProjectBean;
import ru.jewelline.asana4j.api.beans.ProjectStatusBean;
import ru.jewelline.asana4j.api.beans.StoryBean;
import ru.jewelline.asana4j.api.beans.TagBean;
import ru.jewelline.asana4j.api.beans.TaskBean;
import ru.jewelline.asana4j.api.beans.TeamBean;
import ru.jewelline.asana4j.api.beans.UserBean;
import ru.jewelline.asana4j.api.beans.WorkspaceBean;
import ru.jewelline.asana4j.core.impl.api.ApiEntityInstanceProvider;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.request.api.ApiException;
import ru.jewelline.request.api.ApiRequestBuilder;
import ru.jewelline.request.api.entity.EntityDeserializer;
import ru.jewelline.request.api.modifiers.RequestModifier;
import ru.jewelline.request.http.HttpRequestBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ApiEntityContext implements RequestFactory {

    private final RequestFactory requestFactory;
    private final Map<Class<? extends ApiEntityImpl<?>>, ApiEntityInstanceProvider<? extends ApiEntityImpl<?>>> instanceProviders;

    public ApiEntityContext(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
        this.instanceProviders = new HashMap<>();
        // TODO Don't pass 'this' out of a constructor (through anonymous inner class)
        registerApiEntities();
    }

    private void registerApiEntities() {
        registerEntityClass(UserBean.class);
        registerEntityClass(WorkspaceBean.class);
        registerEntityClass(ProjectStatusBean.class);
        registerEntityClass(ProjectBean.class);
        registerEntityClass(TaskBean.class);
        registerEntityClass(StoryBean.class);
        registerEntityClass(AttachmentBean.class);
        registerEntityClass(TeamBean.class);
        registerEntityClass(TagBean.class);
    }

    private <T extends ApiEntityImpl<T>> void registerEntityClass(final Class<T> entityClass) {
        if (entityClass == null) {
            throw new IllegalArgumentException("Entity class can not be null.");
        }
        this.instanceProviders.put(entityClass, new ApiEntityInstanceProvider<T>() {
            @Override
            public T getInstance() {
                try {
                    Constructor<T> constructor = entityClass.getDeclaredConstructor(ApiEntityContext.class);
                    return constructor.newInstance(ApiEntityContext.this);
                } catch (NoSuchMethodException e) {
                } catch (InstantiationException e) {
                } catch (InvocationTargetException e) {
                } catch (IllegalAccessException e) {
                }
                throw new ApiException(ApiException.API_ENTITY_INSTANTIATION_FAIL,
                        "You are trying to instantiate entity which is not supported: " + entityClass.getName() + ".");
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends ApiEntityImpl<T>> ApiEntityInstanceProvider<T> getEntityProvider(Class<T> entityClass) {
        ApiEntityInstanceProvider<T> instanceProvider = (ApiEntityInstanceProvider<T>) this.instanceProviders.get(entityClass);
        if (instanceProvider == null) {
            throw new ApiException(ApiException.API_ENTITY_INSTANTIATION_FAIL,
                    "You are trying to instantiate entity for class which was not registered in the context: "
                            + (entityClass != null ? entityClass.getName() : null) + ".");
        }
        return instanceProvider;
    }

    public <T extends ApiEntityImpl<T>> EntityDeserializer<T> getDeserializer(Class<T> entityClass) {
        return new ApiEntityDeserializer<>(getEntityProvider(entityClass));
    }

    public <T extends ApiEntityImpl<T>> T getEntity(Class<T> entityClass) {
        return getEntityProvider(entityClass).getInstance();
    }

    @Override
    public HttpRequestBuilder httpRequest() {
        return this.requestFactory.httpRequest();
    }

    @Override
    public ApiRequestBuilder apiRequest(RequestModifier... requestModifiers) {
        return this.requestFactory.apiRequest(requestModifiers);
    }
}