package au.com.jcloud.servlet;

import com.avaje.ebean.Ebean;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.jcloud.model.ClickCount;
import au.com.jcloud.util.HttpUtil;

import static au.com.jcloud.WebConstants.PATH_CLICK_COUNT;
import static au.com.jcloud.WebConstants.URL_PARAM_REDIRECT;

/**
 * Created by david on 2/09/16.
 */
@WebServlet(name = "clickCountServlet", urlPatterns = PATH_CLICK_COUNT)
public class ClickCountServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String queryString = request.getQueryString();
		if (queryString.startsWith(URL_PARAM_REDIRECT)) {
			String url = queryString.substring(URL_PARAM_REDIRECT.length());
			ClickCount cc = new ClickCount();
			cc.setUrl(url);
			cc.setDomain(url);
			cc.setUserAgent(HttpUtil.getUserAgent(request));
			cc.setIpAddress(HttpUtil.getIpAddress(request));
			cc.setUsername(request.getRemoteUser());
			Ebean.save(cc);
			response.sendRedirect(url);
		}
	}

}
