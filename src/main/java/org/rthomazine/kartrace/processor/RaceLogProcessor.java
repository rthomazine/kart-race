package org.rthomazine.kartrace.processor;

import org.rthomazine.kartrace.model.RaceLogEntry;

import java.util.List;

public interface RaceLogProcessor {
    public List<RaceLogEntry> processRaceLog(List<RaceLogEntry> raceLog);
}
