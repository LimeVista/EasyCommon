package me.limeice.common.function;

import org.junit.Test;

import static org.junit.Assert.*;

public class TextEncodeUtilsTest {

    @Test
    public void string2Html() {
        String str = "<a href=\"http://alienryderflex.com/polyspline/\">实现（C/C++）</a>";
        CharSequence html = TextEncodeUtils.string2Html(str);
        assertNotEquals(html.length(), str.length());
    }
}