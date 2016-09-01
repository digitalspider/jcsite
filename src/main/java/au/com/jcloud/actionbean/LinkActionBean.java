package au.com.jcloud.actionbean;

import net.sourceforge.stripes.action.JsonResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.RestActionBean;
import net.sourceforge.stripes.action.UrlBinding;


import java.util.HashMap;
import java.util.Map;


import au.com.jcloud.WebConstants;

import static au.com.jcloud.WebConstants.URL_CC_PREFIX;

/**
 * Created by david.vittor on 3/08/16.
 */
@RestActionBean
@UrlBinding(WebConstants.ACTION_PUBLIC_LINK)
public class LinkActionBean extends JCActionBean {

	public Resolution get() {
		return new JsonResolution( getLinkData() );
	}

	public Resolution post() throws Exception {
		return new JsonResolution( getLinkData() );
	}

	protected Map<String,String> getLinkData() {
		Map<String, String> data = new HashMap<>();
		data.put("david", getConextPath()+URL_CC_PREFIX+"http://david.com");
		data.put("google", getConextPath()+URL_CC_PREFIX+"http://google.com");
		return data;
	}
}
