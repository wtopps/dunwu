package io.github.dunwu.tool.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * HexUtil单元测试
 *
 * @author Looly
 */
public class HexUtilTest {

    @Test
    public void hexStrTest() {
        String str = "我是一个字符串";

        String hex = HexUtil.encodeHexStr(str, CharsetUtil.CHARSET_UTF_8);
        String decodedStr = HexUtil.decodeHexStr(hex);

        Assertions.assertEquals(str, decodedStr);
    }

    @Test
    public void toUnicodeHexTest() {
        String unicodeHex = HexUtil.toUnicodeHex('\u2001');
        Assertions.assertEquals("\\u2001", unicodeHex);

        unicodeHex = HexUtil.toUnicodeHex('你');
        Assertions.assertEquals("\\u4f60", unicodeHex);
    }

    @Test
    public void isHexNumberTest() {
        String a = "0x3544534F444";
        boolean isHex = HexUtil.isHexNumber(a);
        Assertions.assertTrue(isHex);
    }

}
