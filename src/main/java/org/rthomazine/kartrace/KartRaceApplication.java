package org.rthomazine.kartrace;

public class KartRaceApplication {

	public static void main(String[] args) {
		if (args.length == 1) {
			System.out.println(String.format("Initializing with kart race log file: %s", args[0]));
		} else {
			System.out.println("Usage error: must provide a race log file");
		}
	}

}
