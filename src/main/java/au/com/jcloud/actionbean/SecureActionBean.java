package au.com.jcloud.actionbean;

import javax.annotation.security.PermitAll;

import au.com.jcloud.util.Constants;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontBind;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * Created by david.vittor on 29/08/16.
 */

@UrlBinding(Constants.ACTION_SECURE_INDEX)
public class SecureActionBean extends JCActionBean {

	@PermitAll
	@DontValidate
	@DontBind
	@DefaultHandler
	@Override
	public Resolution action() {
		return new ForwardResolution(Constants.PAGE_SECURE);
	}
}
