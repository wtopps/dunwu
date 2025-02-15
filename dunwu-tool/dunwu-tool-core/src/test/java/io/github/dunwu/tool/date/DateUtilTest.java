package io.github.dunwu.tool.date;

import io.github.dunwu.tool.collection.CollectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 时间工具单元测试<br> 此单元测试依赖时区为中国+08:00
 *
 * <pre>
 * export TZ=Asia/Shanghai
 * </pre>
 *
 * @author Looly
 */
public class DateUtilTest {

    @Test
    public void nowTest() {
        // 当前时间
        Date date = DateUtil.toDateTime();
        Assertions.assertNotNull(date);
        // 当前时间
        Date date2 = DateUtil.toDateTime(Calendar.getInstance());
        Assertions.assertNotNull(date2);
        // 当前时间
        Date date3 = DateUtil.toDateTime(System.currentTimeMillis());
        Assertions.assertNotNull(date3);

        // 当前日期字符串，格式：yyyy-MM-dd HH:mm:ss
        String now = DateUtil.now();
        Assertions.assertNotNull(now);
        // 当前日期字符串，格式：yyyy-MM-dd
        String today = DateUtil.today();
        Assertions.assertNotNull(today);
    }

    @Test
    public void formatAndParseTest() {
        String dateStr = "2017-03-01";
        Date date = DateUtil.parse(dateStr);

        String format = DateUtil.format(date, "yyyy/MM/dd");
        Assertions.assertEquals("2017/03/01", format);

        // 常用格式的格式化
        String formatDate = DateUtil.formatDate(date);
        Assertions.assertEquals("2017-03-01", formatDate);
        String formatDateTime = DateUtil.formatDateTime(date);
        Assertions.assertEquals("2017-03-01 00:00:00", formatDateTime);
        String formatTime = DateUtil.formatTime(date);
        Assertions.assertEquals("00:00:00", formatTime);
    }

    @Test
    public void beginAndEndTest() {
        String dateStr = "2017-03-01 22:33:23";
        Date date = DateUtil.parse(dateStr);

        // 一天的开始
        Date beginOfDay = DateUtil.beginOfDay(date);
        Assertions.assertEquals("2017-03-01 00:00:00", beginOfDay.toString());
        // 一天的结束
        Date endOfDay = DateUtil.endOfDay(date);
        Assertions.assertEquals("2017-03-01 23:59:59", endOfDay.toString());
    }

    @Test
    public void beginAndWeedTest() {
        String dateStr = "2017-03-01 22:33:23";
        DateTime date = DateUtil.parse(dateStr);
        date.setFirstDayOfWeek(Week.MONDAY);

        // 一周的开始
        Date beginOfWeek = DateUtil.beginOfWeek(date);
        Assertions.assertEquals("2017-02-27 00:00:00", beginOfWeek.toString());
        // 一周的结束
        Date endOfWeek = DateUtil.endOfWeek(date);
        Assertions.assertEquals("2017-03-05 23:59:59", endOfWeek.toString());

        Calendar calendar = DateUtil.toCalendar(date);
        // 一周的开始
        Calendar begin = DateUtil.beginOfWeek(calendar);
        Assertions.assertEquals("2017-02-27 00:00:00", DateUtil.toDateTime(begin).toString());
        // 一周的结束
        Calendar end = DateUtil.endOfWeek(calendar);
        Assertions.assertEquals("2017-03-05 23:59:59", DateUtil.toDateTime(end).toString());
    }

    @Test
    public void offsetDateTest() {
        String dateStr = "2017-03-01 22:33:23";
        Date date = DateUtil.parse(dateStr);

        Date newDate = DateUtil.offset(date, DateField.DAY_OF_MONTH, 2);
        Assertions.assertEquals("2017-03-03 22:33:23", newDate.toString());

        // 偏移天
        DateTime newDate2 = DateUtil.offsetDay(date, 3);
        Assertions.assertEquals("2017-03-04 22:33:23", newDate2.toString());

        // 偏移小时
        DateTime newDate3 = DateUtil.offsetHour(date, -3);
        Assertions.assertEquals("2017-03-01 19:33:23", newDate3.toString());

        // 偏移月
        DateTime offsetMonth = DateUtil.offsetMonth(date, -1);
        Assertions.assertEquals("2017-02-01 22:33:23", offsetMonth.toString());
    }

