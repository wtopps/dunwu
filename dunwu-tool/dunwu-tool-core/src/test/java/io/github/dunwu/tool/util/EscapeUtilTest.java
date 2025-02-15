package io.github.dunwu.tool.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EscapeUtilTest {

    @Test
    public void escapeHtml4Test() {
        String escapeHtml4 = EscapeUtil.escapeHtml4("<a>你好</a>");
        Assertions.assertEquals("&lt;a&gt;你好&lt;/a&gt;", escapeHtml4);

        String result = EscapeUtil.unescapeHtml4("&#25391;&#33633;&#22120;&#31867;&#22411;");
        Assertions.assertEquals("振荡器类型", result);
    }

}
