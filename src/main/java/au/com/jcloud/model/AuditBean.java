package au.com.jcloud.model;

import java.util.Date;

import au.com.jcloud.jcframe.annotation.ExcludeDBRead;
import au.com.jcloud.jcframe.annotation.ExcludeDBWrite;
import au.com.jcloud.jcframe.model.BaseBean;
import au.com.jcloud.util.Constants;

/**
 * Created by david on 17/07/16.
 */
public abstract class AuditBean extends BaseBean<Integer> {
    @ExcludeDBWrite
    protected Date cdate;
    @ExcludeDBWrite
    @ExcludeDBRead
    protected User cuser = Constants.getSystemUser();
    @ExcludeDBWrite
    protected Date mdate;
    @ExcludeDBWrite
    @ExcludeDBRead
    protected User muser = Constants.getSystemUser();

    public User getCuser() {
        return cuser;
    }

    public void setCuser(User cuser) {
        this.cuser = cuser;
    }

    public User getMuser() {
        return muser;
    }

    public void setMuser(User muser) {
        this.muser = muser;
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
}
