package au.com.jcloud.model;

import au.com.jcloud.jcframe.annotation.DisplayValueColumn;
import au.com.jcloud.jcframe.model.BaseBean;

/**
 * Created by david on 17/07/16.
 */
public class Cart extends BaseBean<Integer> {
    private User user;
    private String status;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
