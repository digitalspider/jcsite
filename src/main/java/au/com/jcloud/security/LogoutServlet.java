package au.com.jcloud.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.jcloud.util.Constants;

import static au.com.jcloud.WebConstants.PAGE_LOGIN;

/**
 * Created by david.vittor on 27/08/16.
 */
@WebServlet(name = "logoutServlet", urlPatterns = { "/logout" })
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Invalidate current HTTP session.
		// Will call JAAS LoginModule logout() method
		request.getSession().invalidate();

		// Redirect the user to the secure web page.
		// Since the user is now logged out the
		// authentication form will be shown
		response.sendRedirect(request.getContextPath() + PAGE_LOGIN);

	}

}