    @Test
    public void offsetMonthTest() {
        DateTime st = DateUtil.parseDate("2018-05-31");
        List<DateTime> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(DateUtil.offsetMonth(st, i));
        }
        Assertions.assertEquals("2018-05-31 00:00:00", list.get(0).toString());
        Assertions.assertEquals("2018-06-30 00:00:00", list.get(1).toString());
        Assertions.assertEquals("2018-07-31 00:00:00", list.get(2).toString());
        Assertions.assertEquals("2018-08-31 00:00:00", list.get(3).toString());
    }

    @Test
    public void betweenTest() {
        String dateStr1 = "2017-03-01 22:34:23";
        Date date1 = DateUtil.parse(dateStr1);

        String dateStr2 = "2017-04-01 23:56:14";
        Date date2 = DateUtil.parse(dateStr2);

        // 相差月
        long betweenMonth = DateUtil.betweenMonth(date1, date2, false);
        Assertions.assertEquals(1, betweenMonth);// 相差一个月
        // 反向
        betweenMonth = DateUtil.betweenMonth(date2, date1, false);
        Assertions.assertEquals(1, betweenMonth);// 相差一个月

        // 相差天
        long betweenDay = DateUtil.between(date1, date2, DateUnit.DAY);
        Assertions.assertEquals(31, betweenDay);// 相差一个月，31天
        // 反向
        betweenDay = DateUtil.between(date2, date1, DateUnit.DAY);
        Assertions.assertEquals(31, betweenDay);// 相差一个月，31天

        // 相差小时
        long betweenHour = DateUtil.between(date1, date2, DateUnit.HOUR);
        Assertions.assertEquals(745, betweenHour);
        // 反向
        betweenHour = DateUtil.between(date2, date1, DateUnit.HOUR);
        Assertions.assertEquals(745, betweenHour);

        // 相差分
        long betweenMinute = DateUtil.between(date1, date2, DateUnit.MINUTE);
        Assertions.assertEquals(44721, betweenMinute);
        // 反向
        betweenMinute = DateUtil.between(date2, date1, DateUnit.MINUTE);
        Assertions.assertEquals(44721, betweenMinute);

        // 相差秒
        long betweenSecond = DateUtil.between(date1, date2, DateUnit.SECOND);
        Assertions.assertEquals(2683311, betweenSecond);
        // 反向
        betweenSecond = DateUtil.between(date2, date1, DateUnit.SECOND);
        Assertions.assertEquals(2683311, betweenSecond);

        // 相差秒
        long betweenMS = DateUtil.between(date1, date2, DateUnit.MS);
        Assertions.assertEquals(2683311000L, betweenMS);
        // 反向
        betweenMS = DateUtil.between(date2, date1, DateUnit.MS);
        Assertions.assertEquals(2683311000L, betweenMS);
    }

    @Test
    public void betweenTest2() {
        long between = DateUtil.between(DateUtil.parse("2019-05-06 02:15:00"), DateUtil.parse("2019-05-06 02:20:00"),
            DateUnit.HOUR);
        Assertions.assertEquals(0, between);
    }

    @Test
    public void formatChineseDateTest() {
        String formatChineseDate = DateUtil.formatChineseDate(DateUtil.parse("2018-02-24"), true);
        Assertions.assertEquals("二〇一八年二月二十四日", formatChineseDate);
    }

    @Test
    public void formatBetweenTest() {
        String dateStr1 = "2017-03-01 22:34:23";
        Date date1 = DateUtil.parse(dateStr1);

        String dateStr2 = "2017-04-01 23:56:14";
        Date date2 = DateUtil.parse(dateStr2);

        long between = DateUtil.between(date1, date2, DateUnit.MS);
        String formatBetween = DateUtil.formatBetween(between, BetweenFormater.Level.MINUTE);
        Assertions.assertEquals("31天1小时21分", formatBetween);
    }

    @Test
    public void timerTest() {
        TimeInterval timer = DateUtil.timer();

        // ---------------------------------
        // -------这是执行过程
        // ---------------------------------

        timer.interval();// 花费毫秒数
        timer.intervalRestart();// 返回花费时间，并重置开始时间
        timer.intervalMinute();// 花费分钟数
    }

    @Test
    public void currentTest() {
        long current = DateUtil.current(false);
        String currentStr = String.valueOf(current);
        Assertions.assertEquals(13, currentStr.length());

        long currentNano = DateUtil.current(true);
        String currentNanoStr = String.valueOf(currentNano);
        Assertions.assertNotNull(currentNanoStr);
    }

    @Test
    public void weekOfYearTest() {
        // 第一周周日
        int weekOfYear1 = DateUtil.weekOfYear(DateUtil.parse("2016-01-03"));
        Assertions.assertEquals(1, weekOfYear1);

        // 第二周周四
        int weekOfYear2 = DateUtil.weekOfYear(DateUtil.parse("2016-01-07"));
        Assertions.assertEquals(2, weekOfYear2);
    }

    @Test
    public void timeToSecondTest() {
        int second = DateUtil.timeToSecond("00:01:40");
        Assertions.assertEquals(100, second);
        second = DateUtil.timeToSecond("00:00:40");
        Assertions.assertEquals(40, second);
        second = DateUtil.timeToSecond("01:00:00");
        Assertions.assertEquals(3600, second);
        second = DateUtil.timeToSecond("00:00:00");
        Assertions.assertEquals(0, second);
    }

    @Test
    public void secondToTime() {
        String time = DateUtil.secondToTime(3600);
        Assertions.assertEquals("01:00:00", time);
        time = DateUtil.secondToTime(3800);
        Assertions.assertEquals("01:03:20", time);
        time = DateUtil.secondToTime(0);
        Assertions.assertEquals("00:00:00", time);
        time = DateUtil.secondToTime(30);
        Assertions.assertEquals("00:00:30", time);
    }

    @Test
    public void parseTest2() {
        // 转换时间与SimpleDateFormat结果保持一致即可
        String birthday = "700403";
        Date birthDate = DateUtil.parse(birthday, "yyMMdd");
        // 获取出生年(完全表现形式,如：2010)
        int sYear = DateUtil.year(birthDate);
        Assertions.assertEquals(1970, sYear);
    }

    @Test
    public void parseTest3() {
        String dateStr = "2018-10-10 12:11:11";
        Date date = DateUtil.parse(dateStr);
        String format = DateUtil.format(date, DatePattern.NORM_DATETIME_PATTERN);
        Assertions.assertEquals(dateStr, format);
    }

    @Test
    public void parseTest4() {
        String ymd = DateUtil.parse("2019-3-21 12:20:15", "yyyy-MM-dd").toString(DatePattern.PURE_DATE_PATTERN);
        Assertions.assertEquals("20190321", ymd);
    }

    @Test
    public void parseTest5() {
        // 测试时间解析
        String time = DateUtil.parse("22:12:12").toString(DatePattern.NORM_TIME_FORMAT);
        Assertions.assertEquals("22:12:12", time);
        time = DateUtil.parse("2:12:12").toString(DatePattern.NORM_TIME_FORMAT);
        Assertions.assertEquals("02:12:12", time);
        time = DateUtil.parse("2:2:12").toString(DatePattern.NORM_TIME_FORMAT);
        Assertions.assertEquals("02:02:12", time);
        time = DateUtil.parse("2:2:1").toString(DatePattern.NORM_TIME_FORMAT);
        Assertions.assertEquals("02:02:01", time);
        time = DateUtil.parse("22:2:1").toString(DatePattern.NORM_TIME_FORMAT);
        Assertions.assertEquals("22:02:01", time);
        time = DateUtil.parse("2:22:1").toString(DatePattern.NORM_TIME_FORMAT);
        Assertions.assertEquals("02:22:01", time);

        // 测试两位时间解析
        time = DateUtil.parse("2:22").toString(DatePattern.NORM_TIME_FORMAT);
        Assertions.assertEquals("02:22:00", time);
        time = DateUtil.parse("12:22").toString(DatePattern.NORM_TIME_FORMAT);
        Assertions.assertEquals("12:22:00", time);
        time = DateUtil.parse("12:2").toString(DatePattern.NORM_TIME_FORMAT);
        Assertions.assertEquals("12:02:00", time);
    }

    @Test
    public void parseTest6() {
        String str = "Tue Jun 4 16:25:15 +0800 2019";
        DateTime dateTime = DateUtil.parse(str);
        Assertions.assertEquals("2019-06-04 16:25:15", dateTime.toString());
    }

    @Test
    public void parseTest7() {
        String str = "2019-06-01T19:45:43.000 +0800";
        DateTime dateTime = DateUtil.parse(str, "yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Assertions.assertEquals("2019-06-01 19:45:43", dateTime.toString());
    }

    @Test
    public void parseAndOffsetTest() {
        // 检查UTC时间偏移是否准确
        String str = "2019-09-17T13:26:17.948Z";
        DateTime dateTime = DateUtil.parse(str);
        Assertions.assertEquals("2019-09-17 13:26:17", dateTime.toString());

        DateTime offset = DateUtil.offsetHour(dateTime, 8);
        Assertions.assertEquals("2019-09-17 21:26:17", offset.toString());
    }

    @Test
    public void parseDateTest() {
        String dateStr = "2018-4-10";
        Date date = DateUtil.parseDate(dateStr);
        String format = DateUtil.format(date, DatePattern.NORM_DATE_PATTERN);
        Assertions.assertEquals("2018-04-10", format);
    }

    @Test
    public void parseToDateTimeTest1() {
        String dateStr1 = "2017-02-01";
        String dateStr2 = "2017/02/01";
        String dateStr3 = "2017.02.01";
        String dateStr4 = "2017年02月01日";

        DateTime dt1 = DateUtil.parse(dateStr1);
        DateTime dt2 = DateUtil.parse(dateStr2);
        DateTime dt3 = DateUtil.parse(dateStr3);
        DateTime dt4 = DateUtil.parse(dateStr4);
        Assertions.assertEquals(dt1, dt2);
        Assertions.assertEquals(dt2, dt3);
        Assertions.assertEquals(dt3, dt4);
    }

    @Test
    public void parseToDateTimeTest2() {
        String dateStr1 = "2017-02-01 12:23";
        String dateStr2 = "2017/02/01 12:23";
        String dateStr3 = "2017.02.01 12:23";
        String dateStr4 = "2017年02月01日 12:23";

        DateTime dt1 = DateUtil.parse(dateStr1);
        DateTime dt2 = DateUtil.parse(dateStr2);
        DateTime dt3 = DateUtil.parse(dateStr3);
        DateTime dt4 = DateUtil.parse(dateStr4);
        Assertions.assertEquals(dt1, dt2);
        Assertions.assertEquals(dt2, dt3);
        Assertions.assertEquals(dt3, dt4);
    }

    @Test
    public void parseToDateTimeTest3() {
        String dateStr1 = "2017-02-01 12:23:45";
        String dateStr2 = "2017/02/01 12:23:45";
        String dateStr3 = "2017.02.01 12:23:45";
        String dateStr4 = "2017年02月01日 12时23分45秒";

        DateTime dt1 = DateUtil.parse(dateStr1);
        DateTime dt2 = DateUtil.parse(dateStr2);
        DateTime dt3 = DateUtil.parse(dateStr3);
        DateTime dt4 = DateUtil.parse(dateStr4);
        Assertions.assertEquals(dt1, dt2);
        Assertions.assertEquals(dt2, dt3);
        Assertions.assertEquals(dt3, dt4);
    }

    @Test
    public void parseToDateTimeTest4() {
        String dateStr1 = "2017-02-01 12:23:45";
        String dateStr2 = "20170201122345";

        DateTime dt1 = DateUtil.parse(dateStr1);
        DateTime dt2 = DateUtil.parse(dateStr2);
        Assertions.assertEquals(dt1, dt2);
    }

    @Test
    public void parseToDateTimeTest5() {
        String dateStr1 = "2017-02-01";
        String dateStr2 = "20170201";

        DateTime dt1 = DateUtil.parse(dateStr1);
        DateTime dt2 = DateUtil.parse(dateStr2);
        Assertions.assertEquals(dt1, dt2);
    }

    @Test
    public void parseUTCTest() {
        String dateStr1 = "2018-09-13T05:34:31Z";
        DateTime dt = DateUtil.parseUTC(dateStr1);

        // parse方法支持UTC格式测试
        DateTime dt2 = DateUtil.parse(dateStr1);
        Assertions.assertEquals(dt, dt2);

        // 默认使用Pattern对应的时区，即UTC时区
        String dateStr = dt.toString();
        Assertions.assertEquals("2018-09-13 05:34:31", dateStr);

        // 使用当前（上海）时区
        dateStr = dt.toString(TimeZone.getTimeZone("GMT+8:00"));
        Assertions.assertEquals("2018-09-13 13:34:31", dateStr);

        dateStr1 = "2018-09-13T13:34:32+0800";
        dt = DateUtil.parseUTC(dateStr1);
        dateStr = dt.toString(TimeZone.getTimeZone("GMT+8:00"));
        Assertions.assertEquals("2018-09-13 13:34:32", dateStr);

        dateStr1 = "2018-09-13T13:34:33+08:00";
        dt = DateUtil.parseUTC(dateStr1);
        dateStr = dt.toString(TimeZone.getTimeZone("GMT+8:00"));
        Assertions.assertEquals("2018-09-13 13:34:33", dateStr);

        dateStr1 = "2018-09-13T13:34:34+0800";
        dt = DateUtil.parse(dateStr1);
        dateStr = dt.toString(TimeZone.getTimeZone("GMT+8:00"));
        Assertions.assertEquals("2018-09-13 13:34:34", dateStr);

        dateStr1 = "2018-09-13T13:34:35+08:00";
        dt = DateUtil.parse(dateStr1);
        dateStr = dt.toString(TimeZone.getTimeZone("GMT+8:00"));
        Assertions.assertEquals("2018-09-13 13:34:35", dateStr);

        dateStr1 = "2018-09-13T13:34:36.999+0800";
        dt = DateUtil.parseUTC(dateStr1);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern.NORM_DATETIME_MS_PATTERN);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        dateStr = dt.toString(simpleDateFormat);
        Assertions.assertEquals("2018-09-13 13:34:36.999", dateStr);

        dateStr1 = "2018-09-13T13:34:37.999+08:00";
        dt = DateUtil.parseUTC(dateStr1);
        dateStr = dt.toString(simpleDateFormat);
        Assertions.assertEquals("2018-09-13 13:34:37.999", dateStr);

        dateStr1 = "2018-09-13T13:34:38.999+0800";
        dt = DateUtil.parse(dateStr1);
        dateStr = dt.toString(simpleDateFormat);
        Assertions.assertEquals("2018-09-13 13:34:38.999", dateStr);

        dateStr1 = "2018-09-13T13:34:39.999+08:00";
        dt = DateUtil.parse(dateStr1);
        dateStr = dt.toString(simpleDateFormat);
        Assertions.assertEquals("2018-09-13 13:34:39.999", dateStr);
    }

    @Test
    public void parseCSTTest() {
        String dateStr = "Wed Sep 16 11:26:23 CST 2009";
        DateTime dateTime = DateUtil.parseCST(dateStr);
        Assertions.assertEquals("2009-09-17 01:26:23", dateTime.toString());

        dateTime = DateUtil.parse(dateStr);
        Assertions.assertEquals("2009-09-17 01:26:23", dateTime.toString());
    }

    @Test
    public void parseJDkTest() {
        String dateStr = "Thu May 16 17:57:18 GMT+08:00 2019";
        DateTime time = DateUtil.parse(dateStr);
        Assertions.assertEquals("2019-05-16 17:57:18", time.toString());
    }

    @Test
    public void endOfYearTest() {
        DateTime date = DateUtil.toDateTime();
        date.setField(DateField.YEAR, 2019);
        DateTime endOfYear = DateUtil.endOfYear(date);
        Assertions.assertEquals("2019-12-31 23:59:59", endOfYear.toString());
    }

    @Test
    public void endOfWeekTest() {
        // 周日
        DateTime now = DateUtil.parse("2019-09-15 13:00");

        DateTime startOfWeek = DateUtil.beginOfWeek(now);
        Assertions.assertEquals("2019-09-09 00:00:00", startOfWeek.toString());
        DateTime endOfWeek = DateUtil.endOfWeek(now);
        Assertions.assertEquals("2019-09-15 23:59:59", endOfWeek.toString());

        long between = DateUtil.between(endOfWeek, startOfWeek, DateUnit.DAY);
        // 周一和周日相距6天
        Assertions.assertEquals(6, between);
    }

    @Test
    public void dayOfWeekTest() {
        int dayOfWeek = DateUtil.dayOfWeek(DateUtil.parse("2018-03-07"));
        Assertions.assertEquals(Calendar.WEDNESDAY, dayOfWeek);
        Week week = DateUtil.dayOfWeekEnum(DateUtil.parse("2018-03-07"));
        Assertions.assertEquals(Week.WEDNESDAY, week);
    }

    @Test
    public void rangeTest() {
        DateTime start = DateUtil.parse("2017-01-01");
        DateTime end = DateUtil.parse("2017-01-03");

        // 测试包含开始和结束情况下步进为1的情况
        DateRange range = DateUtil.range(start, end, DateField.DAY_OF_YEAR);
        Assertions.assertEquals(range.next(), DateUtil.parse("2017-01-01"));
        Assertions.assertEquals(range.next(), DateUtil.parse("2017-01-02"));
        Assertions.assertEquals(range.next(), DateUtil.parse("2017-01-03"));
        try {
            range.next();
            Assertions.fail("已超过边界，下一个元素不应该存在！");
        } catch (NoSuchElementException ignored) {
        }

        // 测试多步进的情况
        range = new DateRange(start, end, DateField.DAY_OF_YEAR, 2);
        Assertions.assertEquals(range.next(), DateUtil.parse("2017-01-01"));
        Assertions.assertEquals(range.next(), DateUtil.parse("2017-01-03"));

        // 测试不包含开始结束时间的情况
        range = new DateRange(start, end, DateField.DAY_OF_YEAR, 1, false, false);
        Assertions.assertEquals(range.next(), DateUtil.parse("2017-01-02"));
        try {
            range.next();
            Assertions.fail("不包含结束时间情况下，下一个元素不应该存在！");
        } catch (NoSuchElementException ignored) {
        }
    }

    @Test
    public void rangeToListTest() {
        DateTime start = DateUtil.parse("2017-01-01");
        DateTime end = DateUtil.parse("2017-01-31");

        List<DateTime> rangeToList = DateUtil.rangeToList(start, end, DateField.DAY_OF_YEAR);
        Assertions.assertEquals(rangeToList.get(0), DateUtil.parse("2017-01-01"));
        Assertions.assertEquals(rangeToList.get(1), DateUtil.parse("2017-01-02"));
    }

    @Test
    public void yearAndQTest() {
        String yearAndQuarter = DateUtil.yearAndQuarter(DateUtil.parse("2018-12-01"));
        Assertions.assertEquals("20184", yearAndQuarter);

        LinkedHashSet<String> yearAndQuarters =
            DateUtil.yearAndQuarter(DateUtil.parse("2018-09-10"), DateUtil.parse("2018-12-20"));
        List<String> list = CollectionUtil.list(false, yearAndQuarters);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals("20183", list.get(0));
        Assertions.assertEquals("20184", list.get(1));

        LinkedHashSet<String> yearAndQuarters2 =
            DateUtil.yearAndQuarter(DateUtil.parse("2018-10-10"), DateUtil.parse("2018-12-10"));
        List<String> list2 = CollectionUtil.list(false, yearAndQuarters2);
        Assertions.assertEquals(1, list2.size());
        Assertions.assertEquals("20184", list2.get(0));
    }

    @Test
    public void formatHttpDateTest() {
        String formatHttpDate = DateUtil.formatHttpDate(DateUtil.parse("2019-01-02 22:32:01"));
        Assertions.assertEquals("Wed, 02 Jan 2019 14:32:01 GMT", formatHttpDate);
    }

    @Test
    public void toInstantTest() {
        LocalDateTime localDateTime = LocalDateTime.parse("2017-05-06T08:30:00", DateTimeFormatter.ISO_DATE_TIME);
        Instant instant = DateUtil.toInstant(localDateTime);
        Assertions.assertEquals("2017-05-06T00:30:00Z", instant.toString());

        LocalDate localDate = localDateTime.toLocalDate();
        instant = DateUtil.toInstant(localDate);
        Assertions.assertNotNull(instant);

        LocalTime localTime = localDateTime.toLocalTime();
        instant = DateUtil.toInstant(localTime);
        Assertions.assertNotNull(instant);
    }

    @Test
    public void dateTest() {
        LocalDateTime localDateTime = LocalDateTime.parse("2017-05-06T08:30:00", DateTimeFormatter.ISO_DATE_TIME);
        DateTime date = DateUtil.toDateTime(localDateTime);
        Assertions.assertEquals("2017-05-06 08:30:00", date.toString());
    }

    @Test
    public void ageTest() {
        String d1 = "2000-02-29";
        String d2 = "2018-02-28";
        final int age = DateUtil.age(DateUtil.parseDate(d1), DateUtil.parseDate(d2));
        Assertions.assertEquals(18, age);
    }

}
