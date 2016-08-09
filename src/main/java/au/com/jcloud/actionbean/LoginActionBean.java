package au.com.jcloud.actionbean;

import com.avaje.ebean.Ebean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import au.com.jcloud.context.JCActionBeanContext;
import au.com.jcloud.emodel.User;
import au.com.jcloud.service.UserService;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.RestActionBean;
import net.sourceforge.stripes.action.UrlBinding;

import javax.servlet.http.HttpServletRequest;

@RestActionBean
@UrlBinding("/login.action")
public class LoginActionBean implements ActionBean {

	private static final Logger LOG = Logger.getLogger(LoginActionBean.class);

	private UserService userService = new UserService();
	private JCActionBeanContext context;
    private String username;
    private String password;
	private String email;
	private String firstname;
	private String lastname;
	private String newusername;
	private String newpassword;
    private User user;
    
    @DefaultHandler
    public Resolution login() throws Exception {

		LOG.info("login attempt for user="+username);
    	int pageNo = 0;
    	boolean exact = true;
    	boolean populateBean = true;
        User user = userService.getUserByAuth(username,password);
        if (user == null) {
			throw new Exception("Invalid login for username " + username);
		} else {
			context.setUser(user);
        }
        return new ForwardResolution("index.jsp");
    }

	@HandlesEvent("register")
    public Resolution register() throws Exception {
		LOG.info("register attempt for user="+newusername);
		userService.createUser(newusername,firstname,lastname,email,newpassword);

		// save the logged in user to the session
		context.setUser(user);

		return new ForwardResolution("index.jsp");
    }

	@HandlesEvent("reset")
	public Resolution reset() throws Exception {
		LOG.info("reset() called");
		if (StringUtils.isBlank(password)) {
			throw new Exception("password is required");
		}
		String referrer = context.getRequest().getHeader("referer");
		LOG.info("referrer=" + referrer);
		if (StringUtils.isNotBlank(referrer) && referrer.contains("?")) {
			String queryString = referrer.split("\\?")[1];
			LOG.info("queryString=" + queryString);
			if (StringUtils.isNotBlank(queryString) && queryString.contains("&")) {
				String[] queryParams = queryString.split("\\&");
				Map<String, String> queryParamMap = new HashMap<String, String>();
				for (String queryParam : queryParams) {
					String[] queryParamParts = queryParam.split("=");
					queryParamMap.put(queryParamParts[0], queryParamParts[1]);
				}
				String usernameParam = queryParamMap.get("user");
				LOG.info("usernameParam=" + usernameParam);
				if (StringUtils.isBlank(usernameParam)) {
					throw new Exception("user parameter is missing");
				}
				String token = queryParamMap.get("token");
				LOG.info("token=" + token);
				if (StringUtils.isBlank(token)) {
					throw new Exception("token parameter is missing");
				}
				userService.updatePassword(usernameParam,password);
			}
		}
		return new ForwardResolution("index.jsp");
	}
    
	@Override
	public ActionBeanContext getContext() {
		return context;
	}

	@Override
	public void setContext(ActionBeanContext context) {
		this.context = (JCActionBeanContext)context;
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

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getNewusername() {
		return newusername;
	}

	public void setNewusername(String newusername) {
		this.newusername = newusername;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
}
