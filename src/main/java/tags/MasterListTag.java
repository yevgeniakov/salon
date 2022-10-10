package tags;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import entity.User;
import service.UserManager;

public class MasterListTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {

		System.out.println("hello from masterlist tag");
		UserManager manager = UserManager.getInstance();

		List<User> masterList = new ArrayList<>();
		try {
			masterList = manager.findAllMasters();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageContext.setAttribute("masterlist", masterList);

		return SKIP_BODY;
	}

}