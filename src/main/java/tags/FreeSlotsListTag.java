package tags;

import java.time.LocalDate;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entity.User;
import service.AppointmentManager;
import service.UserManager;

public class FreeSlotsListTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(FreeSlotsListTag.class);
	private int master_id;
	private String date;

	public void setMaster_id(int master_id) {
		this.master_id = master_id;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int doStartTag() throws JspException {
		logger.info("enter");

		UserManager userManager = UserManager.getInstance();
		AppointmentManager appointmentManager = AppointmentManager.getInstance();
		User master = null;
		List<Integer> freeSlots = null;
		try {
			LocalDate dateL = LocalDate.parse(date);
			master = userManager.findUserbyID(master_id);
			freeSlots = appointmentManager.getMasterFreeSlots(dateL, master);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		pageContext.setAttribute("freetimeslots", freeSlots);
		return SKIP_BODY;
	}
}