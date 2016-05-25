package au.com.javacloud.model;

import java.util.Date;

/**
 * Created by david on 22/05/16.
 */

public class User extends BaseBean {
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private String url;
    private String mobile;
    private String token;
    private String image;

    @Override
    public String toString() {
        return "User[" + id + "]"+
                " firstName='" + firstname + '\'' +
                ", lastName='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", username=" + username +
                ", url=" + url +
                super.toString() +
                ']';
    }


}
