package au.com.jcloud.actionbean;

import com.avaje.ebean.Ebean;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.security.PermitAll;

import au.com.jcloud.WebConstants;
import au.com.jcloud.enums.Status;
import au.com.jcloud.model.Address;
import au.com.jcloud.model.OperatingSystem;
import au.com.jcloud.model.Purchase;
import au.com.jcloud.model.Server;
import au.com.jcloud.model.User;
import au.com.jcloud.util.PathParts;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontBind;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;

import static au.com.jcloud.actionbean.ServerActionBean.JSP_BINDING;

/**
 * Created by david.vittor on 3/08/16.
 */
@PermitAll
@UrlBinding(WebConstants.PATH_SECURE+JSP_BINDING)
public class ServerActionBean extends JCSecureActionBean {

	public static final String JSP_BINDING = "/server";

	@Validate(required = true, minlength = 3)
	private String hostname;
	@Validate(required = true)
	private boolean terms;
	@Validate(required = true)
	private String memory;
	@Validate(required = true)
	private String disk;
	@Validate(required = true)
	private String backups;
	@Validate(required = true)
	private String cores;
	@Validate(required = true)
	private String os;
	@Validate(required = true)
	private String dbserver;
	@Validate(required = true)
	private String appserver;
	@Validate(required = true)
	private String webserver;
	@Validate(required = true)
	private String application;
	@Validate(required = true)
	private String support;

	private List<Server> servers;

	@PermitAll
	@DontValidate
	@DontBind
	@DefaultHandler
	@Override
	public Resolution action() {
		User user = getUser();
		servers = user.getServers();
		if (servers.isEmpty()) {
			servers = Ebean.find(Server.class).where().eq("user_id",user.getId()).findList();
		}

		PathParts pathParts = getPathParts(); // 0=server, 1=123 2=start
		LOG.info("pathParts="+pathParts);
		if (pathParts.size()>1) {
			if (pathParts.get(1).equals("add")) {
				LOG.info("go add = " + getEditResolution());
				return getEditResolution();
			}
			if (pathParts.isNumeric(1) && pathParts.size()>2) {
				int serverId = pathParts.getInt(1);
				if (pathParts.get(2).equals("view")) {
					LOG.info("view server " + serverId);
					return getShowResolution();
				}
				else if (pathParts.get(2).equals("start")) {
					LOG.info("start server " + serverId);
				}
				else if (pathParts.get(2).equals("stop")) {
					LOG.info("stop server " + serverId);
				}
				else if (pathParts.get(2).equals("restart")) {
					LOG.info("restart server " + serverId);
				}
				else if (pathParts.get(2).equals("service")) {
					LOG.info("service on server " + serverId);
				}
			}

		}
		return getDefaultResolution();
	}


	@HandlesEvent("addserver")
	public Resolution addServer() throws Exception {
		User user = getUser();
		Server server = new Server();
		try {
			OperatingSystem os = Ebean.find(OperatingSystem.class).where().idEq(1).findUnique();
			Purchase purchase = Ebean.find(Purchase.class).where().idEq(1).findUnique();
			server.setUser(user);
			server.setName(hostname);
			server.setAlias(hostname);
			server.setOs(os);
			server.setPurchase(purchase);
			server.setHddLimit(Double.valueOf(disk));
			server.setCpuLimit(Double.valueOf(cores));
			server.setMemLimit(Double.valueOf(memory));
			server.setStatus(Status.ENABLED.value());
			server.setDescription("os="+os+", appserver="+appserver+", dbserver="+dbserver+", webserver="+webserver+", bkup="+backups+", support="+support+", app="+application);
			Ebean.save(server);

			return getListResolution();
		} catch (Exception e) {
			LOG.error("Error saving server "+server+" for user "+user+". "+e,e);
		}
		return getEditResolution();
	}

	public String getJSPBinding() {
		return JSP_BINDING;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public boolean isTerms() {
		return terms;
	}

	public void setTerms(boolean terms) {
		this.terms = terms;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}

	public String getBackups() {
		return backups;
	}

	public void setBackups(String backups) {
		this.backups = backups;
	}

	public String getCores() {
		return cores;
	}

	public void setCores(String cores) {
		this.cores = cores;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getDbserver() {
		return dbserver;
	}

	public void setDbserver(String dbserver) {
		this.dbserver = dbserver;
	}

	public String getAppserver() {
		return appserver;
	}

	public void setAppserver(String appserver) {
		this.appserver = appserver;
	}

	public String getWebserver() {
		return webserver;
	}

	public void setWebserver(String webserver) {
		this.webserver = webserver;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}
}
