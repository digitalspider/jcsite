package au.com.jcloud.emodel;

import com.avaje.ebean.Model;

/**
 * Created by david.vittor on 5/08/16.
 */
public class IdBean extends Model {
    protected int id;

    @Override
    public String toString() {
        return getClass().getSimpleName()+" ["+id+"]:";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
