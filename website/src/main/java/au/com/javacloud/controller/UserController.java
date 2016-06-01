package au.com.javacloud.controller;

/**
 * Created by david on 22/05/16.
 */

import javax.servlet.annotation.WebServlet;

import au.com.javacloud.model.User;

@WebServlet("/user/*")
public class UserController extends BaseControllerImpl<User> {

    public UserController() {
		super(User.class);
	}

}