package au.com.jcloud.model;

import java.util.Date;

import au.com.jcloud.jcframe.annotation.DisplayValueColumn;
import au.com.jcloud.jcframe.model.BaseBean;

/**
 * Created by david on 17/07/16.
 */
@DisplayValueColumn("name")
public class Purchase extends BaseBean<Integer> {
    private String name;
    private User user;
    private Date date;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
