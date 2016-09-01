package au.com.jcloud.filter;

import com.avaje.ebean.Ebean;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.jcloud.model.ClickCount;
import au.com.jcloud.util.HttpUtil;

import static au.com.jcloud.WebConstants.PATH_CLICK_COUNT;
import static au.com.jcloud.WebConstants.URL_PARAM_REDIRECT;

/**
 * Created by david.vittor on 2/09/16.
 */
@WebFilter(urlPatterns = PATH_CLICK_COUNT)
public class ClickCountFilter extends BaseFilter {

	@Override
	public boolean filterAction(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		String queryString = "?"+request.getQueryString();
		LOG.info("queryString="+queryString);
		if (queryString.startsWith(URL_PARAM_REDIRECT)) {
			String url = queryString.substring(URL_PARAM_REDIRECT.length());
			try {
				LOG.info("url=" + url);
				ClickCount cc = new ClickCount();
				cc.setUrl(url);
				cc.setDomain(url);
				cc.setUserAgent(HttpUtil.getUserAgent(request));
				cc.setIpAddress(HttpUtil.getIpAddress(request));
				cc.setUsername(request.getRemoteUser());
				Ebean.save(cc);
				LOG.info("saved cc=" + cc);
			} catch (Exception e) {
				LOG.error(e,e);
			}
			response.sendRedirect(url);
			return true;

		}
		return false;
	}
}
