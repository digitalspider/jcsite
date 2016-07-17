package au.com.jcloud.model;

import au.com.jcloud.jcframe.annotation.DisplayValueColumn;
import au.com.jcloud.jcframe.annotation.TableName;
import au.com.jcloud.jcframe.model.BaseBean;

/**
 * Created by david on 17/07/16.
 */
@TableName("OS")
@DisplayValueColumn("distribution")
public class OperatingSystem extends BaseBean<Integer> {

    private String distribution;
    private String version;
    private String architecture;

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }
}
