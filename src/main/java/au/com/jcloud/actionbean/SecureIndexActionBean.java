package au.com.jcloud.actionbean;

import javax.annotation.security.PermitAll;

import au.com.jcloud.WebConstants;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontBind;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * Created by david.vittor on 29/08/16.
 */

@UrlBinding(WebConstants.ACTION_SECURE_INDEX)
public class SecureIndexActionBean extends JCActionBean {

	@PermitAll
	@DontValidate
	@DontBind
	@DefaultHandler
	@Override
	public Resolution action() {
		return new ForwardResolution(WebConstants.PAGE_SECURE);
	}
}
