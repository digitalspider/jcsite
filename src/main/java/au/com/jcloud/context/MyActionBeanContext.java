package au.com.jcloud.context;

import au.com.jcloud.model.User;
import net.sourceforge.stripes.action.ActionBeanContext;

public class MyActionBeanContext extends ActionBeanContext {
    public void setUser(User user) {
        getRequest().getSession().setAttribute("user", user);
    }
 
    public User getUser() {
        return (User) getRequest().getSession().getAttribute("user");
    }
}
