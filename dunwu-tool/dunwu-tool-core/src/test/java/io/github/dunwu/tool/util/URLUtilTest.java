package io.github.dunwu.tool.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * URLUtil单元测试
 *
 * @author looly
 */
public class URLUtilTest {

    @Test
    public void normalizeTest() {
        String url = "http://www.hutool.cn//aaa/bbb";
        String normalize = URLUtil.normalize(url);
        Assertions.assertEquals("http://www.hutool.cn/aaa/bbb", normalize);

        url = "www.hutool.cn//aaa/bbb";
        normalize = URLUtil.normalize(url);
        Assertions.assertEquals("http://www.hutool.cn/aaa/bbb", normalize);
    }

    @Test
    public void normalizeTest2() {
        String url = "http://www.hutool.cn//aaa/\\bbb?a=1&b=2";
        String normalize = URLUtil.normalize(url);
        Assertions.assertEquals("http://www.hutool.cn/aaa/bbb?a=1&b=2", normalize);

        url = "www.hutool.cn//aaa/bbb?a=1&b=2";
        normalize = URLUtil.normalize(url);
        Assertions.assertEquals("http://www.hutool.cn/aaa/bbb?a=1&b=2", normalize);
    }

    @Test
    public void normalizeTest3() {
        String url = "http://www.hutool.cn//aaa/\\bbb?a=1&b=2";
        String normalize = URLUtil.normalize(url, true);
        Assertions.assertEquals("http://www.hutool.cn/aaa/bbb?a=1&b=2", normalize);

        url = "www.hutool.cn//aaa/bbb?a=1&b=2";
        normalize = URLUtil.normalize(url, true);
        Assertions.assertEquals("http://www.hutool.cn/aaa/bbb?a=1&b=2", normalize);

        url = "\\/www.hutool.cn//aaa/bbb?a=1&b=2";
        normalize = URLUtil.normalize(url, true);
        Assertions.assertEquals("http://www.hutool.cn/aaa/bbb?a=1&b=2", normalize);
    }

    @Test
    public void formatTest() {
        String url = "//www.hutool.cn//aaa/\\bbb?a=1&b=2";
        String normalize = URLUtil.normalize(url);
        Assertions.assertEquals("http://www.hutool.cn/aaa/bbb?a=1&b=2", normalize);
    }

    @Test
    public void getHostTest() throws MalformedURLException {
        String url = "https://www.hutool.cn//aaa/\\bbb?a=1&b=2";
        String normalize = URLUtil.normalize(url);
        URI host = URLUtil.getHost(new URL(normalize));
        Assertions.assertEquals("https://www.hutool.cn", host.toString());
    }

    @Test
    public void encodeTest() {
        String body = "366466 - 副本.jpg";
        String encode = URLUtil.encode(body);
        Assertions.assertEquals("366466%20-%20%E5%89%AF%E6%9C%AC.jpg", encode);
        Assertions.assertEquals(body, URLUtil.decode(encode));

        String encode2 = URLUtil.encodeQuery(body);
        Assertions.assertEquals("366466+-+%E5%89%AF%E6%9C%AC.jpg", encode2);
    }

}
