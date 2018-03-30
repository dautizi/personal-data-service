package com.danieleautizi.service.personaldata.utility;

import static java.lang.Math.toIntExact;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * DateTime Util. Get and convert date by specific time zone.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtil {

    private static Clock clock = Clock.systemUTC();

    public static Date zonedDateTimeToDate(final ZonedDateTime zonedDateTime) {

        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * Always returns UTC-time as LocalDateTime, independently of the timezone on the server.
     *
     * @return LocalDateTime the date right now
     */
    public static LocalDateTime utcLocalDateTimeNow() {

        return LocalDateTime.ofInstant(Instant.now(clock), ZoneOffset.UTC);
    }

    /**
     * Return a ZonedDateTime with the UTC as offset
     *
     * @return A ZonedDateTime with UTC as offset
     */
    public static ZonedDateTime utcZonedLocalDateTimeNow() {

        return zonedDateTimeNow(ZoneOffset.UTC);
    }

    /**
     * Return a LocalDateTime with the given ZoneOffset
     *
     * @param offset Zone offset
     * @return A LocalDateTime with an offset
     */
    public static ZonedDateTime zonedDateTimeNow(final ZoneOffset offset) {

        return ZonedDateTime.ofInstant(Instant.now(clock), offset);
    }

    /**
     * Helper method to get 'now' and lock the time in place.
     * <p>
     * <b>Only for use in tests!!! Otherwise boom!</b>
     *
     * @return
     */
    public static LocalDateTime getNowAndFixClock() {

        val localDateTime = utcLocalDateTimeNow();
        useFixedClockAt(localDateTime);
        return localDateTime;
    }

    /**
     * Helper method to get 'now' and lock the time in place.
     * <p>
     * <b>Only for use in tests!!! Otherwise boom!</b>
     *
     * @return
     */
    public static ZonedDateTime getUtcZonedNowAndFixClock() {

        val utcZonedDateTime = utcZonedLocalDateTimeNow();
        useFixedClockAt(utcZonedDateTime.toLocalDateTime());
        return utcZonedDateTime;
    }

    /**
     * Helper method to lock the time in place to the given timestamp.
     * <p>
     * <b>Only for use in tests!!! Otherwise boom!</b>
     *
     * @return
     */
    public static ZonedDateTime fixClockAt(final ZonedDateTime timestamp) {

        useFixedClockAt(timestamp.toLocalDateTime());
        return timestamp;
    }

    private static LocalDateTime useFixedClockAt(final LocalDateTime localDateTime) {

        clock = Clock.fixed(localDateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        return localDateTime;
    }

    public static LocalDateTime localDateTimeOrNull(final ZonedDateTime zonedDateTime) {

        return zonedDateTime != null
               ? zonedDateTime.withZoneSameInstant(ZoneOffset.UTC)
                              .toLocalDateTime()
               : null;
    }

    public static int getYearsDifference(final LocalDateTime localDateTime) {

        if (localDateTime == null) {
            return 0;
        }

        val now = utcLocalDateTimeNow();
        return toIntExact(localDateTime.until(now, ChronoUnit.YEARS));
    }
}