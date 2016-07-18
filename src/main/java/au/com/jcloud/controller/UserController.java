package au.com.jcloud.controller;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.jcloud.jcframe.annotation.BeanClass;
import au.com.jcloud.jcframe.controller.BaseControllerImpl;
import au.com.jcloud.model.User;
import au.com.jcloud.util.Constants;

/**
 * Created by david on 18/07/16.
 */
@BeanClass(User.class)
public class UserController extends BaseControllerImpl<Integer, User, Principal> {

    private static final Logger LOG = Logger.getLogger(UserController.class);

    @Override
    public void create() throws Exception {
        super.create();
        LOG.info("New User created!!!");
    }
}
