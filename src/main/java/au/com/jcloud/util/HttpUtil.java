package au.com.jcloud.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by david on 17/08/16.
 */
public class HttpUtil {
	private static final Logger LOG = Logger.getLogger(HttpUtil.class);

	public static String getContextUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String contextUrl = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath();
		LOG.debug("contextUrl="+contextUrl);
		return contextUrl;
	}

	public static String getServletUrl(HttpServletRequest request) {
		String baseUrl = getContextUrl(request);
		baseUrl = baseUrl + request.getServletPath();
		LOG.debug("baseUrl="+baseUrl);
		return baseUrl;
	}

	public static void sendRedirect(HttpServletRequest request, HttpServletResponse response, String redirectParam) throws IOException {
		String redirectPath = HttpUtil.getServletUrl(request);
		if (redirectParam!=null) {
			String redirect = (String) request.getParameter(redirectParam);
			if (StringUtils.isNotEmpty(redirect)) {
				redirectPath += redirect;
			}
		}
		LOG.debug("redirectPath=" + redirectPath);
		response.sendRedirect(response.encodeRedirectURL(redirectPath));
	}

}
