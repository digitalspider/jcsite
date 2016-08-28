package au.com.jcloud.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import au.com.jcloud.jcframe.servlet.UserRoleFilter;
import au.com.jcloud.model.User;

/**
 * Created by david on 26/05/16.
 */
public class Constants {
    public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String SESSION_ATTRIBUTE_USER = "jcuser";
    public static final String SESSION_ATTRIBUTE_ROLES = "jcroles";
    public static final String PATH_SECURE = "/secure";

    public static final String PAGE_LOGIN = "/login.jsp";
    public static final String PAGE_INDEX = "/index.jsp";
    public static final String PAGE_RESET = "/reset.jsp";
    public static final String PAGE_SECURE = "/secure.action";

    public static final User systemUser;

    static {
        systemUser = new User();
        systemUser.setId(1);
        systemUser.setType("SYSTEM");
    }

    public static User getSystemUser() {
        return systemUser;
    }
}
