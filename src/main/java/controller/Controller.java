package controller;



import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.command.Command;
import controller.command.CommandContainer;


/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Controller() {
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		String address = "error.jsp";
		String commandName = request.getParameter("command");
		System.out.println("commandName ==> " + commandName);

		Command command = CommandContainer.getCommand(commandName);
		System.out.println("#doGet obtain command" + command.toString());
		try {
			address = command.execute(request, response);
			System.out.println("have received address" + address + " from command " + commandName);
		} catch (Exception ex) {
			System.out.println("have catched exeption while executing command " + commandName);
			System.out.println("");
			request.setAttribute("error", "command doesnt execute");
			System.out.println(ex.getMessage());
		}

		System.out.println("address ==> " + address);
		
		ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(address);
        requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String address = "error.jsp";
		String commandName = request.getParameter("command");
		System.out.println("commandName ==> " + commandName);

		Command command = CommandContainer.getCommand(commandName);
		try {
			address = command.execute(request, response);
			System.out.println("have received address" + address + " from command " + commandName);
		} catch (Exception ex) {
			System.out.println("have catched exeption while executing command " + commandName);
			System.out.println("");
			request.setAttribute("error", "command doesnt execute");
			System.out.println(ex.getMessage());
		}

		System.out.println("address ==> " + address);
		
		if (request.getAttribute("error") == null) {
			System.out.println("sending redirect in controller");
			response.sendRedirect(address);
		} else {
			System.out.println("sending forward in controller");
			ServletContext servletContext = getServletContext();
	        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(address);
	        requestDispatcher.forward(request, response);	
		
		}
	}

}
