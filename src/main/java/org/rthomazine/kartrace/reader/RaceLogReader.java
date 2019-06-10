package org.rthomazine.kartrace.reader;

import org.rthomazine.kartrace.model.RaceLogEntry;

import java.util.List;

public interface RaceLogReader {

    public List<RaceLogEntry> readRaceLog(String filename);

}
