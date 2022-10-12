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
				
				if (timeslot == 0) pageContext.getOut().write("12 a.m.");
				if (timeslot == 12) pageContext.getOut().write("12 p.m.");
				if (timeslot > 0 && timeslot < 12) pageContext.getOut().write(timeslot + " a.m.");
				if (timeslot > 12 && timeslot < 23) pageContext.getOut().write(timeslot - 12  + " p.m.");
			
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return SKIP_BODY;
	}
}