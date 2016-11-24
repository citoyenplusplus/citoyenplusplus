package org.citpp.test.app;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication(scanBasePackages = { "org.citpp" })
public class TestApplication {

	private static final Logger LOG = LoggerFactory.getLogger(TestApplication.class);

	@Resource
	private Environment env;

	/**
	 * Main method, used to run the application.
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TestApplication.class);
		app.setBannerMode(Mode.CONSOLE);
		app.run(args);
	}

	@PostConstruct
	public void initApplication() throws IOException {
		if (env.getActiveProfiles().length == 0) {
			LOG.warn("No Spring profile configured, running with default configuration");
		} else {
			LOG.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
		}
	}

}
