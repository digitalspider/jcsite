package au.com.jcloud.model;

import java.util.Date;

import au.com.jcloud.jcframe.annotation.DisplayValueColumn;

/**
 * Created by david on 17/07/16.
 */

@DisplayValueColumn("name")
public class Server extends AuditBean {

    private String name;
    private User user;
    private String lxdId;
    private String type;
    private String description;
    private String tags;
    private String status;
    private OperatingSystem os;
    private Date pdate;
    private double price;
    private double cpulimit;
    private double cpuusage;
    private double cpupeak;
    private double memlimit;
    private double memusage;
    private double mempeak;
    private double hddlimit;
    private double hddusage;
    private double hddpeak;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLxdId() {
        return lxdId;
    }

    public void setLxdId(String lxdId) {
        this.lxdId = lxdId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OperatingSystem getOs() {
        return os;
    }

    public void setOs(OperatingSystem os) {
        this.os = os;
    }

    public Date getPdate() {
        return pdate;
    }

    public void setPdate(Date pdate) {
        this.pdate = pdate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCpulimit() {
        return cpulimit;
    }

    public void setCpulimit(double cpulimit) {
        this.cpulimit = cpulimit;
    }

    public double getCpuusage() {
        return cpuusage;
    }

    public void setCpuusage(double cpuusage) {
        this.cpuusage = cpuusage;
    }

    public double getCpupeak() {
        return cpupeak;
    }

    public void setCpupeak(double cpupeak) {
        this.cpupeak = cpupeak;
    }

    public double getMemlimit() {
        return memlimit;
    }

    public void setMemlimit(double memlimit) {
        this.memlimit = memlimit;
    }

    public double getMemusage() {
        return memusage;
    }

    public void setMemusage(double memusage) {
        this.memusage = memusage;
    }

    public double getMempeak() {
        return mempeak;
    }

    public void setMempeak(double mempeak) {
        this.mempeak = mempeak;
    }

    public double getHddlimit() {
        return hddlimit;
    }

    public void setHddlimit(double hddlimit) {
        this.hddlimit = hddlimit;
    }

    public double getHddusage() {
        return hddusage;
    }

    public void setHddusage(double hddusage) {
        this.hddusage = hddusage;
    }

    public double getHddpeak() {
        return hddpeak;
    }

    public void setHddpeak(double hddpeak) {
        this.hddpeak = hddpeak;
    }
}
