package au.com.jcloud.actionbean;

import java.util.List;

import org.apache.log4j.Logger;

import au.com.jcloud.jcframe.dao.BaseDAO;
import au.com.jcloud.jcframe.util.Statics;
import au.com.jcloud.model.Login;
import au.com.jcloud.model.User;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.JsonResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.RestActionBean;
import net.sourceforge.stripes.action.UrlBinding;

@RestActionBean
@UrlBinding("/login")
public class LoginActionBean implements ActionBean {

	private static final Logger LOG = Logger.getLogger(LoginActionBean.class);
	
	private ActionBeanContext context;
    private String username;
    private String password;
    private User user;
    
    @DefaultHandler
    public Resolution login() throws Exception {
    	BaseDAO dao = Statics.getDaoMap().get(Login.class);
    	int pageNo = 0;
    	boolean exact = true;
    	boolean populateBean = true;
        List<Login> logins = dao.find("username", username, pageNo, exact, populateBean);
        if (!logins.isEmpty()) {
            if (logins.size() > 1) {
                throw new Exception("Too many users with username=" + username);
            }
            Login login = logins.get(0);
            LOG.info("login=" + login);
            if (login.getPassword() != null && login.getPassword().equals(password)) {
            	BaseDAO<Integer,User> userDao = (BaseDAO<Integer,User>) Statics.getDaoMap().get(User.class);
            	user = userDao.get(login.getId(),populateBean);
            	context.getRequest().getSession().setAttribute("user", user);
            } else {
            	throw new Exception("Invalid login for username " + username);
            }
        }
        return new ForwardResolution("index.jsp");
    }
    
    public Resolution post() throws Exception {
    	login();
    	if (user!=null) {
    		return new JsonResolution( user );
    	}
    	return null;
    }
    
	@Override
	public ActionBeanContext getContext() {
		return context;
	}

	@Override
	public void setContext(ActionBeanContext context) {
		this.context = context;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
