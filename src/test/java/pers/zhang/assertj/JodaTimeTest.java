package pers.zhang.assertj;

/**
 * @Author: acton_zhang
 * @Date: 2024/1/25 2:43 下午
 * @Version 1.0
 */

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static org.assertj.jodatime.api.Assertions.assertThat;

public class JodaTimeTest {

    @Test
    public void test() {
        DateTime dateTime1 = new DateTime(2000, 1, 1, 0, 0, 0, DateTimeZone.UTC);
        DateTime dateTime2 = new DateTime(2001, 1, 1, 0, 0, 0, DateTimeZone.UTC);

        assertThat(dateTime1).isBefore(dateTime2);
        assertThat(dateTime2).isAfterOrEqualTo(dateTime1);

        //自动转换字符串
        assertThat(new DateTime("2000-01-01")).isEqualTo("2000-01-01");

        //datetime比较忽略秒和毫秒
        dateTime1 = new DateTime(2000, 1, 1, 23, 50, 0, 0, DateTimeZone.UTC);
        dateTime2 = new DateTime(2000, 1, 1, 23, 50, 10, 456, DateTimeZone.UTC);
        assertThat(dateTime1).isEqualToIgnoringSeconds(dateTime2);

        DateTime utcTime = new DateTime(2013, 6, 10, 0, 0, DateTimeZone.UTC);
        DateTime cestTime = new DateTime(2013, 6, 10, 2, 0, DateTimeZone.forID("Europe/Berlin"));
        assertThat(utcTime).as("in UTC time").isEqualTo(cestTime);
    }

    @Test
    public void LocalDateTest() {
        assertThat(new LocalDate(2000, 1, 1))
                .hasYear(2000)
                .hasMonthOfYear(1)
                .hasDayOfMonth(1);

        LocalDate date1 = new LocalDate(2000, 1, 1);
        LocalDate date2 = new LocalDate(2001, 1, 1);
        assertThat(date1)
                .isBefore(date2)
                .isBeforeOrEqualTo(date2);
        assertThat(date2)
                .isAfter(date1)
                .isAfterOrEqualTo(date1);
    }

    @Test
    public void LocalDateTimeTest() {
        assertThat(new LocalDateTime(2000, 1, 1, 0, 0, 0))
                .hasYear(2000)
                .hasMonthOfYear(1)
                .hasDayOfMonth(1)
                .hasHourOfDay(0)
                .hasMinuteOfHour(0)
                .hasSecondOfMinute(0)
                .hasMillisOfSecond(0);

        LocalDateTime dateTime1 = new LocalDateTime(2000, 1, 1, 0,0, 0);
        LocalDateTime dateTime2 = new LocalDateTime(2001, 1, 1, 0,0, 0);
        assertThat(dateTime1)
                .isBefore(dateTime2)
                .isBeforeOrEqualTo(dateTime2);
        assertThat(dateTime2)
                .isAfter(dateTime1)
                .isAfterOrEqualTo(dateTime1);
    }

    @Test
    public void DateTimeTest() {
        assertThat(new DateTime(2000, 1, 1, 0, 0, 0, 0))
                .hasYear(2000)
                .hasMonthOfYear(1)
                .hasDayOfMonth(1)
                .hasHourOfDay(0)
                .hasMinuteOfHour(0)
                .hasSecondOfMinute(0)
                .hasMillisOfSecond(0);
        DateTime dateTime1 = new DateTime(2000, 1, 1, 0,0, 0);
        DateTime dateTime2 = new DateTime(2001, 1, 1, 0,0, 0);
        assertThat(dateTime1)
                .isBefore(dateTime2)
                .isBeforeOrEqualTo(dateTime2);
        assertThat(dateTime2)
                .isAfter(dateTime1)
                .isAfterOrEqualTo(dateTime1);
    }
}
