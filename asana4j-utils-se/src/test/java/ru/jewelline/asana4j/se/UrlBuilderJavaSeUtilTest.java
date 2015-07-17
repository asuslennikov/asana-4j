package ru.jewelline.asana4j.se;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ru.jewelline.asana4j.utils.URLBuilder;
import ru.jewelline.asana4j.utils.UrlBuilderTest;

@RunWith(MockitoJUnitRunner.class)
public class UrlBuilderJavaSeUtilTest extends UrlBuilderTest {

    @Override
    protected URLBuilder getUrlBuilder() {
        return new UrlBuilderJavaSeUtil();
    }
}
