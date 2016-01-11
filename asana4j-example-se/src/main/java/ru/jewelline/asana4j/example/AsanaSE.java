package ru.jewelline.asana4j.example;

import ru.jewelline.asana4j.core.impl.Asana;
import ru.jewelline.asana4j.se.Base64JavaSeUtil;
import ru.jewelline.asana4j.se.UrlCreatorJavaSeUtil;

public class AsanaSE extends Asana{

    public AsanaSE() {
        super(new InMemoryPreferenceService(), new UrlCreatorJavaSeUtil(), new Base64JavaSeUtil());
    }
}
