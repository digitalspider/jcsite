package au.com.jcloud.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import au.com.jcloud.model.User;

/**
 * Created by david on 26/05/16.
 */
public class Constants {
    public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static User getSystemUser() {
        User systemUser = new User();
        systemUser.setId(1);
        return systemUser;
    }
}
