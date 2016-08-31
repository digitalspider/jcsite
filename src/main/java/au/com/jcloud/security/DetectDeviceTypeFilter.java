package au.com.jcloud.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;

import au.com.jcloud.enums.DeviceType;
import au.com.jcloud.util.Constants;

/**
 * This filter used to determine the device type (PC or Mobile) by looking at the request.
 */
public class DetectDeviceTypeFilter implements Filter {

	@Override
	public void init(final FilterConfig arg0) throws ServletException {
		// No init required
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain) throws IOException, ServletException {
		if (!isHttpServletRequest(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		detectAndSave(request);
		filterChain.doFilter(request, response);

	}

	private boolean isHttpServletRequest(final ServletRequest request) {
		return (request instanceof HttpServletRequest);
	}

	private void detectAndSave(final ServletRequest inputRequest) {
		HttpServletRequest request = (HttpServletRequest) inputRequest;
		String deviceTypeParam = ServletRequestUtils.getStringParameter(request, Constants.REQUEST_DEVICE_TYPE, null);
		if (StringUtils.isNotBlank(deviceTypeParam)) {
			DeviceType deviceType = DeviceType.parse(deviceTypeParam);
			request.getSession().setAttribute(Constants.SESSION_ATTRIBUTE_DEVICE, deviceType);
		}
	}

	@Override
	public void destroy() {
		// No action needed
	}
}
