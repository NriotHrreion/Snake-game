package net.nocpiun.snake.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	public static void log(String content) {
		Date date = new Date();
		System.out.println("["+ new SimpleDateFormat("HH:mm:ss").format(date) +"][Log] "+ content);
	}
}
