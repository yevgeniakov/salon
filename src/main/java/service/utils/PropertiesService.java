package service.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AppointmentManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public class PropertiesService {
	private static final Logger logger = LogManager.getLogger(PropertiesService.class);
	public static String getProperty(String key) {
		String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
		String appConfigPath = rootPath + "application.properties";
		
		Properties appProps = new Properties();
		try {
			appProps.load(Files.newInputStream(Paths.get(appConfigPath)));
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return appProps.getProperty(key);
		
	}
}
