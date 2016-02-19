package ru.jewelline.asana.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.jewelline.asana.auth.AuthCodeGrantErrorResponse;
import ru.jewelline.asana.common.EntityContext;
import ru.jewelline.asana.common.EntityResponseReceiver;
import ru.jewelline.asana.common.EntityWithErrorResponseReceiver;
import ru.jewelline.asana.jackson.entites.AuthCodeGrantErrorResponseMixIn;

public class JacksonEntityContext implements EntityContext {

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
    public <T> EntityResponseReceiver<T> getReceiver(Class<T> entityClass) {
        return null;
    }

    @Override
    public <T, E> EntityWithErrorResponseReceiver<T, E> getReceiver(Class<T> entityClass, Class<E> errorClass) {
        return new JacksonEntityWithErrorReceiver<>(objectMapper, entityClass, errorClass);
    }
}
