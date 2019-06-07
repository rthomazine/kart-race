package org.rthomazine.kartrace.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalTime;

@Data
public class RaceLogEntry {
    private LocalTime time;
    private String racer;
    private Integer turn;
    private Duration turnDuration;
    private Float speedAverage;
}
