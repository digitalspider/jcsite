package au.com.jcloud.security;

/**
 * Created by david on 13/06/16.
 */
import org.apache.log4j.Logger;

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
import au.com.jcloud.util.Constants;

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

	private final static Logger LOG = Logger.getLogger(UserRoleFilter.class);

	public void init(FilterConfig cfg) throws ServletException {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain next)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		User user = (User)request.getSession().getAttribute(Constants.SESSION_ATTRIBUTE_USER);

		@SuppressWarnings("unchecked")
		List<String> roles = (List<String>)request.getSession().getAttribute(Constants.SESSION_ATTRIBUTE_ROLES);

		if (user!=null) {
			next.doFilter(new UserRoleRequestWrapper(user, roles, request), response);
		} else {
			// Secure all "/jsp/secure/*" pages
			String servletPath = request.getServletPath();
			LOG.debug("servletPath="+servletPath);
			String contextPath = request.getContextPath();
			LOG.debug("contextPath="+contextPath);
			if (servletPath.startsWith(Constants.PATH_SECURE_JSP)) {
				response.sendRedirect(contextPath+ Constants.PAGE_LOGIN);
			} else {
				next.doFilter(request, response);
			}
		}
	}

	public void destroy() {
	}
}
