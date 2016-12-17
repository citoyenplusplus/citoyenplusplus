package org.citpp.parser.json;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public enum JSONTransformerType {

	STRING_TO_NULL((map, key) -> {
		if (map.get(key) instanceof String) {
			map.put(key, null);
		}
	}), STRING_TO_INTEGER((map, key) -> {
		Object object = map.get(key);
		if (object instanceof String) {
			map.put(key, Long.parseLong((String) object));
		} else if (object instanceof String[]) {
			List<Long> numbers = new ArrayList<>();
			for (String value : (String[]) object) {
				numbers.add(Long.parseLong(value));
			}
			map.put(key, numbers.toArray(new Long[numbers.size()]));
		}
	}), STRING_TO_FLOAT((map, key) -> {
		Object object = map.get(key);

		if (object instanceof String) {
			try {
				map.put(key, NumberFormat.getInstance(Locale.FRANCE).parse((String) object).doubleValue());
			} catch (ParseException e) {
				map.put(key, Double.parseDouble((String) object));
			}
		} else if (object instanceof String[]) {
			List<Double> numbers = new ArrayList<>();
			for (String value : (String[]) object) {
				try {
					numbers.add(NumberFormat.getInstance(Locale.FRANCE).parse(value).doubleValue());
				} catch (ParseException e) {
					numbers.add(Double.parseDouble(value));
				}
			}
			map.put(key, numbers.toArray(new Double[numbers.size()]));
		}
	});

	private final JSONTransformer transformer;

	private JSONTransformerType(JSONTransformer transformer) {
		this.transformer = transformer;
	}

	public JSONTransformer getTransformer() {
		return transformer;
	}

}
