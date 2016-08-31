package au.com.jcloud.security;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by david on 13/06/16.
 */
import org.apache.log4j.Logger;

import au.com.jcloud.model.Role;
import au.com.jcloud.model.User;
import au.com.jcloud.util.Constants;

/**
 * https://coderanch.com/t/466744/Servlets/java/Set-user-principal-filter
 *
 * A servlet filter to replace the request object with our own request wrapper
 * that implements our isUserInRole() method. This makes the subsequent (jsp
 * page, servlet) our handler to read the database information for the user when
 * isUserInRole() is called.
 *
 * <p>
 * This should be installed as a filter mapped to /* events. Or maybe *.jsp and
 * *.do, etc. any where we would want to call the isUserInRole method.
 * </p>
 * <p>
 * Depends on the profile manager
 * </p>
 *
 * @author thein
 */
@WebFilter(urlPatterns = { "/*" })
public class UserRoleFilter extends BaseFilter {

	@Override
	public boolean filterAction(final HttpServletRequest request, final HttpServletResponse response,
	                         FilterChain filterChain) throws IOException, ServletException {

		User user = (User) request.getSession().getAttribute(Constants.SESSION_ATTRIBUTE_USER);

		@SuppressWarnings("unchecked")
		Set<Role> roles = (Set<Role>) request.getSession().getAttribute(Constants.SESSION_ATTRIBUTE_ROLES);

		if (user != null) {
			filterChain.doFilter(new UserRoleRequestWrapper(user, roles, request), response);
		} else {
			// Secure all "/jsp/secure/*" pages
			String servletPath = request.getServletPath();
			LOG.debug("servletPath=" + servletPath);
			String contextPath = request.getContextPath();
			LOG.debug("contextPath=" + contextPath);
			if (servletPath.startsWith(Constants.PATH_SECURE_JSP)) {
				response.sendRedirect(contextPath + Constants.PAGE_LOGIN);
			} else {
				filterChain.doFilter(request, response);
			}
		}
		return true;
	}

}
