package au.com.jcloud.actionbean;

import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.security.PermitAll;

import au.com.jcloud.WebConstants;

import static au.com.jcloud.actionbean.AccountActionBean.JSP_BINDING;

/**
 * Created by david.vittor on 3/08/16.
 */
@PermitAll
@UrlBinding(WebConstants.PATH_SECURE+JSP_BINDING)
public class AccountActionBean extends JCSecureActionBean {

	public static final String JSP_BINDING = "/account";

	@Validate(required = true, minlength = 2)
	private String name;
	@Validate(required = true, converter = EmailTypeConverter.class)
	private String email;
	@Validate(required = true, minlength = 2)
	private String subject;
	@Validate(required = true, minlength = 2)
	private String message;

	public String getJSPBinding() {
		return JSP_BINDING;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
