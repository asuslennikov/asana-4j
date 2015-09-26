package ru.jewelline.asana4j.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class Base64Test {
    private static final String STRING = "STRING!@#$%^&*()_+1234567890-==\\~ /";
    private static final String BASE64 = "U1RSSU5HIUAjJCVeJiooKV8rMTIzNDU2Nzg5MC09PVx+IC8=";

    @Test
    public void test_encodeNull(){
        assertThat(getBase64Util().encode(null)).isNull();
    }

    protected abstract Base64 getBase64Util();

    @Test
    public void test_encodeString(){
        assertThat(getBase64Util().encode(STRING)).isEqualTo(BASE64);
    }

    @Test
    public void test_decodeNull(){
        assertThat(getBase64Util().decode(null)).isEqualTo(null);
    }

    @Test
    public void test_decodeString(){
        assertThat(getBase64Util().decode(BASE64)).isEqualTo(STRING);
    }

    @Test
    public void test_decodeEncode(){
        Base64 b64 = getBase64Util();
        String str = b64.decode(BASE64);
        assertThat(b64.encode(str)).isEqualTo(BASE64);
    }

    @Test
    public void test_encodeDecode(){
        Base64 b64 = getBase64Util();
        String str = b64.encode(STRING);
        assertThat(b64.decode(str)).isEqualTo(STRING);
    }

    @Test
    public void test_doubleEncodeDecode(){
        Base64 b64 = getBase64Util();
        String str = b64.encode(STRING);
        str = b64.encode(str);
        str = b64.decode(str);
        assertThat(b64.decode(str)).isEqualTo(STRING);
    }
}
