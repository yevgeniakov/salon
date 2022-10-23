package controller.listeners;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.utils.EmailSender;

@WebListener
public class ContextListener implements ServletContextListener {
	private static final Logger logger = LogManager.getLogger(ContextListener.class);

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
    	

    	ServletContext context = event.getServletContext();
    	String localesFileName = context.getInitParameter("locales");
    	String localesFileRealPath = context.getRealPath(localesFileName);

    	Properties locales = new Properties();
    	try {
			locales.load(Files.newInputStream(Paths.get(localesFileRealPath)));
		} catch (IOException e) {
			logger.error("can't load locales properties");
		}
    	
    	context.setAttribute("locales", locales);
    	locales.list(System.out);

    	logger.trace("contextInitialized");
    	
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new EmailSender(), 0, 1, TimeUnit.DAYS);
        
        logger.info("set scheduler for email reminders");
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    	logger.trace("contextDestroyed");
        scheduler.shutdownNow();
    }

}