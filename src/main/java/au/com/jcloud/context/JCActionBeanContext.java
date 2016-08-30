package au.com.jcloud.context;

import au.com.jcloud.jcframe.servlet.UserRoleFilter;
import au.com.jcloud.model.User;
import net.sourceforge.stripes.action.ActionBeanContext;

public class JCActionBeanContext extends ActionBeanContext {
	public void setUser(User user) {
		getRequest().getSession().setAttribute(UserRoleFilter.SESSION_ATTRIBUTE_USER, user);
	}

	public User getUser() {
		return (User) getRequest().getSession().getAttribute(UserRoleFilter.SESSION_ATTRIBUTE_USER);
	}
}
