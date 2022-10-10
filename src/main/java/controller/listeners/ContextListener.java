package controller.listeners;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    	logger.info("contextInitialized");
    	
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new EmailSender(), 0, 1, TimeUnit.DAYS);
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    	logger.info("contextDestroyed");
        scheduler.shutdownNow();
    }

}