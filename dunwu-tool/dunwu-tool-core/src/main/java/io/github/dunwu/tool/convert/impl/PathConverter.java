package io.github.dunwu.tool.convert.impl;

import io.github.dunwu.tool.convert.AbstractConverter;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 字符串转换器
 *
 * @author Looly
 */
public class PathConverter extends AbstractConverter<Path> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Path convertInternal(Object value) {
        try {
            if (value instanceof URI) {
                return Paths.get((URI) value);
            }

            if (value instanceof URL) {
                return Paths.get(((URL) value).toURI());
            }

            if (value instanceof File) {
                return ((File) value).toPath();
            }

            return Paths.get(convertToStr(value));
        } catch (Exception e) {
            // Ignore Exception
        }
        return null;
    }

}
