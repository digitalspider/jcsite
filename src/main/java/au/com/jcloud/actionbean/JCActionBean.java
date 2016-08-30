package au.com.jcloud.actionbean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import au.com.jcloud.context.JCActionBeanContext;
import au.com.jcloud.util.Constants;
import au.com.jcloud.util.HttpUtil;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontBind;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;

/**
 * Created by david.vittor on 14/08/16.
 */
public class JCActionBean implements ActionBean {
	protected final Logger LOG = Logger.getLogger(getClass());

	protected JCActionBeanContext context;

	@DontValidate
	@DontBind
	@DefaultHandler
	public Resolution action() throws Exception {
		String url = Constants.PAGE_LOGIN;
		String queryString = getQueryString();
		if (StringUtils.isNotBlank(queryString)) {
			url += "?" + queryString;
		}
		return new RedirectResolution(url);
	}

	protected HttpServletRequest getRequest() {
		return getContext().getRequest();
	}

	protected HttpServletResponse getResponse() {
		return getContext().getResponse();
	}

	protected Resolution getSourcePageResolution() {
		return getContext().getSourcePageResolution();
	}

	protected String getReferrer() {
		String referrer = getContext().getRequest().getHeader("referer");
		return referrer;
	}

	protected String getQueryString() {
		String queryString = getContext().getRequest().getQueryString();
		return queryString;
	}

	protected void addGlobalValidationError(String messageKey, Object... params) {
		ValidationErrors errors = new ValidationErrors();
		errors.addGlobalError(new LocalizableError(messageKey, params));
		getContext().setValidationErrors(errors);
	}

	protected String getConextPath() {
		String contextPath = HttpUtil.getContextUrl(getRequest());
		LOG.info("contextPath=" + contextPath);
		return contextPath;
	}

	@Override
	public ActionBeanContext getContext() {
		return context;
	}

	@Override
	public void setContext(ActionBeanContext context) {
		this.context = (JCActionBeanContext) context;
	}

}
