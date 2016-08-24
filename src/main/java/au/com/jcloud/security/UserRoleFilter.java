package au.com.jcloud.security;

/**
 * Created by david on 13/06/16.
 */

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.jcloud.emodel.User;

/**
 * https://coderanch.com/t/466744/Servlets/java/Set-user-principal-filter
 *
 * A servlet filter to replace the request object with our own request wrapper that implements our isUserInRole() method.
 * This makes the subsequent (jsp page, servlet) our handler to read the database information for the user when isUserInRole() is called.
 *
 * <p>This should be installed as a filter mapped to /* events. Or maybe *.jsp and *.do, etc. any where we would want to call the isUserInRole method.</p>
 * <p>Depends on the profile manager</p>
 *
 * @author thein
 */
@WebFilter(urlPatterns = {"/*"})
public class UserRoleFilter implements Filter {

    public static String SESSION_ATTRIBUTE_USER = "jcuser";
    public static String SESSION_ATTRIBUTE_ROLES = "jcroles";

    public void init(FilterConfig cfg) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse response,
                         FilterChain next) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        User user = (User)request.getSession().getAttribute(SESSION_ATTRIBUTE_USER);

        @SuppressWarnings("unchecked")
		List<String> roles = (List<String>)request.getSession().getAttribute(SESSION_ATTRIBUTE_ROLES);

        next.doFilter(new UserRoleRequestWrapper(user, roles, request), response);
    }

    public void destroy() {
    }
}
