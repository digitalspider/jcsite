package au.com.javacloud.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class HttpUtil
{
    private static final Logger LOG = Logger.getLogger(HttpUtil.class);

    public static PathParts getPathParts(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        LOG.debug("pathInfo="+pathInfo);
        if (pathInfo!=null) {
            return new PathParts(pathInfo.substring(1).split("/"));
        }
        return new PathParts(new String[0]);
    }


    public static String getContextUrl(HttpServletRequest request) {

        String path1 = request.getServletPath(); // /gpio
        String path2 = request.getContextPath(); // null
        String path3 = request.getPathInfo(); // /4/direction
        String path4 = request.getQueryString(); // q=test&p2=4
        String path5 = request.getServerName(); // vittor-desktop
        String path6 = request.getProtocol(); // http
        String uri = request.getRequestURI(); // /gpio/4/direction
        String url = request.getRequestURL().toString(); // http://vittor-desktop:8111/gpio/4/direction
        System.out.println("path1="+path1);
        System.out.println("path2="+path2);
        System.out.println("path3="+path3);
        System.out.println("path4="+path4);
        System.out.println("path5="+path5);
        System.out.println("path6="+path6);
        System.out.println("uri="+uri);
        System.out.println("url="+url);

/*
        path1=/student
        path2=/website
        path3=/edit/3
        path4=null
        path5=localhost
        path6=HTTP/1.1
        uri=/website/student/edit/3
        url=http://localhost:9280/website/student/edit/3
        2016-06-03 19:03:44 INFO  BaseControllerImpl:137 - baseUrl=http://localhost:9280/websiteeditedit
        path1=/student
        path2=/website
        path3=/edit/3
        path4=null
        path5=localhost
        path6=HTTP/1.1
        uri=/website/student/edit/3
        url=http://localhost:9280/website/student/edit/3
*/

        String url2 = request.getRequestURL().toString();
        String contextUrl = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath();
        LOG.debug("contextUrl="+contextUrl);
        return contextUrl;
    }
    
    public static String getBaseUrl(HttpServletRequest request) {
        String baseUrl = getContextUrl(request);
        PathParts pathParts = getPathParts(request);
        if (!pathParts.isEmpty()) {
        	baseUrl += request.getServletPath();
        }
        LOG.debug("baseUrl="+baseUrl);
        return baseUrl;
    }
}