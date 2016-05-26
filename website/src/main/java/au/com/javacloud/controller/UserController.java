package au.com.javacloud.controller;

/**
 * Created by david on 22/05/16.
 */

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.javacloud.dao.StudentDAO;
import au.com.javacloud.dao.StudentDAOImpl;
import au.com.javacloud.dao.UserDAO;
import au.com.javacloud.dao.UserDAOImpl;
import au.com.javacloud.model.Student;
import au.com.javacloud.model.User;

@WebServlet("/user")
public class UserController extends BaseController {

    public static final String URL_lIST = "/page/user/list.jsp";
    public static final String URL_INSERT_OR_EDIT = "/page/user/edit.jsp";
    public static final String PROP_BEANNAME = "user";
    public static final String PROP_BEANSNAME = "users";

    public UserController() {
        super(new UserDAOImpl(), PROP_BEANNAME, PROP_BEANSNAME, URL_lIST, URL_INSERT_OR_EDIT);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setEmail( request.getParameter( "email" ) );
        user.setUsername( request.getParameter( "username" ) );
        user.setPassword( request.getParameter( "password" ) );
        user.setLastname( request.getParameter( "lastname" ) );
        user.setFirstname( request.getParameter( "firstname" ) );

        String id = request.getParameter("id");
        if( id == null || id.isEmpty() )
            dao.add(user);
        else {
            user.setId( Integer.parseInt(id) );
            dao.update(user);
        }
        RequestDispatcher view = request.getRequestDispatcher( URL_lIST );
        request.setAttribute(PROP_BEANSNAME, dao.getAll());
        view.forward(request, response);
    }
}