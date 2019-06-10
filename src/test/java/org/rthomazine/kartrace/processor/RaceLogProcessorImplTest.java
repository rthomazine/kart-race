package org.rthomazine.kartrace.processor;

import org.junit.BeforeClass;
import org.junit.Test;
import org.rthomazine.kartrace.model.RaceLogEntry;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class RaceLogProcessorImplTest {

    private static List raceLogTwoDriversFullRace;
    private static List raceLogTwoDriversPartialRace;

    @BeforeClass
    public static void setup() {
        raceLogTwoDriversFullRace = Arrays.asList(
                new RaceLogEntry()
                        .setDriverCode("023")
                        .setDriver("M.WEBBER")
                        .setTime(LocalTime.of(23, 49, 12, 667000000))
                        .setTurn(1)
                        .setTurnDuration(Duration.ofMillis(1 * 60000 + 4 * 1000 + 414))
                        .setSpeedAverage(43.202F),
                new RaceLogEntry()
                        .setDriverCode("023")
                        .setDriver("M.WEBBER")
                        .setTime(LocalTime.of(23, 50, 17, 472000000))
                        .setTurn(2)
                        .setTurnDuration(Duration.ofMillis(1 * 60000 + 4 * 1000 + 805))
                        .setSpeedAverage(42.941F),
                new RaceLogEntry()
                        .setDriverCode("038")
                        .setDriver("F.MASSA")
                        .setTime(LocalTime.of(23, 49, 8, 277000000))
                        .setTurn(1)
                        .setTurnDuration(Duration.ofMillis(1 * 60000 + 2 * 1000 + 852))
                        .setSpeedAverage(44.275F),
                new RaceLogEntry()
                        .setDriverCode("038")
                        .setDriver("F.MASSA")
                        .setTime(LocalTime.of(23, 50, 11, 447000000))
                        .setTurn(2)
                        .setTurnDuration(Duration.ofMillis(1 * 60000 + 3 * 1000 + 170))
                        .setSpeedAverage(44.053F));
        raceLogTwoDriversPartialRace = Arrays.asList(
                new RaceLogEntry()
                        .setDriverCode("023")
                        .setDriver("M.WEBBER")
                        .setTime(LocalTime.of(23, 49, 12, 667000000))
                        .setTurn(1)
                        .setTurnDuration(Duration.ofMillis(1 * 60000 + 4 * 1000 + 414))
                        .setSpeedAverage(43.202F),
                new RaceLogEntry()
                        .setDriverCode("023")
                        .setDriver("M.WEBBER")
                        .setTime(LocalTime.of(23, 50, 17, 472000000))
                        .setTurn(2)
                        .setTurnDuration(Duration.ofMillis(1 * 60000 + 4 * 1000 + 805))
                        .setSpeedAverage(42.941F),
                new RaceLogEntry()
                        .setDriverCode("038")
                        .setDriver("F.MASSA")
                        .setTime(LocalTime.of(23, 49, 8, 277000000))
                        .setTurn(1)
                        .setTurnDuration(Duration.ofMillis(1 * 60000 + 2 * 1000 + 852))
                        .setSpeedAverage(44.275F));
    }

    @Test
    public void testProcessRaceLogFullRace() {
        RaceLogProcessor processor = new RaceLogProcessorImpl();
        List<RaceLogEntry> result = processor.processRaceLog(raceLogTwoDriversFullRace);
        assertNotNull(result);
        assertTrue(result.size() == 2);
        assertTrue(result.get(0).getDriverCode().equals("038"));
        assertTrue(result.get(1).getDriverCode().equals("023"));
    }

    @Test
    public void testProcessRaceLogPartialRace() {
        RaceLogProcessor processor = new RaceLogProcessorImpl();
        List<RaceLogEntry> result = processor.processRaceLog(raceLogTwoDriversPartialRace);
        assertNotNull(result);
        assertTrue(result.size() == 2);
        assertTrue(result.get(0).getDriverCode().equals("023"));
        assertTrue(result.get(1).getDriverCode().equals("038"));
    }

}