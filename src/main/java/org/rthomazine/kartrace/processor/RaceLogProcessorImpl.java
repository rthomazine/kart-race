package org.rthomazine.kartrace.processor;

import org.rthomazine.kartrace.model.RaceLogEntry;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RaceLogProcessorImpl implements RaceLogProcessor {

    @Override
    public List<RaceLogEntry> processRaceLog(List<RaceLogEntry> raceLog) {
        Map<String, RaceLogEntry> raceResult = new LinkedHashMap<String, RaceLogEntry>();
        raceLog.forEach(log -> {
            if (raceResult.containsKey(log.getDriverCode())) {
                RaceLogEntry entry = raceResult.remove(log.getDriverCode());
                log.setTurnDuration(log.getTurnDuration().plus(entry.getTurnDuration()));
                log.setSpeedAverage(log.getSpeedAverage() + entry.getSpeedAverage());
            }
            raceResult.put(log.getDriverCode(), log);
        });
        return raceResult.values().stream()
                .sorted(Comparator.comparing(RaceLogEntry::getTurn).reversed()
                        .thenComparing(RaceLogEntry::getTurnDuration))
                .collect(Collectors.toList());
    }

}
