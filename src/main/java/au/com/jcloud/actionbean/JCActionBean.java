package au.com.jcloud.actionbean;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

import org.apache.log4j.Logger;

import au.com.jcloud.context.JCActionBeanContext;

/**
 * Created by david on 14/08/16.
 */
public class JCActionBean implements ActionBean {
	protected final Logger LOG = Logger.getLogger(getClass());

	protected JCActionBeanContext context;

	@Override
	public ActionBeanContext getContext() {
		return context;
	}

	@Override
	public void setContext(ActionBeanContext context) {
		this.context = (JCActionBeanContext) context;
	}

}
