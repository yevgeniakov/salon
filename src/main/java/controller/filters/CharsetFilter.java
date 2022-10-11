package controller.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebFilter("/*")
public class CharsetFilter implements Filter {
	private static final Logger logger = LogManager.getLogger(CharsetFilter.class);     
    
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.trace("init");
	}

    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    	       
    	String encoding = request.getCharacterEncoding();
    	
		if (encoding == null) {
			request.setCharacterEncoding("UTF-8");
		}
		chain.doFilter(request, response);
    }

    public void destroy() {
    }


}