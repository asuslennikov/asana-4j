package ru.jewelline.asana4j.core.impl.api.clients.modifiers;

import ru.jewelline.asana4j.api.ApiRequestBuilder;
import ru.jewelline.asana4j.api.clients.modifiers.ModifiersChain;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;
import ru.jewelline.asana4j.api.entity.io.JsonEntity;
import ru.jewelline.asana4j.api.entity.io.SerializableEntity;
import ru.jewelline.asana4j.http.HttpMethod;

import java.util.Map;

public class LoggingRequestModifier implements RequestModifier {
    @Override
    public int priority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void modify(ApiRequestBuilder requestBuilder, HttpMethod httpMethod, ModifiersChain modifiersChain) {
        StringBuilder sb = new StringBuilder("=======================\nRequest url: ").append(requestBuilder.getPath());
        sb.append("\n\tMethod: ").append(httpMethod.name());
        sb.append("\n\tHeaders:");
        for (Map.Entry header : requestBuilder.getHeaders().entrySet()) {
            sb.append("\n\t\t").append(header.getKey()).append(": ").append(header.getValue());
        }
        sb.append("\n\tQuery parameters:");
        for (Map.Entry param : requestBuilder.getQueryParameters().entrySet()) {
            sb.append("\n\t\t").append(param.getKey()).append(": ").append(param.getValue());
        }
        SerializableEntity entity = requestBuilder.getEntity();
        if (entity != null && entity instanceof JsonEntity) {
            sb.append("\n\tPayload:\n\t\t").append(((JsonEntity) entity).asJson());
        }
        System.out.println(sb.toString());
        modifiersChain.next(requestBuilder, httpMethod);
    }
}
