package org.rthomazine.kartrace.reader;

import org.rthomazine.kartrace.model.RaceLogEntry;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RaceLogReaderImpl implements RaceLogReader {

    protected static final Pattern RACE_LOG_LINE_PATTERN = Pattern
            .compile("(\\d{2}:\\d{2}:\\d{2}.\\d{3}\\s+)(\\d{3}\\s\\W\\s)(\\w.\\w+\\h+\\s+)(\\d{1}\\s+)(\\d{1}:\\d{2}.\\d{3}\\s+)(\\d{2}\\W\\d{2,3})");
    protected static final Pattern TURN_DURATION_PATTERN = Pattern.compile("(\\d{1}):(\\d{2}).(\\d{3})");
    protected static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public List<RaceLogEntry> readRaceLog(String filename) {
        try {
            List<RaceLogEntry> raceLog = new ArrayList<RaceLogEntry>();
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = "";
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                raceLog.add(parceRaceLogLine(line));
            }
            return raceLog;
        } catch (FileNotFoundException e) {
            System.out.println(String.format("The race log file '%s' was not found", filename));
        } catch (IOException e) {
            System.out.println(String.format("There was an error while reading race log file '%s'", filename));
        }
        return null;
    }

    protected static RaceLogEntry parceRaceLogLine(String logLine) {
        Matcher matcher = RACE_LOG_LINE_PATTERN.matcher(logLine);
        if (matcher.matches()) {
            return new RaceLogEntry()
                    .setTime(LocalTime.parse(matcher.group(1).trim(), TIME_FORMATTER))
                    .setDriverCode(matcher.group(2).substring(0,3))
                    .setDriver(matcher.group(3).trim())
                    .setTurn(Integer.parseInt(matcher.group(4).trim()))
                    .setTurnDuration(Duration.ofMillis(parseTurnDuration(matcher.group(5).trim())))
                    .setSpeedAverage(Float.parseFloat(matcher.group(6).trim().replace(',','.')));
        } else {
            throw new IllegalArgumentException("Invalid race log entry format: " + logLine);
        }
    }

    protected static long parseTurnDuration(String str) {
        Matcher matcher = TURN_DURATION_PATTERN.matcher(str);
        if (matcher.matches()) {
            return Long.parseLong(matcher.group(1)) * 60000
                    + Long.parseLong(matcher.group(2)) * 1000
                    + Long.parseLong(matcher.group(3));
        } else {
            throw new IllegalArgumentException("Invalid duration format: " + str);
        }
    }

}
