package io.github.dunwu.tool.convert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumberChineseFormaterTest {

    @Test
    public void formatTest() {
        String f1 = NumberChineseFormater.format(10889.72356, false);
        Assertions.assertEquals("一万零八百八十九点七二", f1);
        f1 = NumberChineseFormater.format(12653, false);
        Assertions.assertEquals("一万二千六百五十三", f1);
        f1 = NumberChineseFormater.format(215.6387, false);
        Assertions.assertEquals("二百一十五点六四", f1);
        f1 = NumberChineseFormater.format(1024, false);
        Assertions.assertEquals("一千零二十四", f1);
        f1 = NumberChineseFormater.format(100350089, false);
        Assertions.assertEquals("一亿三十五万零八十九", f1);
        f1 = NumberChineseFormater.format(1200, false);
        Assertions.assertEquals("一千二百", f1);
        f1 = NumberChineseFormater.format(12, false);
        Assertions.assertEquals("一十二", f1);
        f1 = NumberChineseFormater.format(0.05, false);
        Assertions.assertEquals("零点零五", f1);
    }

    @Test
    public void formatTest2() {
        String f1 = NumberChineseFormater.format(-0.3, false, false);
        Assertions.assertEquals("负零点三", f1);
    }

    @Test
    public void formatTranditionalTest() {
        String f1 = NumberChineseFormater.format(10889.72356, true);
        Assertions.assertEquals("壹万零捌佰捌拾玖点柒贰", f1);
        f1 = NumberChineseFormater.format(12653, true);
        Assertions.assertEquals("壹万贰仟陆佰伍拾叁", f1);
        f1 = NumberChineseFormater.format(215.6387, true);
        Assertions.assertEquals("贰佰壹拾伍点陆肆", f1);
        f1 = NumberChineseFormater.format(1024, true);
        Assertions.assertEquals("壹仟零贰拾肆", f1);
        f1 = NumberChineseFormater.format(100350089, true);
        Assertions.assertEquals("壹亿叁拾伍万零捌拾玖", f1);
        f1 = NumberChineseFormater.format(1200, true);
        Assertions.assertEquals("壹仟贰佰", f1);
        f1 = NumberChineseFormater.format(12, true);
        Assertions.assertEquals("壹拾贰", f1);
        f1 = NumberChineseFormater.format(0.05, true);
        Assertions.assertEquals("零点零伍", f1);
    }

    @Test
    public void digitToChineseTest() {
        String digitToChinese = Convert.digitToChinese(12412412412421.12);
        Assertions.assertEquals("壹拾贰万肆仟壹佰贰拾肆亿壹仟贰佰肆拾壹万贰仟肆佰贰拾壹元壹角贰分", digitToChinese);

        String digitToChinese2 = Convert.digitToChinese(12412412412421D);
        Assertions.assertEquals("壹拾贰万肆仟壹佰贰拾肆亿壹仟贰佰肆拾壹万贰仟肆佰贰拾壹元整", digitToChinese2);

        String digitToChinese3 = Convert.digitToChinese(2421.02);
        Assertions.assertEquals("贰仟肆佰贰拾壹元零贰分", digitToChinese3);
    }

}
