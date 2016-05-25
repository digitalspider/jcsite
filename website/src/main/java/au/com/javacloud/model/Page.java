package au.com.javacloud.model;

/**
 * Created by david on 22/05/16.
 */

public class Page extends BaseBean {
    private String title;
    private String extract;
    private String content;
    private String url;

    @Override
    public String toString() {
        return "Page[" + id + "]"+
                " title='" + title + '\'' +
                ", extract='" + extract + '\'' +
                ", url=" + url +
                super.toString() +
                ']';
    }

}
