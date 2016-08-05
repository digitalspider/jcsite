package au.com.jcloud.emodel;

import java.sql.Date;

import javax.persistence.MappedSuperclass;

/**
 * Created by david on 5/08/16.
 */
@MappedSuperclass
public class BaseBean extends IdBean {
    protected String name;
    protected Date cdate;
    protected Date mdate;
    protected String status;

    @Override
    public String toString() {
        return super.toString()+" name="+name+" status="+status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public Date getMdate() {
        return mdate;
    }

    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}