package org.rthomazine.kartrace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class KartRaceApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(KartRaceApplication.class);

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.bannerMode(Banner.Mode.OFF)
				.sources(KartRaceApplication.class)
				.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length == 0) {
			LOG.info("Initializing with sample kart race payload");
		} else if (args.length == 1) {
			LOG.info("Initializing with given kart race payload: %s", args[0]);
		} else {
			LOG.warn("Usage error");
		}
	}

}
