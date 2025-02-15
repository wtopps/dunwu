package io.github.dunwu.tool.text.replacer;

import io.github.dunwu.tool.lang.Chain;
import io.github.dunwu.tool.text.StrBuilder;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 字符串替换链，用于组合多个字符串替换逻辑
 *
 * @author looly
 * @since 4.1.5
 */
public class ReplacerChain extends StrReplacer implements Chain<StrReplacer, ReplacerChain> {

    private static final long serialVersionUID = 1L;

    private List<StrReplacer> replacers = new LinkedList<>();

    /**
     * 构造
     *
     * @param strReplacers 字符串替换器
     */
    public ReplacerChain(StrReplacer... strReplacers) {
        for (StrReplacer strReplacer : strReplacers) {
            addChain(strReplacer);
        }
    }

    @Override
    public ReplacerChain addChain(StrReplacer element) {
        replacers.add(element);
        return this;
    }

    @Override
    public Iterator<StrReplacer> iterator() {
        return replacers.iterator();
    }

    @Override
    protected int replace(CharSequence str, int pos, StrBuilder out) {
        int consumed = 0;
        for (StrReplacer strReplacer : replacers) {
            consumed = strReplacer.replace(str, pos, out);
            if (0 != consumed) {
                return consumed;
            }
        }
        return consumed;
    }

}
