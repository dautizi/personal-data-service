package com.danieleautizi.service.personaldata.utility;

import static org.junit.Assert.assertEquals;

import lombok.val;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RunWith(MockitoJUnitRunner.class)
public class DateTimeUtilTest {

    protected static final ZonedDateTime FIXED_TODAY = DateTimeUtil.fixClockAt(ZonedDateTime.of(LocalDate.of(2018, 3, 25), LocalTime.MIN, ZoneOffset.UTC));

    @Test
    public void getYearsDifference() {

        val input = ZonedDateTime.of(LocalDate.of(2013, 2, 21),
                                     LocalTime.of(14, 35, 56),
                                     ZoneOffset.UTC);
        val actual = DateTimeUtil.getYearsDifference(input.toLocalDateTime());

        assertEquals(5, actual);
    }

}
