package au.com.jcloud.actionbean;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontBind;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.security.PermitAll;

import au.com.jcloud.service.EmailService;
import au.com.jcloud.util.Constants;

/**
 * Created by david.vittor on 3/08/16.
 */
@PermitAll
@UrlBinding("/secure/server.action")
public class ServerActionBean extends JCActionBean {

	private ExecutorService executorService = Executors.newSingleThreadExecutor();
	@Validate(required=true, minlength = 2) private String name;
	@Validate(required=true, converter = EmailTypeConverter.class) private String email;
	@Validate(required=true, minlength = 2) private String subject;
	@Validate(required=true, minlength = 2) private String message;

	@PermitAll
	@DontValidate
	@DontBind
	@DefaultHandler
	@Override
	public Resolution action() {
		return new ForwardResolution(Constants.PAGE_SERVER);
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
