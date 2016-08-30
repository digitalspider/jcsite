package au.com.jcloud.actionbean;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import au.com.jcloud.model.User;
import au.com.jcloud.service.EmailService;
import au.com.jcloud.service.TokenService;
import au.com.jcloud.service.UserService;
import au.com.jcloud.util.Constants;

/**
 * Created by david.vittor on 3/08/16.
 */
// Requires Stripes 1.7.0
//@RestActionBean
@UrlBinding(Constants.ACTION_PUBLIC_LINK)
public class LinkActionBean extends JCActionBean {

	public Resolution post() {
		double result = 5;
		return null;
		//return new JsonResolution( Double.toString( result ) );
	}
}
