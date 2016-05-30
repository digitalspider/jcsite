package au.com.javacloud.model;

/**
 * Created by david on 22/05/16.
 */

public class BaseBean {
    protected int id;
    protected String name;

    @Override
    public String toString() {
        return getClass().getSimpleName()+"["+id+"] name="+name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
