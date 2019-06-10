package org.rthomazine.kartrace;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.rthomazine.kartrace.model.RaceLogEntry;
import org.rthomazine.kartrace.processor.RaceLogProcessorImplTest;
import org.rthomazine.kartrace.reader.RaceLogReader;
import org.rthomazine.kartrace.reader.RaceLogReaderImpl;
import org.rthomazine.kartrace.reader.RaceLogReaderImplTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({RaceLogReaderImplTest.class, RaceLogProcessorImplTest.class})
public class KartRaceTests {
}
