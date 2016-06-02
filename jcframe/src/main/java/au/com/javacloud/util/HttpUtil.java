package au.com.javacloud.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class HttpUtil
{
    private static final Logger LOG = Logger.getLogger(HttpUtil.class);

    public static String[] getPathParts(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        LOG.debug("pathInfo="+pathInfo);
        if (pathInfo!=null) {
            return pathInfo.substring(1).split("/");
        }
        return new String[0];
    }


    public static String getBaseUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String baseUrl = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath();
        LOG.debug("baseUrl="+baseUrl);
        return baseUrl;
    }
}