package au.com.javacloud.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


/**
 */
public class HttpUtil
{
    private static final Logger log = Logger.getLogger(HttpUtil.class);

    public static String[] getPathParts(HttpServletRequest req) {
        String path1 = req.getServletPath(); // /gpio
        String path2 = req.getContextPath(); // null
        String path3 = req.getPathInfo(); // /4/direction
        String path4 = req.getQueryString(); // q=test&p2=4
        String path5 = req.getServerName(); // vittor-desktop
        String path6 = req.getProtocol(); // http
        String uri = req.getRequestURI(); // /gpio/4/direction
        String url = req.getRequestURL().toString(); // http://vittor-desktop:8111/gpio/4/direction

        if (path3!=null) {
            return path3.substring(1).split("/");
        }
        return new String[0];
    }


    public static String getBaseUrl(HttpServletRequest request) {
//		System.out.println("request="+request);
		System.out.println("request.getPathInfo()="+request.getPathInfo());
//		System.out.println("request.getServletPath()="+request.getServletPath());
//		System.out.println("request.getQueryString()="+request.getQueryString());
//		System.out.println("request.getRequestURL()="+request.getRequestURL());
        String url = request.getRequestURL().toString();
        String baseUrl = url.substring(0, url.indexOf("/",9));
        baseUrl = baseUrl + request.getServletPath();
//		System.out.println("baseUrl="+baseUrl);
        return baseUrl;
    }

    public static String executeLinuxCmd(String cmd) throws IOException {
        String[] cmdArray = { "/bin/sh", "-c", cmd };
        log.debug("cmd="+cmdArray[2]);
        Process p = Runtime.getRuntime().exec(cmdArray);
//        String output = IOUtils.toString(p.getInputStream());
//        log.debug("output="+output);
//        return output;
        return null;
    }

    public static InputStream executeLinuxCmdAsStream(String cmd) throws IOException {
        String[] cmdArray = { "/bin/sh", "-c", cmd };
        log.debug("cmd="+cmdArray[2]);
        Process p = Runtime.getRuntime().exec(cmdArray);
        return p.getInputStream();
    }

    public static int getLinesCountForFile(File readFile) throws Exception {
        String cmd = "wc -l " + readFile+" | cut -f 1 -d ' '";
        String output = executeLinuxCmd(cmd);
        if (output.trim().length()>0) {
            return Integer.parseInt(output.trim());
        }
        return 0;
    }

    public static List<String> getLastNLogLines(File readFile, int batchSize, int linesToRead) {
        List<String> content = new ArrayList<String>();
        InputStream is = null;
        try {
            String cmd = "tail -" + linesToRead + " "+readFile+" | head -"+batchSize;
            is = executeLinuxCmdAsStream(cmd);
            BufferedReader input = new BufferedReader(new java.io.InputStreamReader(is));
            String line = null;
            while ((line = input.readLine()) != null) {
                content.add(0,line);
            }
        } catch (java.io.IOException e) {
            log.error(e,e);
        } finally {
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error(e,e);
                }
            }
        }
        return content;
    }


    public static String getHost(String url) {
        int startIndex = url.indexOf("://");
        if (startIndex>0) {
            startIndex+=3;
            int endIndex = url.indexOf("/",startIndex);
            if (endIndex>startIndex) {
                String currentHost = url.substring(startIndex,endIndex);
                return currentHost;
            }
        }
        return null;
    }

    public static String getTextFileName(String filename) {
        int index = filename.lastIndexOf(".");
        filename = filename.substring(0,index);
        return filename+".txt";
    }

    public static String getFileExtension(String filename) {
        int index = filename.lastIndexOf(".");
        return filename.substring(index+1);
    }

    public static String firstCharUpperCase(String input) {
        return input.substring(0, 1).toUpperCase()+input.substring(1);
    }

    public static void main( String[] args )
    {
        try {
            log.info( "Hello World!" );
        } catch (Exception e) {
            log.error(e,e);
        }
    }

}