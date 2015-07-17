package ru.jewelline.asana4j.se;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ru.jewelline.asana4j.utils.Base64;
import ru.jewelline.asana4j.utils.Base64Test;

@RunWith(MockitoJUnitRunner.class)
@Ignore(value = "Incorrect test initialization")
public class Base64AndroidUtilTest extends Base64Test{

    @Override
    protected Base64 getBase64Util() {
        return new Base64AndroidUtil();
    }
}
