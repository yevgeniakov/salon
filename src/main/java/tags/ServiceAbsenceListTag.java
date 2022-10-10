package tags;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import entity.Service;
import service.ServiceManager;

public class ServiceAbsenceListTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int master_id;
	

	public void setMaster_id(int master_id) {
		this.master_id = master_id;
	}

	

	@Override
	public int doStartTag() throws JspException {
		
		System.out.println("hello from tag 2");
		ServiceManager manager = ServiceManager.getInstance();
		
			System.out.println(master_id);
			
			
			if (master_id != 0) {
			List<Service> serviceList = new ArrayList<>();
			try {
				serviceList = manager.findAllServicesAbsentByMaster(master_id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(serviceList);
			pageContext.setAttribute("serviceabsentlist", serviceList);
		}
		
		return SKIP_BODY;
	}

}