package io.github.dunwu.tool.clone;

import io.github.dunwu.tool.exceptions.ExceptionUtil;
import io.github.dunwu.tool.util.StringUtil;

/**
 * 克隆异常
 *
 * @author xiaoleilu
 */
public class CloneRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 6774837422188798989L;

    public CloneRuntimeException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public CloneRuntimeException(String message) {
        super(message);
    }

    public CloneRuntimeException(String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params));
    }

    public CloneRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CloneRuntimeException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params), throwable);
    }

}
