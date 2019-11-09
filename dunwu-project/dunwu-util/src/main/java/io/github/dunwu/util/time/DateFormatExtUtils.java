package io.github.dunwu.util.time;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Date;

/**
 * Date的parse()与format(), 采用Apache Common Lang中线程安全, 性能更佳的FastDateFormat 注意Common Lang版本，3.5版才使用StringBuilder，3
 * .4及以前使用StringBuffer. 1. 常用格式的FastDateFormat定义, 常用格式直接使用这些FastDateFormat 2. 日期格式不固定时的String<->Date 转换函数. 3.
 * 打印时间间隔，如"01:10:10"，以及用户友好的版本，比如"刚刚"，"10分钟前"
 *
 * @see FastDateFormat#parse(String)
 * @see FastDateFormat#format(Date)
 * @see FastDateFormat#format(long)
 */
public class DateFormatExtUtils extends DateFormatUtils {

	// 以T分隔日期和时间，并带时区信息，符合ISO8601规范
	public static final FastDateFormat ISO_FORMAT = FastDateFormat
		.getInstance(DatePattern.PATTERN_ISO.pattern());

	public static final FastDateFormat ISO_ON_SECOND_FORMAT = FastDateFormat
		.getInstance(DatePattern.PATTERN_ISO_ON_SECOND.pattern());

	public static final FastDateFormat ISO_ON_DATE_FORMAT = FastDateFormat
		.getInstance(DatePattern.PATTERN_ISO_ON_DATE.pattern());

	// 以空格分隔日期和时间，不带时区信息
	public static final FastDateFormat DEFAULT_FORMAT = FastDateFormat
		.getInstance(DatePattern.PATTERN_DEFAULT.pattern());

	public static final FastDateFormat DEFAULT_ON_SECOND_FORMAT = FastDateFormat
		.getInstance(DatePattern.PATTERN_DEFAULT_ON_SECOND.pattern());

	// parseDate
	// -------------------------------------------------------------------------------------------------

	/**
	 * 按HH:mm:ss.SSS格式，格式化时间间隔. endDate必须大于startDate，间隔可大于1天，
	 *
	 * @see DurationFormatUtils
	 */
	public static String formatDuration(final Date startDate, final Date endDate) {
		return DurationFormatUtils
			.formatDurationHMS(endDate.getTime() - startDate.getTime());
	}

	/**
	 * 按HH:mm:ss.SSS格式，格式化时间间隔 单位为毫秒，必须大于0，可大于1天
	 *
	 * @see DurationFormatUtils
	 */
	public static String formatDuration(final long durationMillis) {
		return DurationFormatUtils.formatDurationHMS(durationMillis);
	}

	// formatDuration
	// -------------------------------------------------------------------------------------------------

	/**
	 * 按HH:mm:ss格式，格式化时间间隔 endDate必须大于startDate，间隔可大于1天
	 *
	 * @see DurationFormatUtils
	 */
	public static String formatDurationOnSecond(final Date startDate,
		final Date endDate) {
		return DurationFormatUtils.formatDuration(endDate.getTime() - startDate.getTime(),
			"HH:mm:ss");
	}

	/**
	 * 按HH:mm:ss格式，格式化时间间隔 单位为毫秒，必须大于0，可大于1天
	 *
	 * @see DurationFormatUtils
	 */
	public static String formatDurationOnSecond(final long durationMillis) {
		return DurationFormatUtils.formatDuration(durationMillis, "HH:mm:ss");
	}

	/**
	 * 打印用户友好的，与当前时间相比的时间差，如刚刚，5分钟前，今天XXX，昨天XXX copy from AndroidUtilCode
	 */
	public static String formatFriendlyTimeSpanByNow(final Date date) {
		return formatFriendlyTimeSpanByNow(date.getTime());
	}

	/**
	 * 打印用户友好的，与当前时间相比的时间差，如刚刚，5分钟前，今天XXX，昨天XXX copy from AndroidUtilCode
	 */
	public static String formatFriendlyTimeSpanByNow(final long timeStampMillis) {
		long now = ClockUtils.currentTimeMillis();
		long span = now - timeStampMillis;
		if (span < 0) {
			// 'c' 日期和时间，被格式化为 "%ta %tb %td %tT %tZ %tY"，例如 "Sun Jul 20 16:17:00 EDT
			// 1969"。
			return String.format("%tc", timeStampMillis);
		}
		if (span < DateExtUtils.MILLIS_PER_SECOND) {
			return "刚刚";
		} else if (span < DateExtUtils.MILLIS_PER_MINUTE) {
			return String.format("%d秒前", span / DateExtUtils.MILLIS_PER_SECOND);
		} else if (span < DateExtUtils.MILLIS_PER_HOUR) {
			return String.format("%d分钟前", span / DateExtUtils.MILLIS_PER_MINUTE);
		}
		// 获取当天00:00
		long wee = DateExtUtils.beginOfDate(new Date(now)).getTime();
		if (timeStampMillis >= wee) {
			// 'R' 24 小时制的时间，被格式化为 "%tH:%tM"
			return String.format("今天%tR", timeStampMillis);
		} else if (timeStampMillis >= wee - DateExtUtils.MILLIS_PER_DAY) {
			return String.format("昨天%tR", timeStampMillis);
		} else {
			// 'F' ISO 8601 格式的完整日期，被格式化为 "%tY-%tm-%td"。
			return String.format("%tF", timeStampMillis);
		}
	}

	// formatFriendlyTimeSpanByNow
	// -------------------------------------------------------------------------------------------------

	public static Date parseDate(final DatePattern datePattern, final String dateString)
		throws ParseException {
		return parseDate(datePattern.pattern(), dateString);
	}

	/**
	 * 分析日期字符串, 仅用于pattern不固定的情况. 否则直接使用DateFormats中封装好的FastDateFormat. FastDateFormat.getInstance()
	 * 已经做了缓存，不会每次创建对象，但直接使用对象仍然能减少在缓存中的查找.
	 */
	public static Date parseDate(final String pattern, final String dateString)
		throws ParseException {
		return FastDateFormat.getInstance(pattern).parse(dateString);
	}

	public enum DatePattern {

		// 以T分隔日期和时间，并带时区信息，符合ISO8601规范
		PATTERN_ISO("yyyy-MM-dd'T'HH:mm:ss.SSSZZ"),
		PATTERN_ISO_ON_SECOND(
			"yyyy-MM-dd'T'HH:mm:ssZZ"),
		PATTERN_ISO_ON_DATE("yyyy-MM-dd"),

		// 以空格分隔日期和时间，不带时区信息
		PATTERN_DEFAULT("yyyy-MM-dd HH:mm:ss.SSS"),
		PATTERN_DEFAULT_ON_SECOND(
			"yyyy-MM-dd HH:mm:ss");

		private String pattern;

		DatePattern(String pattern) {
			this.pattern = pattern;
		}

		public String pattern() {
			return pattern;
		}
	}

}
