package tags;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entity.Service;
import service.ServiceManager;

/**
 * Tag for receiving a list of services, provided by certain master
 * 
 * @author yevgenia.kovalova
 *
 */

public class ServiceListTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(ServiceListTag.class);
	private int master_id;

	public void setMaster_id(int master_id) {
		this.master_id = master_id;
	}

	@Override
	public int doStartTag() throws JspException {
		logger.trace("enter");

		ServiceManager manager = ServiceManager.getInstance();
		if (master_id == 0) {
			List<Service> serviceList = new ArrayList<>();
			try {
				serviceList = manager.findAllservices();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			pageContext.setAttribute("servicelist", serviceList);

		} else {
			TreeMap<Service, Integer> serviceMap = new TreeMap<>();
			try {
				serviceMap = manager.findAllServicesByMaster(master_id);
			} catch (Exception e) {
				logger.error(e.getMessage(), e, master_id);
			}
			pageContext.setAttribute("servicemap", serviceMap);
		}
		return SKIP_BODY;
	}
}