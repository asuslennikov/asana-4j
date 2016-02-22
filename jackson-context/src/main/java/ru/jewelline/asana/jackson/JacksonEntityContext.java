package ru.jewelline.asana.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.jewelline.asana.auth.AuthCodeGrantErrorBean;
import ru.jewelline.asana.auth.AuthCodeGrantResponseBean;
import ru.jewelline.asana.core.EntityContext;
import ru.jewelline.asana.core.EntityResponseReader;
import ru.jewelline.asana.jackson.entites.AuthCodeGrantErrorBeanMixIn;
import ru.jewelline.asana.jackson.entites.AuthCodeGrantResponseBeanMixIn;
import ru.jewelline.asana.jackson.entites.UserMixIn;
import ru.jewelline.asana.jackson.entites.WorkspaceMixIn;
import ru.jewelline.asana.jackson.readers.ClassReader;
import ru.jewelline.asana.jackson.readers.DataRootClassReader;
import ru.jewelline.asana.jackson.readers.Reader;
import ru.jewelline.asana4j.api.beans.UserBean;
import ru.jewelline.asana4j.api.beans.WorkspaceBean;
import ru.jewelline.asana4j.api.entities.User;
import ru.jewelline.asana4j.api.entities.Workspace;

import java.util.HashSet;
import java.util.Set;

public class JacksonEntityContext implements EntityContext {

    private static class JacksonBindModule extends SimpleModule {
        public JacksonBindModule() {
            super("JacksonBindModule", new Version(0, 0, 1, null, "ru.jewelline.asana", "jackson"));
        }

        @Override
        public void setupModule(SetupContext context) {
            super.setupModule(context);
            SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
            resolver.addMapping(User.class, UserBean.class);
            resolver.addMapping(Workspace.class, WorkspaceBean.class);
            context.addAbstractTypeResolver(resolver);

            context.setMixInAnnotations(AuthCodeGrantErrorBean.class, AuthCodeGrantErrorBeanMixIn.class);
            context.setMixInAnnotations(AuthCodeGrantResponseBean.class, AuthCodeGrantResponseBeanMixIn.class);
            context.setMixInAnnotations(UserBean.class, UserMixIn.class);
            context.setMixInAnnotations(WorkspaceBean.class, WorkspaceMixIn.class);
        }
    }

    private ObjectMapper objectMapper;
    private Set<Class<?>> dataRootExclusions;

    public JacksonEntityContext() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JacksonBindModule());
        this.dataRootExclusions = new HashSet<>();
        this.dataRootExclusions.add(AuthCodeGrantErrorBean.class);
        this.dataRootExclusions.add(AuthCodeGrantResponseBean.class);
    }

    @Override
    public <T, E> EntityResponseReader<T, E> getReader(Class<T> entityClass, Class<E> errorClass) {
        Reader<T> responseReader = getReader(entityClass);
        Reader<E> errorReader = getReader(errorClass);
        return new JacksonEntityWithErrorReader<>(objectMapper, responseReader, errorReader);
    }

    private <T> Reader<T> getReader(Class<T> clazz) {
        // TODO cache
        if (this.dataRootExclusions.contains(clazz)) {
            return new ClassReader<>(clazz);
        } else {
            return new DataRootClassReader<>(clazz);
        }
    }
}
