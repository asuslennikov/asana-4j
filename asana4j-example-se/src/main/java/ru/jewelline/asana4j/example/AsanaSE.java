package ru.jewelline.asana4j.example;

import ru.jewelline.asana4j.core.impl.AsanaImpl;
import ru.jewelline.asana4j.se.Base64JavaSeUtil;

public class AsanaSE extends AsanaImpl {

    public AsanaSE() {
        super(new InMemoryPreferenceService(), new Base64JavaSeUtil());
    }
}
