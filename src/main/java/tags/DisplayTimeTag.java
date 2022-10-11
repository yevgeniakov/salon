package tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DisplayTimeTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(FreeSlotsListTag.class);
	private int timeslot;
	private String currentLang;

	public void setTimeslot(int timeslot) {
		this.timeslot = timeslot;
	}
	
	public void setCurrentLang(String currentLang) {
		this.currentLang = currentLang;
	}

	@Override
	public int doStartTag() throws JspException {
		logger.trace("enter");

		
		try {
			if ("ua".equals(currentLang)) {
				pageContext.getOut().write(timeslot + ": 00");
			} else {
				pageContext.getOut().write(timeslot <= 12 ? timeslot + " AM" : (timeslot - 12) + " PM");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return SKIP_BODY;
	}
}