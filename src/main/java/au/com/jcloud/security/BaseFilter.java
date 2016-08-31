package au.com.jcloud.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.ServletRequestUtils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.jcloud.enums.DeviceType;
import au.com.jcloud.util.Constants;

/**
 * Created by david on 31/08/16.
 */

public abstract class BaseFilter implements Filter {

	protected Logger LOG = Logger.getLogger(getClass());

	/**
	 * Handle the filter action with the request and response parameter.
	 *
	 * Return true to signify that you have handled the filterChain forwarding, otherwise
	 * this class with handle it.
	 *
	 * @param request
	 * @param response
	 * @param filterChain
	 * @return true if have called filterChain.doFilter() in your implementation
	 * @throws IOException
	 * @throws ServletException
	 */
	public abstract boolean filterAction(final HttpServletRequest request, final HttpServletResponse response,
	                                  final FilterChain filterChain) throws IOException, ServletException;

	@Override
	public void init(final FilterConfig arg0) throws ServletException {
		// No init required
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain filterChain) throws IOException, ServletException {
		if (!isHttpServletRequest(req)) {
			filterChain.doFilter(req, resp);
			return;
		}

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		boolean handled = filterAction(request, response, filterChain);
		if (!handled) {
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// No action needed
	}

	protected boolean isHttpServletRequest(final ServletRequest request) {
		return (request instanceof HttpServletRequest);
	}
}
