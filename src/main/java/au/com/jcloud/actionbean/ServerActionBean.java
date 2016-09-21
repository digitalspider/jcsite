package au.com.jcloud.actionbean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.security.PermitAll;

import au.com.jcloud.WebConstants;
import au.com.jcloud.model.User;
import au.com.jcloud.util.PathParts;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontBind;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;

import static au.com.jcloud.actionbean.ServerActionBean.JSP_BINDING;

/**
 * Created by david.vittor on 3/08/16.
 */
@PermitAll
@UrlBinding(WebConstants.PATH_SECURE+JSP_BINDING)
public class ServerActionBean extends JCSecureActionBean {

	private ExecutorService executorService = Executors.newSingleThreadExecutor();

	public static final String JSP_BINDING = "/server";

	@Validate(required = true, minlength = 2)
	private String name;
	@Validate(required = true, converter = EmailTypeConverter.class)
	private String email;
	@Validate(required = true, minlength = 2)
	private String subject;
	@Validate(required = true, minlength = 2)
	private String message;

	@PermitAll
	@DontValidate
	@DontBind
	@DefaultHandler
	@Override
	public Resolution action() {
		User user = getUser();
		email = user.getEmail();

		PathParts pathParts = getPathParts(); // 0=server, 1=123 2=start
		LOG.info("pathParts="+pathParts);
		if (pathParts.size()>1) {
			if (pathParts.get(1).equals("add")) {
				LOG.info("go add = " + getEditResolution());
				return getEditResolution();
			}
			if (pathParts.isNumeric(1) && pathParts.size()>2) {
				int serverId = pathParts.getInt(1);
				if (pathParts.get(2).equals("view")) {
					LOG.info("view server " + serverId);
					return getShowResolution();
				}
				else if (pathParts.get(2).equals("start")) {
					LOG.info("start server " + serverId);
				}
				else if (pathParts.get(2).equals("stop")) {
					LOG.info("stop server " + serverId);
				}
				else if (pathParts.get(2).equals("restart")) {
					LOG.info("restart server " + serverId);
				}
				else if (pathParts.get(2).equals("service")) {
					LOG.info("service on server " + serverId);
				}
			}

		}
		return getDefaultResolution();
	}

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
