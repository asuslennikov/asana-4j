package ru.jewelline.asana4j.core.impl.api.entity.common;

import ru.jewelline.asana4j.api.ApiException;
import ru.jewelline.asana4j.api.clients.modifiers.PrettyJsonResponseModifier;
import ru.jewelline.asana4j.api.entity.io.EntityDeserializer;
import ru.jewelline.asana4j.auth.AuthenticationService;
import ru.jewelline.asana4j.core.impl.api.ApiEntityInstanceProvider;
import ru.jewelline.asana4j.core.impl.api.clients.modifiers.AuthenticationRequestModifier;
import ru.jewelline.asana4j.core.impl.api.clients.modifiers.BaseApiUrlAppenderModifier;
import ru.jewelline.asana4j.core.impl.api.clients.modifiers.DataRootRequestModifier;
import ru.jewelline.asana4j.core.impl.api.clients.modifiers.JsonContentTypeModifier;
import ru.jewelline.asana4j.core.impl.api.clients.modifiers.LoggingRequestModifier;
import ru.jewelline.asana4j.core.impl.api.entity.AttachmentImpl;
import ru.jewelline.asana4j.core.impl.api.entity.ProjectImpl;
import ru.jewelline.asana4j.core.impl.api.entity.ProjectStatusImpl;
import ru.jewelline.asana4j.core.impl.api.entity.StoryImpl;
import ru.jewelline.asana4j.core.impl.api.entity.TagImpl;
import ru.jewelline.asana4j.core.impl.api.entity.TaskImpl;
import ru.jewelline.asana4j.core.impl.api.entity.TeamImpl;
import ru.jewelline.asana4j.core.impl.api.entity.UserImpl;
import ru.jewelline.asana4j.core.impl.api.entity.WorkspaceImpl;
import ru.jewelline.request.http.HttpRequestBuilder;
import ru.jewelline.request.http.HttpRequestFactory;
import ru.jewelline.request.http.modifiers.RequestModifier;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ApiEntityContext {

    private final HttpRequestFactory httpRequestFactory;
    private final RequestModifier[] mandatoryRequestModifiers;
    private final Map<Class<? extends ApiEntityImpl<?>>, ApiEntityInstanceProvider<? extends ApiEntityImpl<?>>> instanceProviders;

    public ApiEntityContext(HttpRequestFactory httpRequestFactory, AuthenticationService authenticationService) {
        this.httpRequestFactory = httpRequestFactory;
        this.mandatoryRequestModifiers = new RequestModifier[]{
                new AuthenticationRequestModifier(authenticationService),
                new BaseApiUrlAppenderModifier(),
                new DataRootRequestModifier(),
                new JsonContentTypeModifier()/*,
                new LoggingRequestModifier(),
                new PrettyJsonResponseModifier()*/
        };
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

    private RequestModifier[] getRequestModifiers(RequestModifier[] requestModifiers) {
        if (requestModifiers == null || requestModifiers.length == 0) {
            return mandatoryRequestModifiers;
        }
        RequestModifier[] modifiers = Arrays.copyOf(requestModifiers, requestModifiers.length + mandatoryRequestModifiers.length);
        System.arraycopy(mandatoryRequestModifiers, 0, modifiers, requestModifiers.length, mandatoryRequestModifiers.length);
        return modifiers;
    }

    public HttpRequestBuilder newRequest(RequestModifier... requestModifiers) {
        return this.httpRequestFactory.newRequest(getRequestModifiers(requestModifiers));
    }
}
