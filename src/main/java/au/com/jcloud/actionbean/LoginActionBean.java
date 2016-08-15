package au.com.jcloud.actionbean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import au.com.jcloud.emodel.User;
import au.com.jcloud.service.UserService;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
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
	@Validate(required=true, minlength = 2, on="{login, register}") private String username;
	@Validate(required=true, minlength = 2, on="{login, register}") private String password;
	@Validate(required=true, converter = EmailTypeConverter.class, on="register") private String email;
	@Validate(required=true, minlength = 2, on="register") private String firstname;
	@Validate(required=true, minlength = 2, on="register") private String lastname;
	private User user;

	@DefaultHandler
	public Resolution onLoad() {
		return context.getSourcePageResolution();
	}

	public Resolution login() throws Exception {

		LOG.info("login attempt for user=" + username);
		User user = userService.getUserByAuth(username, password);
		if (user == null) {
			ValidationErrors errors = new ValidationErrors();
			errors.add( "name", new LocalizableError("/login.action.invalidAttempt", username) );
			getContext().setValidationErrors(errors);
			return getContext().getSourcePageResolution();
		}
		else {
			context.setUser(user);
		}
		return new ForwardResolution("index.jsp");
	}

	public Resolution register() throws Exception {
		LOG.info("register attempt for user=" + username);
		userService.createUser(username, firstname, lastname, email, password);

		// save the logged in user to the session
		context.setUser(user);

		return new ForwardResolution("index.jsp");
	}

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

	/*
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
	*/
}
