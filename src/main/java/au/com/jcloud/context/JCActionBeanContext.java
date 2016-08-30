package au.com.jcloud.context;

import au.com.jcloud.util.Constants;
import au.com.jcloud.model.User;
import net.sourceforge.stripes.action.ActionBeanContext;

public class JCActionBeanContext extends ActionBeanContext {
	public void setUser(User user) {
		getRequest().getSession().setAttribute(Constants.SESSION_ATTRIBUTE_USER, user);
		getRequest().getSession().setAttribute(Constants.SESSION_ATTRIBUTE_ROLES, user.getRoles());
	}

	public User getUser() {
		return (User) getRequest().getSession().getAttribute(Constants.SESSION_ATTRIBUTE_USER);
	}
}
