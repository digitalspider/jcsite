package au.com.jcloud.context;

import net.sourceforge.stripes.action.ActionBeanContext;

import au.com.jcloud.emodel.User;

public class JCActionBeanContext extends ActionBeanContext {
    public void setUser(User user) {
        getRequest().getSession().setAttribute("user", user);
    }
 
    public User getUser() {
        return (User) getRequest().getSession().getAttribute("user");
    }
}
