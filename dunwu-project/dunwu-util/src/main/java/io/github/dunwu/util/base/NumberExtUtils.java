package io.github.dunwu.util.base;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.common.primitives.Shorts;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Locale;

/**
 * 数字的工具类. 1.原始类型数字与byte[]的双向转换(via Guava) 2.判断字符串是否数字, 是否16进制字符串(via Common Lang) 3.10机制/16进制字符串 与 原始类型数字/数字对象
 * 的双向转换(参考Common Lang自写)
 */
public class NumberExtUtils extends NumberUtils {

	// byte[] 与原始类型互相转换
	// -------------------------------------------------------------------------------------------------

	private NumberExtUtils() {}

	// 十六进制字符串转换为包装类
	// -------------------------------------------------------------------------------------------------

	/**
	 * 将16进制的String转化为Integer. 当str为空或非数字字符串时抛NumberFormatException
	 */
	public static Integer hexToIntObject(final String str) {
		// 统一行为，不要有时候抛NPE，有时候抛NumberFormatException
		if (str == null) {
			throw new NumberFormatException("null");
		}
		return Integer.decode(str);
	}

	/**
	 * 将16进制的String转化为Integer，出错时返回默认值.
	 */
	public static Integer hexToIntObject(final String str, Integer defaultValue) {
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		try {
			return Integer.decode(str);
		} catch (final NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * 将16进制的String转化为Long 当str为空或非数字字符串时抛NumberFormatException
	 */
	public static Long hexToLongObject(final String str) {
		// 统一行为，不要有时候抛NPE，有时候抛NumberFormatException
		if (str == null) {
			throw new NumberFormatException("null");
		}
		return Long.decode(str);
	}

	/**
	 * 将16进制的String转化为Long，出错时返回默认值.
	 */
	public static Long hexToLongObject(final String str, Long defaultValue) {
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		try {
			return Long.decode(str);
		} catch (final NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * 整数转换英文
	 *
	 * @param num 整数
	 * @return
	 */
	public static String intToEnWords(final int num) {
		if (num == 0) {
			return "Zero";
		}

		int billion = num / 1000000000;
		int million = (num - billion * 1000000000) / 1000000;
		int thousand = (num - billion * 1000000000 - million * 1000000) / 1000;
		int rest = num - billion * 1000000000 - million * 1000000 - thousand * 1000;

		String result = "";
		if (billion != 0) {
			result = NumberToEnglishFormat.three(billion) + " Billion";
		}
		if (million != 0) {
			if (!result.isEmpty()) {
				result += " ";
			}
			result += NumberToEnglishFormat.three(million) + " Million";
		}
		if (thousand != 0) {
			if (!result.isEmpty()) {
				result += " ";
			}
			result += NumberToEnglishFormat.three(thousand) + " Thousand";
		}
		if (rest != 0) {
			if (!result.isEmpty()) {
				result += " ";
			}
			result += NumberToEnglishFormat.three(rest);
		}
		return result;
	}

	/**
	 * 判断字符串是否16进制
	 */
	public static boolean isHexNumber(final String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}

		int index = value.startsWith("-") ? 1 : 0;
		return value.startsWith("0x", index) || value.startsWith("0X", index)
			|| value.startsWith("#", index);
	}

	// 字符串、byte 数组转换为原始数据类型
	// -------------------------------------------------------------------------------------------------

	public static double parseDouble(final byte[] bytes) {
		return Double.longBitsToDouble(parseLong(bytes));
	}

	public static long parseLong(final byte[] bytes) {
		return Longs.fromByteArray(bytes);
	}

	public static double parseDouble(final String str) {
		return Double.parseDouble(str);
	}

	public static float parseFloat(final byte[] bytes) {
		return Float.intBitsToFloat(parseInt(bytes));
	}

	public static int parseInt(final byte[] bytes) {
		return Ints.fromByteArray(bytes);
	}

	public static float parseFloat(final String str) {
		return Float.parseFloat(str);
	}

	public static int parseInt(final String str) {
		return Integer.parseInt(str);
	}

	public static long parseLong(final String str) {
		return Long.parseLong(str);
	}

	public static short parseShort(final byte[] bytes) {
		return Shorts.fromByteArray(bytes);
	}

	public static short parseShort(final String str) {
		return Short.parseShort(str);
	}

	// 数组原始数据类型转换为 byte 数组
	// -------------------------------------------------------------------------------------------------

	/**
	 * 输出格式化为小数后两位的double字符串
	 */
	public static String to2DigitString(final double d) {
		return String.format(Locale.ROOT, "%.2f", d);
	}

	public static byte[] toBytes(final short value) {
		return Shorts.toByteArray(value);
	}

	public static byte[] toBytes(final float value) {
		return toBytes(Float.floatToRawIntBits(value));
	}

	public static byte[] toBytes(final int value) {
		return Ints.toByteArray(value);
	}

	public static byte[] toBytes(final double value) {
		return toBytes(Double.doubleToRawLongBits(value));
	}

	// 字符串转换为包装类
	// -------------------------------------------------------------------------------------------------

	public static byte[] toBytes(final long value) {
		return Longs.toByteArray(value);
	}

	/**
	 * 将10进制的String安全的转化为Double. 当str为空或非数字字符串时抛NumberFormatException
	 */
	public static Double toDoubleObject(final String str) {
		// 统一行为，不要有时候抛NPE，有时候抛NumberFormatException
		if (str == null) {
			throw new NumberFormatException("null");
		}
		return Double.valueOf(str);
	}

	/**
	 * 将10进制的String安全的转化为Long. 当str为空或非数字字符串时，返回default值
	 */
	public static Double toDoubleObject(final String str, Double defaultValue) {
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		try {
			return Double.valueOf(str);
		} catch (final NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * 将10进制的String安全的转化为Double. 当str为空或非数字字符串时抛NumberFormatException
	 */
	public static Float toFloatObject(final String str) {
		// 统一行为，不要有时候抛NPE，有时候抛NumberFormatException
		if (str == null) {
			throw new NumberFormatException("null");
		}
		return Float.valueOf(str);
	}

	/**
	 * 将10进制的String安全的转化为Long. 当str为空或非数字字符串时，返回default值
	 */
	public static Float toFloatObject(final String str, Float defaultValue) {
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		try {
			return Float.valueOf(str);
		} catch (final NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * 安全的将小于Integer.MAX的long转为int，否则抛出IllegalArgumentException异常
	 */
	public static int toInt32(final long x) {
		if ((int) x == x) {
			return (int) x;
		}
		throw new IllegalArgumentException("Int " + x + " out of range");
	}

	/**
	 * 将10进制的String安全的转化为Integer. 当str为空或非数字字符串时抛NumberFormatException
	 */
	public static Integer toIntObject(final String str) {
		return Integer.valueOf(str);
	}

	/**
	 * 将10进制的String安全的转化为Integer. 当str为空或非数字字符串时，返回default值
	 */
	public static Integer toIntObject(final String str, Integer defaultValue) {
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		try {
			return Integer.valueOf(str);
		} catch (final NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * 将10进制的String安全的转化为Long. 当str为空或非数字字符串时抛NumberFormatException
	 */
	public static Long toLongObject(final String str) {
		return Long.valueOf(str);
	}

	/**
	 * 将10进制的String安全的转化为Long. 当str为空或非数字字符串时，返回default值
	 */
	public static Long toLongObject(final String str, Long defaultValue) {
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		try {
			return Long.valueOf(str);
		} catch (final NumberFormatException nfe) {
			return defaultValue;
		}
	}

	// 数组原始数据类型转换为字符串
	// -------------------------------------------------------------------------------------------------

	/**
	 * 将10进制的String安全的转化为Integer. 当str为空或非数字字符串时抛NumberFormatException
	 */
	public static Short toShortObject(final String str) {
		return Short.valueOf(str);
	}

	/**
	 * 将10进制的String安全的转化为Integer. 当str为空或非数字字符串时，返回default值
	 */
	public static Short toShortObject(final String str, Short defaultValue) {
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		try {
			return Short.valueOf(str);
		} catch (final NumberFormatException nfe) {
			return defaultValue;
		}
	}

	public static String toString(final short value) {
		return Short.toString(value);
	}

	public static String toString(final int value) {
		return Integer.toString(value);
	}

	public static String toString(final long value) {
		return Long.toString(value);
	}

	// 杂项
	// -------------------------------------------------------------------------------------------------

	public static String toString(final float value) {
		return Float.toString(value);
	}

	public static String toString(final double value) {
		return Double.toString(value);
	}

	static class NumberToEnglishFormat {

		private static final int NUM10 = 10;

		private static final int NUM20 = 20;

		static String three(int num) {
			int hundred = num / 100;
			int rest = num - hundred * 100;
			String res = "";
			if (hundred * rest != 0) {
				res = one(hundred) + " Hundred " + two(rest);
			} else if ((hundred == 0) && (rest != 0)) {
				res = two(rest);
			} else if ((hundred != 0) && (rest == 0)) {
				res = one(hundred) + " Hundred";
			}
			return res;
		}

		static String one(int num) {
			switch (num) {
				case 1:
					return "One";
				case 2:
					return "Two";
				case 3:
					return "Three";
				case 4:
					return "Four";
				case 5:
					return "Five";
				case 6:
					return "Six";
				case 7:
					return "Seven";
				case 8:
					return "Eight";
				case 9:
					return "Nine";
				default:
					return "";
			}
		}

		static String two(int num) {
			if (num == 0) {
				return "";
			} else if (num < NUM10) {
				return one(num);
			} else if (num < NUM20) {
				return twoLessThan20(num);
			} else {
				int tenner = num / 10;
				int rest = num - tenner * 10;
				if (rest != 0) {
					return ten(tenner) + " " + one(rest);
				} else {
					return ten(tenner);
				}
			}
		}

		static String twoLessThan20(int num) {
			switch (num) {
				case 10:
					return "Ten";
				case 11:
					return "Eleven";
				case 12:
					return "Twelve";
				case 13:
					return "Thirteen";
				case 14:
					return "Fourteen";
				case 15:
					return "Fifteen";
				case 16:
					return "Sixteen";
				case 17:
					return "Seventeen";
				case 18:
					return "Eighteen";
				case 19:
					return "Nineteen";
				default:
					return "";
			}
		}

		static String ten(int num) {
			switch (num) {
				case 2:
					return "Twenty";
				case 3:
					return "Thirty";
				case 4:
					return "Forty";
				case 5:
					return "Fifty";
				case 6:
					return "Sixty";
				case 7:
					return "Seventy";
				case 8:
					return "Eighty";
				case 9:
					return "Ninety";
				default:
					return "";
			}
		}

	}

}