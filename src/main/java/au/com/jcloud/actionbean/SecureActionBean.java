package au.com.jcloud.actionbean;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontBind;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.security.PermitAll;

/**
 * Created by david on 29/08/16.
 */

@UrlBinding("/secure.action")
public class SecureActionBean extends JCActionBean {

	@PermitAll
	@DontValidate
	@DontBind
	@DefaultHandler
	public Resolution action() {
		return new ForwardResolution("/secure/index.jsp");
	}
}

