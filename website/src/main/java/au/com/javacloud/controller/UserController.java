package au.com.javacloud.controller;

/**
 * Created by david on 22/05/16.
 */

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.javacloud.dao.UserDAOImpl;
import au.com.javacloud.model.User;

@WebServlet("/user/*")
public class UserController extends BaseController<User> {

    public UserController() {
        super(new UserDAOImpl(), User.class);
    }

    protected User populateBean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setFirstname( request.getParameter( "firstName" ) );
        user.setLastname( request.getParameter( "lastName" ) );
        user.setEmail( request.getParameter( "email" ) );
        user.setUsername( request.getParameter( "username" ) );
        user.setPassword( request.getParameter( "password" ) );
        user.setUrl( request.getParameter( "url" ) );
        user.setMobile( request.getParameter( "mobile" ) );
        user.setType( request.getParameter( "type" ) );
        user.setTags( request.getParameter( "tags" ) );
        user.setStatus( request.getParameter( "status" ) );
        return user;
    }
}