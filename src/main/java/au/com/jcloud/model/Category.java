package au.com.jcloud.model;

import au.com.jcloud.jcframe.annotation.DisplayValueColumn;

/**
 * Created by david on 17/07/16.
 */
@DisplayValueColumn("name")
public class Category extends AuditBean {

    private String name;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
