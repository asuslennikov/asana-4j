package ru.jewelline.asana.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.jewelline.asana.auth.AuthCodeGrantErrorResponse;
import ru.jewelline.asana.core.EntityContext;
import ru.jewelline.asana.core.EntityResponseReader;
import ru.jewelline.asana.core.EntityWithErrorResponseReader;
import ru.jewelline.asana.jackson.entites.AuthCodeGrantErrorResponseMixIn;

public abstract class JacksonEntityContext implements EntityContext {

    private static class JacksonBindModule extends SimpleModule {
        public JacksonBindModule() {
            super("JacksonBindModule", new Version(0, 0, 1, null, "ru.jewelline.asana", "jackson"));
        }

        @Override
        public void setupModule(SetupContext context) {
            super.setupModule(context);
            context.setMixInAnnotations(AuthCodeGrantErrorResponse.class, AuthCodeGrantErrorResponseMixIn.class);
        }
    }

    private ObjectMapper objectMapper;

    public JacksonEntityContext() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JacksonBindModule());
    }

    @Override
    public <T> EntityResponseReader<T> getReader(Class<T> entityClass) {
        return null;
    }

    @Override
    public <T, E> EntityWithErrorResponseReader<T, E> getReader(Class<T> entityClass, Class<E> errorClass) {
        return new JacksonEntityWithErrorReader<>(objectMapper, entityClass, errorClass);
    }
}
