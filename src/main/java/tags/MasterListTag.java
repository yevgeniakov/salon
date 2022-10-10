package tags;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entity.User;
import service.UserManager;

public class MasterListTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(MasterListTag.class);

	@Override
	public int doStartTag() throws JspException {
		logger.info("enter");

		UserManager manager = UserManager.getInstance();
		List<User> masterList = new ArrayList<>();
		try {
			masterList = manager.findAllMasters();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		pageContext.setAttribute("masterlist", masterList);
		return SKIP_BODY;
	}
}