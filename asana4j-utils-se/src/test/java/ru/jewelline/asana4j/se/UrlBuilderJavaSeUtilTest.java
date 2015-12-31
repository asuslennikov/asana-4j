package ru.jewelline.asana4j.se;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ru.jewelline.asana4j.utils.URLCreator;
import ru.jewelline.asana4j.utils.UrlBuilderTest;

@RunWith(MockitoJUnitRunner.class)
public class UrlBuilderJavaSeUtilTest extends UrlBuilderTest {

    @Override
    protected URLCreator.Builder getUrlBuilder() {
        return new UrlCreatorJavaSeUtil().builder();
    }
}
