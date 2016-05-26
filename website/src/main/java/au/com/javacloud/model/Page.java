package au.com.javacloud.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 22/05/16.
 */

public class Page extends BaseBean {
    private String title;
    private String content;
    private String url;



    @Override
    public String toString() {
        return "Page[" + id + "]"+
                " title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url=" + url +
                super.toString() +
                ']';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
