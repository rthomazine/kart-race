package org.rthomazine.kartrace;

import org.rthomazine.kartrace.model.RaceLogEntry;
import org.rthomazine.kartrace.processor.RaceLogProcessor;
import org.rthomazine.kartrace.processor.RaceLogProcessorImpl;
import org.rthomazine.kartrace.reader.RaceLogReader;
import org.rthomazine.kartrace.reader.RaceLogReaderImpl;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class KartRace {

	public static void main(String[] args) {
		if (args.length == 1) {
			System.out.println(String.format("Initializing with kart race log file: %s", args[0]));
			KartRace.run(args[0]);
		} else {
			System.out.println("Usage error: you must provide a race log file to process");
		}
	}

	private static void run(String raceLogFilename) {
		RaceLogReader reader = new RaceLogReaderImpl();
		List<RaceLogEntry> raceLog = reader.readRaceLog(raceLogFilename);

		RaceLogProcessor processor = new RaceLogProcessorImpl();
		List<RaceLogEntry> raceResult = processor.processRaceLog(raceLog);

		AtomicInteger position = new AtomicInteger();
		position.getAndIncrement();
		raceResult.forEach(log -> System.out.println(
				String.format("%s - %s - %s - %s - %s", position.getAndIncrement(), log.getDriverCode(), log.getDriver(),
						log.getTurn(), formatDuration(log.getTurnDuration().toMillis()))));
	}

	private static String formatDuration(long duration) {
		long hours = TimeUnit.MILLISECONDS.toHours(duration)
				- TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
		long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
				- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
				- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
		long miliseconds = TimeUnit.MILLISECONDS.toMillis(duration)
				- TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duration));;
		return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, miliseconds);
	}

}
