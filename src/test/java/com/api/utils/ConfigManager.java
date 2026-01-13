package com.api.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	private static Properties prop = new Properties();
	private static String path = "config/config.qa.properties";
	private static String env;

	private ConfigManager() {

	}

	static {

		env = System.getProperty("env", "qa");
		env = env.toLowerCase().trim();

		System.out.println("Running Tests in Env: " + env);

		switch (env) {

		case "dev" -> path = "config/config.dev.properties";

		case "qa" -> path = "config/config.qa.properties";

		case "uat" -> path = "config/config.uat.properties";

		default -> path = "config/config.qa.properties";
		}

		InputStream input = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("config/config.properties");

		if (input == null) {
			throw new RuntimeException("Cannot find the file at the path " + path);
		}

		try {
			prop.load(input);
		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public static String getProperty(String key) {

		return prop.getProperty(key);

	}
}
