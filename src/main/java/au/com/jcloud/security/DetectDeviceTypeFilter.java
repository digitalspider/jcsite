package au.com.jcloud.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.ServletRequestUtils;

import au.com.jcloud.enums.DeviceType;
import au.com.jcloud.util.Constants;

/**
 * This filter used to determine the device type (PC or Mobile) by looking at the request.
 */
public class DetectDeviceTypeFilter extends BaseFilter {

	@Override
	public boolean filterAction(final HttpServletRequest request, final HttpServletResponse response, FilterChain filterChain) {
		String deviceTypeParam = ServletRequestUtils.getStringParameter(request, Constants.REQUEST_DEVICE_TYPE, null);
		LOG.info("deviceTypeParam="+deviceTypeParam);
		if (StringUtils.isNotBlank(deviceTypeParam)) {
			DeviceType deviceType = DeviceType.parse(deviceTypeParam);
			LOG.info("deviceType="+deviceType);
			request.getSession().setAttribute(Constants.SESSION_ATTRIBUTE_DEVICE, deviceType);
		}
		return false;
	}
}
