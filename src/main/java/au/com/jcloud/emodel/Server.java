package au.com.jcloud.emodel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by david on 5/08/16.
 */
@Entity
@Table(name="server")
public class Server extends BaseBean {
    @Id
    private Long id;
    protected String ip;
    protected String alias;
    protected String os;
    protected String architecture;
    protected String osversion;
    protected String memMax;
    protected String memPeak;
    protected String memcurrent;
    protected String hddMax;
    protected String hddPeak;
    protected String hddCurrent;
    protected String sshkey;
    protected int parentServerId;

    @Override
    public String toString() {
        return super.toString()+" ip="+ip+" alias="+alias+" os="+os;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public String getOsversion() {
        return osversion;
    }

    public void setOsversion(String osversion) {
        this.osversion = osversion;
    }

    public String getMemMax() {
        return memMax;
    }

    public void setMemMax(String memMax) {
        this.memMax = memMax;
    }

    public String getMemPeak() {
        return memPeak;
    }

    public void setMemPeak(String memPeak) {
        this.memPeak = memPeak;
    }

    public String getMemcurrent() {
        return memcurrent;
    }

    public void setMemcurrent(String memcurrent) {
        this.memcurrent = memcurrent;
    }

    public String getHddMax() {
        return hddMax;
    }

    public void setHddMax(String hddMax) {
        this.hddMax = hddMax;
    }

    public String getHddPeak() {
        return hddPeak;
    }

    public void setHddPeak(String hddPeak) {
        this.hddPeak = hddPeak;
    }

    public String getHddCurrent() {
        return hddCurrent;
    }

    public void setHddCurrent(String hddCurrent) {
        this.hddCurrent = hddCurrent;
    }

    public String getSshkey() {
        return sshkey;
    }

    public void setSshkey(String sshkey) {
        this.sshkey = sshkey;
    }

    public int getParentServerId() {
        return parentServerId;
    }

    public void setParentServerId(int parentServerId) {
        this.parentServerId = parentServerId;
    }
}
