package tags;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entity.Service;
import service.ServiceManager;

/**
 * Tag for receiving services, that are not provided via certain master
 * 
 * @author yevgenia.kovalova
 *
 */

public class ServiceAbsenceListTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(ServiceAbsenceListTag.class);
	private int master_id;

	public void setMaster_id(int master_id) {
		this.master_id = master_id;
	}

	@Override
	public int doStartTag() throws JspException {
		logger.trace("enter");

		ServiceManager manager = ServiceManager.getInstance();
		if (master_id != 0) {
			List<Service> serviceList = new ArrayList<>();
			try {
				serviceList = manager.findAllServicesAbsentByMaster(master_id);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			pageContext.setAttribute("serviceabsentlist", serviceList);
		}
		return SKIP_BODY;
	}
}