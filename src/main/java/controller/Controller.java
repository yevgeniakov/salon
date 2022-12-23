package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import controller.command.CommandContainer;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(Controller.class);

	public Controller() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.trace("doGet");

		String address = "error.jsp";
		String commandName = request.getParameter("command");
		logger.trace("commandName ==> " + commandName);
		Command command = CommandContainer.getCommand(commandName);
		try {
			address = command.execute(request, response);
			logger.trace("have received address" + address + " from command " + commandName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", "Unable to do action. Try again.");
		}
		if (request.getAttribute("redirect") != null) {
			logger.trace("sending redirect in controller");
			response.sendRedirect(address);
		} else {
			logger.trace("sending forward in controller");
			ServletContext servletContext = getServletContext();
			RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(address);
			requestDispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.trace("doPost");
		String address = "/error.jsp";
		String commandName = request.getParameter("command");
		logger.trace("commandName ==> " + commandName);
		Command command = CommandContainer.getCommand(commandName);
		try {
			address = command.execute(request, response);
			logger.trace("have received address" + address + " from command " + commandName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", "Unable to do action. Try again.");
		}
		if (request.getAttribute("redirect") != null) {
			logger.trace("sending redirect in controller");
			response.sendRedirect(address);
		} else {
			logger.trace("sending forward in controller");
			ServletContext servletContext = getServletContext();
			RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(address);
			requestDispatcher.forward(request, response);
		}
	}
}
