package ru.jewelline.asana4j.core.impl.api.entity;

import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.ApiEntityInstanceProvider;
import ru.jewelline.asana4j.core.impl.api.ApiRequestBuilderProvider;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityDeserializer;
import ru.jewelline.asana4j.core.impl.api.entity.common.ApiEntityImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ApiEntityContext implements ApiRequestBuilderProvider {

    private final ApiRequestBuilderProvider requestBuilderProvider;
    private final Map<Class<? extends ApiEntityImpl<?>>, ApiEntityInstanceProvider<? extends ApiEntityImpl<?>>> instanceProviders;

    public ApiEntityContext(ApiRequestBuilderProvider requestBuilderProvider) {
        this.requestBuilderProvider = requestBuilderProvider;
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
    public final <T extends ApiEntityImpl<T>> ApiEntityInstanceProvider<T> getEntityProvider(Class<T> entityClass) {
        ApiEntityInstanceProvider<T> instanceProvider = (ApiEntityInstanceProvider<T>) this.instanceProviders.get(entityClass);
        if (instanceProvider == null) {
            throw new ApiException(ApiException.API_ENTITY_INSTANTIATION_FAIL,
                    "You are trying to instantiate entity for class which was not registered in the context: "
                            + (entityClass != null ? entityClass.getName() : null) + ".");
        }
        return instanceProvider;
    }

    public final <T extends ApiEntityImpl<T>> EntityDeserializer<T> getDeserializer(Class<T> entityClass) {
        return new ApiEntityDeserializer<>(getEntityProvider(entityClass));
    }

    public final <T extends ApiEntityImpl<T>> T getEntity(Class<T> entityClass) {
        return getEntityProvider(entityClass).getInstance();
    }

    @Override
    public ApiRequestBuilder newRequest(RequestModifier... requestModifiers) {
        return this.requestBuilderProvider.newRequest(requestModifiers);
    }
}
