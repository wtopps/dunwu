package io.github.dunwu.tool.codec;

import io.github.dunwu.tool.util.CharsetUtil;
import io.github.dunwu.tool.util.StringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Base64单元测试
 *
 * @author looly
 */
public class Base64Test {

    @Test
    public void encodeAndDecodeTest() {
        String a = "伦家是一个非常长的字符串66";
        String encode = Base64.encode(a);
        Assertions.assertEquals("5Lym5a625piv5LiA5Liq6Z2e5bi46ZW/55qE5a2X56ym5LiyNjY=", encode);

        String decodeStr = Base64.decodeStr(encode);
        Assertions.assertEquals(a, decodeStr);
    }

    @Test
    public void encodeAndDecodeTest2() {
        String a = "a61a5db5a67c01445ca2-HZ20181120172058/pdf/中国电信影像云单体网关Docker版-V1.2.pdf";
        String encode = Base64.encode(a, CharsetUtil.UTF_8);
        Assertions.assertEquals(
            "YTYxYTVkYjVhNjdjMDE0NDVjYTItSFoyMDE4MTEyMDE3MjA1OC9wZGYv5Lit5Zu955S15L+h5b2x5YOP5LqR5Y2V5L2T572R5YWzRG9ja2Vy54mILVYxLjIucGRm",
            encode);

        String decodeStr = Base64.decodeStr(encode, CharsetUtil.UTF_8);
        Assertions.assertEquals(a, decodeStr);
    }

    @Test
    public void encodeAndDecodeTest3() {
        String a = ":";
        String encode = Base64.encode(a);
        Assertions.assertEquals("Og==", encode);

        String decodeStr = Base64.decodeStr(encode);
        Assertions.assertEquals(a, decodeStr);
    }

    @Test
    public void urlSafeEncodeAndDecodeTest() {
        String a = "广州伦家需要安全感55";
        String encode = StringUtil.utf8Str(Base64.encodeUrlSafe(StringUtil.utf8Bytes(a), false));
        Assertions.assertEquals("5bm_5bee5Lym5a626ZyA6KaB5a6J5YWo5oSfNTU", encode);

        String decodeStr = Base64.decodeStr(encode);
        Assertions.assertEquals(a, decodeStr);
    }

}
