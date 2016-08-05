package au.com.jcloud.emodel;

import com.avaje.ebean.Model;

import javax.persistence.Id;
import javax.persistence.Version;

/**
 * Created by david.vittor on 5/08/16.
 */
public class IdBean {
    @Id
    protected Long id;

    @Override
    public String toString() {
        return getClass().getSimpleName()+" ["+id+"]:";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
