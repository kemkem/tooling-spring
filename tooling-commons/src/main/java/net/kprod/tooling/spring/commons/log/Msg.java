package net.kprod.tooling.spring.commons.log;

import org.slf4j.helpers.MessageFormatter;

/**
 * Message formatter class
 * This is useful to create messages with various parameters
 * Works as a Logger formatter
 */
public class Msg {
	private Msg() {
	}

	/**
	 * Return a message formatted according pattern and parameters
	 * Each parameter is defined with a '{}' within the pattern
	 * @param pattern message pattern
	 * @param objects variable message parameters
	 * @return formatted message
	 */
	public static String format(String pattern, Object... objects) {
		return MessageFormatter.arrayFormat(pattern, objects).getMessage();
	}
}
