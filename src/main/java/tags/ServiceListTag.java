package tags;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import entity.Service;
import service.ServiceManager;

public class ServiceListTag extends TagSupport {

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
		
		System.out.println("hello from tag");
		ServiceManager manager = ServiceManager.getInstance();
		if (master_id == 0) {
			List<Service> serviceList = new ArrayList<>();
			try {
				serviceList = manager.findAllservices();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pageContext.setAttribute("servicelist", serviceList);
			
		} else {
			TreeMap<Service, Integer> serviceMap = new TreeMap<>();
			try {
				serviceMap = manager.findAllServicesByMaster(master_id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pageContext.setAttribute("servicemap", serviceMap);
			
		}
		
		return SKIP_BODY;
	}

}