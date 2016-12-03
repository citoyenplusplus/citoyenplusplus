package org.citpp.parser.json.impl;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrenchReunionsJSONCleanerImpl extends AbstractStandardJSONCleaner {
	private final static Logger LOG = LoggerFactory.getLogger(FrenchReunionsJSONCleanerImpl.class);

	private final static Pattern PATTERN = Pattern.compile("(.*)(T(\\d{2}))([^:].*)(\\.)((\\d{1,3})\\+\\d+:\\d+)");

	private final JSONPathValues[] dateFormat = new JSONPathValues[] { new JSONPathValues("", "timeStampFin"),
			new JSONPathValues("cycleDeVie.chrono", "creation", "cloture") };

	@Override
	protected JSONPathValues[] getStringToIntValues() {
		return null;
	}

	@Override
	protected JSONPathValues[] getStringToNullValues() {
		return null;
	}

	@Override
	protected void addSpecificTranforms(Map<String, Object> rootMap) {
		this.transform(rootMap, (map, key) -> {
			if (map.get(key) instanceof String) {
				String input = (String) map.get(key);
				input = input.replace("Z", "+01:00");
				Matcher m = PATTERN.matcher(input);
				if (m.matches()) {
					String t = "T";
					if (m.group(4).indexOf(":") == 1) {
						t = "T0";
					}
					input = m.group(1).concat(t)
							.concat(m.group(4).concat(":").concat(m.group(3)).concat(m.group(5)).concat(m.group(6)));
				}
				boolean replaced = false;
				if (input.contains("T24:")) {
					input = input.replace("T24:", "T23:");
					replaced = true;
				}
				try {
					ZonedDateTime time = ZonedDateTime.parse(input);
					if (replaced) {
						time = time.plus(1, ChronoUnit.HOURS);
					}
					map.put(key, time.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
				} catch (DateTimeParseException e) {

					LOG.error("{}", e);
				}
			}
		}, dateFormat);
	}

}
