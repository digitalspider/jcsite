package au.com.jcloud.actionbean;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;

import javax.annotation.security.PermitAll;

import au.com.jcloud.WebConstants;
import au.com.jcloud.model.User;
import au.com.jcloud.util.PathParts;

import static au.com.jcloud.actionbean.AccountActionBean.JSP_BINDING;

/**
 * Created by david.vittor on 3/08/16.
 */
@PermitAll
@UrlBinding(WebConstants.PATH_SECURE+JSP_BINDING)
public class AccountActionBean extends JCSecureActionBean {

	public static final String JSP_BINDING = "/account";

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

	@DefaultHandler
	@Override
	public Resolution action() {
		User user = getJCContext().getUser();
		username = user.getUsername();
		firstname = user.getFirstName();
		lastname = user.getLastName();
		email = user.getEmail();

		PathParts pathParts = getPathParts(); // 0=account, 1=edit
		LOG.debug("pathParts="+pathParts);
		if (pathParts.size()>1 && pathParts.get(1).equals("edit")) {
			LOG.debug("go edit = "+getEditResolution());
			return getEditResolution();
		}
		return getShowResolution();
	}

	public String getJSPBinding() {
		return JSP_BINDING;
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
