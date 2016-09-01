package au.com.jcloud.security;

import javax.servlet.FilterChain;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;

import au.com.jcloud.enums.DeviceType;
import au.com.jcloud.util.Constants;

/**
 * This filter used to determine the device type (PC or Mobile) by looking at the request.
 */
@WebFilter(urlPatterns = { "/*" })
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