package ru.jewelline.asana4j.core.impl.api.entity.common;

import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.ApiEntityInstanceProvider;
import ru.jewelline.asana4j.core.impl.api.RequestFactory;
import ru.jewelline.asana4j.core.impl.api.entity.AttachmentImpl;
import ru.jewelline.asana4j.core.impl.api.entity.ProjectImpl;
import ru.jewelline.asana4j.core.impl.api.entity.ProjectStatusImpl;
import ru.jewelline.asana4j.core.impl.api.entity.StoryImpl;
import ru.jewelline.asana4j.core.impl.api.entity.TagImpl;
import ru.jewelline.asana4j.core.impl.api.entity.TaskImpl;
import ru.jewelline.asana4j.core.impl.api.entity.TeamImpl;
import ru.jewelline.asana4j.core.impl.api.entity.UserImpl;
import ru.jewelline.asana4j.core.impl.api.entity.WorkspaceImpl;
import ru.jewelline.asana4j.http.HttpRequestBuilder;

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
        registerEntityClass(UserImpl.class);
        registerEntityClass(WorkspaceImpl.class);
        registerEntityClass(ProjectStatusImpl.class);
        registerEntityClass(ProjectImpl.class);
        registerEntityClass(TaskImpl.class);
        registerEntityClass(StoryImpl.class);
        registerEntityClass(AttachmentImpl.class);
        registerEntityClass(TeamImpl.class);
        registerEntityClass(TagImpl.class);
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
