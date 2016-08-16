package au.com.jcloud.actionbean;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import au.com.jcloud.emodel.User;
import au.com.jcloud.service.UserService;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;

/**
 * Created by david.vittor on 3/08/16.
 */
@UrlBinding("/login.action")
public class LoginActionBean extends JCActionBean {

	@SpringBean
	private UserService userService;
	@Validate(required = true, minlength = 2, maxlength = 64, on = "login")
	private String username;
	@Validate(required = true, minlength = 2, maxlength = 64, on = "login")
	private String password;
	@Validate(required = true, minlength = 2, maxlength = 64, on = "register")
	private String email;
	@Validate(required = true, minlength = 2, maxlength = 32, on = "register")
	private String firstname;
	@Validate(required = true, minlength = 2, maxlength = 32, on = "register")
	private String lastname;
	@Validate(required = true, minlength = 2, maxlength = 64, on = "register")
	private String newusername;
	@Validate(required = true, minlength = 2, maxlength = 64, on = "register")
	private String newpassword;

	@Before(stages = LifecycleStage.BindingAndValidation)
	public void presubmit() {
		LOG.info("presubmit()");
	}

	@DefaultHandler
	public Resolution login() throws Exception {

		LOG.info("login attempt for user=" + username);
		if (validateAlreadyLoggedIn()) {
			return getContext().getSourcePageResolution();
		}
		User user = userService.getUserByAuth(username, password);
		if (user == null) {
			addGlobalValidationError("/login.action.invalidAttempt", username);
			return getContext().getSourcePageResolution();
		}
		else {
			context.setUser(user);
		}
		LOG.info("login successful for user=" + username);
		return new ForwardResolution("index.jsp");
	}

	@HandlesEvent("register")
	public Resolution register() throws Exception {
		LOG.info("register attempt for user=" + newusername);
		if (validateAlreadyLoggedIn()) {
			return getContext().getSourcePageResolution();
		}
		if (userService.getByUsername(newusername)!=null) {
			addGlobalValidationError("/login.action.newusername.alreadyExists");
			return getContext().getSourcePageResolution();
		}
		if (userService.getByEmail(email)!=null) {
			addGlobalValidationError("/login.action.email.alreadyExists");
			return getContext().getSourcePageResolution();
		}
		User user = userService.createUser(newusername, firstname, lastname, email, newpassword);
		LOG.info("user created=" + user);

		// save the logged in user to the session
		context.setUser(user);

		return new ForwardResolution("index.jsp");
	}

	@HandlesEvent("logout")
	public Resolution logout() throws Exception {
		LOG.info("logout attempt for user=" + context.getUser());
		if (context.getUser() == null) {
			addGlobalValidationError("/login.action.notLoggedIn");
			return getContext().getSourcePageResolution();
		}
		// log out the user from the session
		context.setUser(null);

		return new ForwardResolution("login.jsp");
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
				userService.updatePassword(usernameParam, password);
			}
		}
		return new ForwardResolution("index.jsp");
	}

	private boolean validateAlreadyLoggedIn() {
		if (context.getUser() != null) {
			addGlobalValidationError("/login.action.alreadyLoggedIn", context.getUser().getName());
			return true;
		}
		return false;
	}

	private void addGlobalValidationError(String messageKey, Object... params) {
		ValidationErrors errors = new ValidationErrors();
		errors.addGlobalError(new LocalizableError(messageKey, params));
		getContext().setValidationErrors(errors);
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
