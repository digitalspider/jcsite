package au.com.jcloud.actionbean;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import au.com.jcloud.emodel.User;
import au.com.jcloud.jcframe.servlet.FrontControllerServlet;
import au.com.jcloud.jcframe.servlet.UserRoleFilter;
import au.com.jcloud.service.EmailService;
import au.com.jcloud.service.TokenService;
import au.com.jcloud.service.UserService;
import au.com.jcloud.util.Constants;
import au.com.jcloud.util.HttpUtil;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
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
	@SpringBean
	private EmailService emailService;
	@SpringBean
	private TokenService tokenService;
	@Validate(required = true, minlength = 2, maxlength = 64, on = "{login, forgot}")
	private String username;
	@Validate(required = true, minlength = 8, maxlength = 64, on = "{login, reset}")
	private String password;
	@Validate(required = true, minlength = 2, maxlength = 64, on = "register", converter = EmailTypeConverter.class)
	private String email;
	@Validate(required = true, minlength = 2, maxlength = 32, on = "register")
	private String firstname;
	@Validate(required = true, minlength = 2, maxlength = 32, on = "register")
	private String lastname;
	@Validate(required = true, minlength = 2, maxlength = 64, on = "register")
	private String newusername;
	@Validate(required = true, minlength = 8, maxlength = 64, on = "register")
	private String newpassword;

	public static final String PARAM_USERNAME = "username";
	public static final String PARAM_TOKEN = "token";

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
		String redirect = Constants.PAGE_SECURE;
		String referrer = getContext().getRequest().getHeader("referer");
		String path = HttpUtil.getContextUrl(getContext().getRequest());
		if (path!=null) {
			path+=Constants.PATH_SECURE;
		}
		LOG.debug("referrer=" + referrer);
		LOG.debug("path=" + path);
		if (StringUtils.isNotBlank(referrer) && StringUtils.isNotBlank(path) && referrer.startsWith(path)) {
			String queryString = referrer.substring(path.length());
			LOG.debug("queryString=" + queryString);

			if (queryString != null && queryString.startsWith("?r=")) {
				redirect = queryString.substring(3);
			}
		}
		return new RedirectResolution(redirect);
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

		return new RedirectResolution(Constants.PAGE_SECURE);
	}

	public static boolean isResetReady(HttpServletRequest request) {
		String username = request.getParameter("username");
		String token = request.getParameter("token");
		boolean resetReady = StringUtils.isNotBlank(username) && StringUtils.isNotBlank(token);
		return resetReady;
	}

	@HandlesEvent("forgot")
	public Resolution forgot() throws Exception {
		LOG.info("forgot for user=" + username);
		if (validateAlreadyLoggedIn()) {
			return getContext().getSourcePageResolution();
		}
		User user = userService.getByUsernameOrEmail(username);
		if (user == null) {
			addGlobalValidationError("/login.action.username.invalid");
			return getContext().getSourcePageResolution();
		}
		String basePath = HttpUtil.getContextUrl(getContext().getRequest());
		LOG.info("basePath="+basePath);
		String url = basePath+Constants.PAGE_RESET+"?"+PARAM_USERNAME+"="+user.getName()+"&"+PARAM_TOKEN+"="+tokenService.generateAndRecordToken(user.getName());
		LOG.info("url="+url);
		String message = "Hi " + user.getFirstName()+",<br/><br/>"+
				"You have 30 minutes to use the below link to reset your password<br/>" +
				"<a href=\""+url+"\">"+url+"</a><br/><br/>"+
				"Yours sincerely,<br/>"+
				"The JCloud team<br/>";
		emailService.sendToEmail(user.getFullName(), user.getEmail(),"JCloud Password Reset", message);
		return getContext().getSourcePageResolution();
	}

	@HandlesEvent("reset")
	public Resolution reset() throws Exception {
		LOG.info("reset() called");
		String referrer = getContext().getRequest().getHeader("referer");
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
				String usernameParam = queryParamMap.get(PARAM_USERNAME);
				LOG.info("usernameParam=" + usernameParam);
				if (StringUtils.isBlank(usernameParam)) {
					addGlobalValidationError("/login.action.reset.userMissing", usernameParam);
					return getContext().getSourcePageResolution();
				}
				String token = queryParamMap.get(PARAM_TOKEN);
				LOG.info("token=" + token);
				if (StringUtils.isBlank(token)) {
					addGlobalValidationError("/login.action.reset.tokenMissing", usernameParam);
					return getContext().getSourcePageResolution();
				}
				if (!tokenService.validateToken(usernameParam,token)) {
					addGlobalValidationError("/login.action.reset.invalidToken", usernameParam);
					return getContext().getSourcePageResolution();
				}
				User user = userService.updatePassword(usernameParam, password);
				context.setUser(user);
				tokenService.clearToken(usernameParam);
			}
		}
		return new RedirectResolution(Constants.PAGE_INDEX);
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

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public TokenService getTokenService() {
		return tokenService;
	}

	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
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
