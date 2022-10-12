package controller.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entity.Role;
import entity.User;

@WebFilter("/*")
public class AccessCheckFilter implements Filter{
	private HttpServletRequest httpRequest;
	private static final Logger logger = LogManager.getLogger(AccessCheckFilter.class); 
   
    private static final String[] ClientAllowedURLs = {
            "/header.jsp", "/client_page.jsp", "/error.jsp", "/my_info.jsp", "/master_list.jsp", 
            "/master_schedule.jsp", "/create_appointment.jsp", "/leave_feedback.jsp", 
            "/appointments_list.jsp", "/appointment_info.jsp", "/Controller", "css", "js"
    };
    private static final String[] MasterAllowedURLs = {
    		"/header.jsp", "/master_page.jsp", "/error.jsp", "/my_info.jsp", "/master_schedule", 
            "/appointments_list.jsp", "/appointment_info.jsp", "/Controller", "css", "js"
    };
    private static final String[] GuestAllowedURLs = {
    		"/header.jsp", "/index.jsp", "/registration.jsp", "/login.jsp", "/error.jsp", 
            "/master_list.jsp", "/master_info.jsp", "/Controller", "css", "js"
    };
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.trace("init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.trace("doFilter enter");

		httpRequest = (HttpServletRequest) request;
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        HttpSession session = httpRequest.getSession(false);
        
        User loggedUser = null;
        if (session != null) {
        	loggedUser = (User)session.getAttribute("user");
        }
        
        boolean isAdmin = (session != null && loggedUser != null && loggedUser.getRole() == Role.ADMIN);
        boolean isClient = (session != null && loggedUser != null && loggedUser.getRole() == Role.CLIENT);
        boolean isMaster = (session != null && loggedUser != null && loggedUser.getRole() == Role.HAIRDRESSER);
        boolean isGuest = (!isAdmin && !isClient && !isMaster);
       
        logger.trace("isAdmin = " + isAdmin + ", isClient = " + isClient + ", isMaster = " + isMaster + ", isGuest = " + isGuest);
        String destinationPage = path;
        
        if (isAdmin) destinationPage = "admin_page.jsp";
        if (isClient) destinationPage = "client_page.jsp";
        if (isMaster) destinationPage = "master_page.jsp";
        if (isGuest) destinationPage = "index.jsp";
        
        if (isAdmin && !isAdminAllowed() || isClient && !isClientAllowed() || isMaster && !isMasterAllowed() || isGuest && !isGuestAllowed() ) {
        	logger.trace("don't allowed " + httpRequest.getRequestURI() + ", redirecting to " + destinationPage);
         	HttpServletResponse httpResponse = (HttpServletResponse) response;
        	httpResponse.sendRedirect(destinationPage);
        	
        } else {
        
        logger.trace("allowed");
        chain.doFilter(request, response);
	}
        }

	@Override
	public void destroy() {
		logger.trace("destroy");
	}
	
	private boolean isClientAllowed() {
		String requestURL = httpRequest.getRequestURL().toString();

		for (String str : ClientAllowedURLs) {
			if (requestURL.contains(str)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isMasterAllowed() {
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

		for (String str : MasterAllowedURLs) {
			if (path.contains(str)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isGuestAllowed() {
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

		for (String str : GuestAllowedURLs) {
			if (path.contains(str)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isAdminAllowed() {
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		if (path.contains("/index") || path.contains("/master_page") || path.contains("/client_page")) {
			return false;
		}
		return true;
	}

}
