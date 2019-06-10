package org.rthomazine.kartrace.reader;

import org.junit.Test;
import org.rthomazine.kartrace.model.RaceLogEntry;

import java.time.Duration;
import java.time.LocalTime;
import java.util.regex.Matcher;

import static org.junit.Assert.*;

public class RaceLogReaderImplTest {

    private static final String lineWithTabs = "23:53:06.741      015 – F.ALONSO                          4\t\t1:20.050\t\t\t34,763";
    private static final String lineWithoutTabs = "23:50:15.057      002 – K.RAIKKONEN                       2             1:03.982                        43,493";
    private static final String lineWithTwoDigitsSpeedAverage = "23:49:30.976      015 – F.ALONSO                          1\t\t1:18.456\t\t\t35,47";
    private static final String invalidLineWithTabs = "23:53:06741      015 – F.ALONSO                          4\t\t1:20.050\t\t\t34,763";
    private static final String invalidLineWithoutTabs = "23:50:15.057      002  K.RAIKKONEN                       2             1:03.982                        43,493";
    private static final String invalidLineWithTwoDigitsSpeedAverage = "23:49:30.976      015 – F.ALONSO                          \t\t1:18.456\t\t\t35,47";

    @Test
    public void testRaceLogLinePatternInvalidLineWithTabs() {
        Matcher matcher = RaceLogReaderImpl.RACE_LOG_LINE_PATTERN.matcher(invalidLineWithTabs);
        assertFalse(matcher.matches());
    }

    @Test
    public void testRaceLogLinePatternInvalidLineWithoutTabs() {
        Matcher matcher = RaceLogReaderImpl.RACE_LOG_LINE_PATTERN.matcher(invalidLineWithoutTabs);
        assertFalse(matcher.matches());
    }

    @Test
    public void testRaceLogLinePatternInvalidLineWithTwoDigitsSpeedAverage() {
        Matcher matcher = RaceLogReaderImpl.RACE_LOG_LINE_PATTERN.matcher(invalidLineWithTwoDigitsSpeedAverage);
        assertFalse(matcher.matches());
    }

    @Test
    public void testRaceLogLinePatternLineWithTabs() {
        Matcher matcher = RaceLogReaderImpl.RACE_LOG_LINE_PATTERN.matcher(lineWithTabs);
        assertTrue(matcher.matches());
        assertTrue(matcher.group(1).trim().equals("23:53:06.741"));
        assertTrue(matcher.group(2).trim().equals("015 –"));
        assertTrue(matcher.group(3).trim().equals("F.ALONSO"));
        assertTrue(matcher.group(4).trim().equals("4"));
        assertTrue(matcher.group(5).trim().equals("1:20.050"));
        assertTrue(matcher.group(6).trim().equals("34,763"));
    }

    @Test
    public void testRaceLogLinePatternLineWithoutTabs() {
        Matcher matcher = RaceLogReaderImpl.RACE_LOG_LINE_PATTERN.matcher(lineWithoutTabs);
        assertTrue(matcher.matches());
        assertTrue(matcher.group(1).trim().equals("23:50:15.057"));
        assertTrue(matcher.group(2).trim().equals("002 –"));
        assertTrue(matcher.group(3).trim().equals("K.RAIKKONEN"));
        assertTrue(matcher.group(4).trim().equals("2"));
        assertTrue(matcher.group(5).trim().equals("1:03.982"));
        assertTrue(matcher.group(6).trim().equals("43,493"));
    }

    @Test
    public void testRaceLogLinePatternLineWithTwoDigitsSpeedAverage() {
        Matcher matcher = RaceLogReaderImpl.RACE_LOG_LINE_PATTERN.matcher(lineWithTwoDigitsSpeedAverage);
        assertTrue(matcher.matches());
        assertTrue(matcher.group(1).trim().equals("23:49:30.976"));
        assertTrue(matcher.group(2).trim().equals("015 –"));
        assertTrue(matcher.group(3).trim().equals("F.ALONSO"));
        assertTrue(matcher.group(4).trim().equals("1"));
        assertTrue(matcher.group(5).trim().equals("1:18.456"));
        assertTrue(matcher.group(6).trim().equals("35,47"));
    }

    @Test
    public void testTurnDurationPattern() {
        Matcher matcher = RaceLogReaderImpl.TURN_DURATION_PATTERN.matcher("1:03.982");
        assertTrue(matcher.matches());
        assertTrue(matcher.group(1).equals("1"));
        assertTrue(matcher.group(2).equals("03"));
        assertTrue(matcher.group(3).equals("982"));
    }

    @Test
    public void testParseTurnDuration() {
        long duration = 1 * 60000 + 3 * 1000 + 982;
        long parsedDuration = RaceLogReaderImpl.parseTurnDuration("1:03.982");
        assertTrue(duration == parsedDuration);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParceRaceLogInvalidLineWithTabs() {
        RaceLogReaderImpl.parceRaceLogLine(invalidLineWithTabs);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParceRaceLogInvalidLineWithoutTabs() {
        RaceLogReaderImpl.parceRaceLogLine(invalidLineWithoutTabs);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParceRaceLogInvalidLineWithTwoDigitsSpeedAverage() {
        RaceLogReaderImpl.parceRaceLogLine(invalidLineWithTwoDigitsSpeedAverage);
    }

    @Test
    public void testParceRaceLogLineWithTabs() {
        RaceLogEntry logEntry = RaceLogReaderImpl.parceRaceLogLine(lineWithTabs);
        assertNotNull(logEntry);
        long duration = 1 * 60000 + 20 * 1000 + 50;
        assertTrue(logEntry.getTime().equals(LocalTime.of(23,53,6,741000000)));
        assertTrue(logEntry.getDriverCode().equals("015"));
        assertTrue(logEntry.getDriver().equals("F.ALONSO"));
        assertTrue(logEntry.getTurn().intValue() == 4);
        assertTrue(logEntry.getTurnDuration().equals(Duration.ofMillis(duration)));
        assertTrue(logEntry.getSpeedAverage().equals(34.763F));
    }

    @Test
    public void testParceRaceLogLineWithoutTabs() {
        RaceLogEntry logEntry = RaceLogReaderImpl.parceRaceLogLine(lineWithoutTabs);
        assertNotNull(logEntry);
        long duration = 1 * 60000 + 3 * 1000 + 982;
        assertTrue(logEntry.getTime().equals(LocalTime.of(23,50,15,57000000)));
        assertTrue(logEntry.getDriverCode().equals("002"));
        assertTrue(logEntry.getDriver().equals("K.RAIKKONEN"));
        assertTrue(logEntry.getTurn().intValue() == 2);
        assertTrue(logEntry.getTurnDuration().equals(Duration.ofMillis(duration)));
        assertTrue(logEntry.getSpeedAverage().equals(43.493F));
    }

    @Test
    public void testParceRaceLogLineWithTwoDigitsSpeedAverage() {
        RaceLogEntry logEntry = RaceLogReaderImpl.parceRaceLogLine(lineWithTwoDigitsSpeedAverage);
        assertNotNull(logEntry);
        long duration = 1 * 60000 + 18 * 1000 + 456;
        assertTrue(logEntry.getTime().equals(LocalTime.of(23,49,30,976000000)));
        assertTrue(logEntry.getDriverCode().equals("015"));
        assertTrue(logEntry.getDriver().equals("F.ALONSO"));
        assertTrue(logEntry.getTurn().intValue() == 1);
        assertTrue(logEntry.getTurnDuration().equals(Duration.ofMillis(duration)));
        assertTrue(logEntry.getSpeedAverage().equals(35.47F));
    }

}