package io.github.dunwu.tool.io;

import io.github.dunwu.tool.util.CharsetUtil;
import io.github.dunwu.tool.util.StringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * BufferUtil单元测试
 *
 * @author looly
 */
public class BufferUtilTest {

    @Test
    public void copyTest() {
        byte[] bytes = "AAABBB".getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        ByteBuffer buffer2 = BufferUtil.copy(buffer, ByteBuffer.allocate(5));
        Assertions.assertEquals("AAABB", StringUtil.utf8Str(buffer2));
    }

    @Test
    public void readBytesTest() {
        byte[] bytes = "AAABBB".getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        byte[] bs = BufferUtil.readBytes(buffer, 5);
        Assertions.assertEquals("AAABB", StringUtil.utf8Str(bs));
    }

    @Test
    public void readBytes2Test() {
        byte[] bytes = "AAABBB".getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        byte[] bs = BufferUtil.readBytes(buffer, 5);
        Assertions.assertEquals("AAABB", StringUtil.utf8Str(bs));
    }

    @Test
    public void readLineTest() {
        String text = "aa\r\nbbb\ncc";
        ByteBuffer buffer = ByteBuffer.wrap(text.getBytes());

        // 第一行
        String line = BufferUtil.readLine(buffer, CharsetUtil.CHARSET_UTF_8);
        Assertions.assertEquals("aa", line);

        // 第二行
        line = BufferUtil.readLine(buffer, CharsetUtil.CHARSET_UTF_8);
        Assertions.assertEquals("bbb", line);

        // 第三行因为没有行结束标志，因此返回null
        line = BufferUtil.readLine(buffer, CharsetUtil.CHARSET_UTF_8);
        Assertions.assertNull(line);

        // 读取剩余部分
        Assertions.assertEquals("cc", StringUtil.utf8Str(BufferUtil.readBytes(buffer)));
    }

}
