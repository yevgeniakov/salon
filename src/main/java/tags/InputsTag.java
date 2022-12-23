package tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InputsTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(FreeSlotsListTag.class);
	
	@Override
	public int doStartTag() throws JspException {
		logger.trace("enter");
		return SKIP_BODY;
	}
}