package io.github.dunwu.web.converter;

import io.github.dunwu.tool.date.DatePattern;
import io.github.dunwu.tool.util.StringUtil;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-14
 */
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String dateString) {
        if (StringUtil.isBlank(dateString)) {
            return null;
        }

        Date date = null;
        try {
            date = DatePattern.NORM_DATETIME_FORMAT.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

}
