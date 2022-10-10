package controller.command.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import entity.Service;
import service.ServiceManager;

public class ShowServiceListCommand implements Command {
	private static final Logger logger = LogManager.getLogger(ShowServiceListCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.info("execute");

		ServiceManager manager = ServiceManager.getInstance();
		List<Service> services = new ArrayList<>();

		try {
			services = manager.findAllservices();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", "unable to get service list");
			return "/error.jsp";
		}

		request.setAttribute("servicelist", services);
		return "/service_list.jsp";
	}

}
