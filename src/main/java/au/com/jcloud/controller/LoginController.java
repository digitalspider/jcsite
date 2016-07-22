package au.com.jcloud.controller;

import org.apache.log4j.Logger;

import au.com.jcloud.jcframe.controller.BaseLoginController;
import au.com.jcloud.jcframe.annotation.BeanClass;
import au.com.jcloud.jcframe.model.BaseLogin;

@BeanClass(BaseLogin.class)
public class LoginController extends BaseLoginController {

    private static final Logger LOG = Logger.getLogger(UserController.class);

    @Override
    protected boolean isValid(String username, String password) throws Exception {
		boolean result = super.isValid(username,password);
		LOG.info("Login attempt. User="+username+". Result="+result);
		return result;
	}
}
