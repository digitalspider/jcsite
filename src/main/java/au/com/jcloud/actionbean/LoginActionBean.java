package au.com.jcloud.actionbean;

import com.avaje.ebean.Ebean;

import java.util.List;

import org.apache.log4j.Logger;

import au.com.jcloud.context.JCActionBeanContext;
import au.com.jcloud.emodel.User;
import au.com.jcloud.service.EncryptService;
import au.com.jcloud.service.UserService;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.RestActionBean;
import net.sourceforge.stripes.action.UrlBinding;

@RestActionBean
@UrlBinding("/login.action")
public class LoginActionBean implements ActionBean {

	private static final Logger LOG = Logger.getLogger(LoginActionBean.class);

	private UserService userService = new UserService();
	private EncryptService encryptService = new EncryptService();
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
		String passValue = getEncryptService().md5(password);
        User user = userService.getUserByAuth(username,passValue);
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
    	User user = new User();
		List<User> exists = userService.getByUsername(newusername);
		if (exists.size()>0) {
			throw new Exception("Username already exists! Please select a different one");
		}
		exists = userService.getByEmail(email);
		if (exists.size()>0) {
			throw new Exception("This email is already registered! Please select a different one");
		}
		user.setName(newusername);
		String passValue = getEncryptService().md5(newpassword);
		user.setPassword(passValue);
		user.setEmail(email);
		user.setFirstName(firstname);
		user.setLastName(lastname);
		Ebean.save(user);

		// save the logged in user to the session
		context.setUser(user);

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

	protected EncryptService getEncryptService() {
		return encryptService;
	}

	public void setEncryptService(EncryptService encryptService) {
		this.encryptService = encryptService;
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
