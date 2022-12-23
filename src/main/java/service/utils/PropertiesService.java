package service.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * Tool for working with Properties
 * 
 * @author yevgenia.kovalova
 *
 */

public class PropertiesService {
	private static final Logger logger = LogManager.getLogger(PropertiesService.class);

	public static String getProperty(String key) {
		String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(""))
				.getPath();
		Properties appProps = new Properties();
		String appConfigPath = rootPath + "application.properties";
		try {
			appProps.load(new FileInputStream(appConfigPath));
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return appProps.getProperty(key);
	}
}
