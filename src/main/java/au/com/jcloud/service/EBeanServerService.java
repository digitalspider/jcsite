package au.com.jcloud.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.avaje.datasource.DataSourceConfig;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.H2Platform;

import au.com.jcloud.model.Server;
import au.com.jcloud.model.User;
import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 * Created by david.vittor on 5/08/16.
 */
public class EBeanServerService extends BaseService {
	public static final String EBEAN_PROPERTIES = "ebean.properties";
	public static final String EBEAN_PROPERTIES_TEST = "test-ebean.properties";

	private static ServerConfig serverConfig;
	private static EbeanServer ebeanServer;

	@SpringBean
	private PropertyReaderService propertyReaderService;

	public EBeanServerService() {
		this(false);
	}

	public EBeanServerService(boolean isTest) {
		if (isTest) {
			init(EBEAN_PROPERTIES_TEST);
		} else {
			init(EBEAN_PROPERTIES);
		}
	}

	public EBeanServerService(String properiesFile) {
		init(properiesFile);
	}

	public void init(String propertiesFile) {
		try {
			if (ebeanServer == null) {
				serverConfig = new ServerConfig();
				serverConfig.setName("jc");
				serverConfig.setDefaultServer(true);
				Properties properties = propertyReaderService.loadProperties(propertiesFile);
				serverConfig.loadFromProperties(properties);
				ebeanServer = EbeanServerFactory.create(serverConfig);
			}
		} catch (Exception e) {
			LOG.error("ERROR initialising properties file: " + propertiesFile + " " + e.getMessage(), e);
		}
	}

	public static EbeanServer createEbeanServer() {

		List<Class<?>> beanClasses = new ArrayList<Class<?>>();
		beanClasses.add(User.class);
		beanClasses.add(Server.class);
		System.setProperty("ebean.ignoreExtraDdl", "true");

		ServerConfig c = new ServerConfig();
		c.setName("jc");

		// requires postgres driver in class path
		DataSourceConfig tsetDb = new DataSourceConfig();
		tsetDb.setDriver("org.h2.Driver");
		tsetDb.setUsername("sa");
		tsetDb.setPassword("");
		tsetDb.setUrl("jdbc:h2:mem:tests;DB_CLOSE_DELAY=-1");
		tsetDb.setHeartbeatSql("select 1");

		c.loadFromProperties();
		c.setDdlGenerate(true);
		c.setDdlRun(true);
		c.setDefaultServer(false);
		c.setRegister(false);
		c.setDataSourceConfig(tsetDb);
		c.setClasses(beanClasses);

		// c.setDatabaseBooleanTrue("1");
		// c.setDatabaseBooleanFalse("0");
		c.setDatabaseBooleanTrue("T");
		c.setDatabaseBooleanFalse("F");

		c.setDatabasePlatform(new H2Platform());

		// c.addClass(User.class);

		return EbeanServerFactory.create(c);
	}

	public static EbeanServer getEBeanServer() {
		return ebeanServer;
	}

	public static ServerConfig getServerConfig() {
		return serverConfig;
	}

	protected void setPropertyReaderService(PropertyReaderService propertyReaderService) {
		this.propertyReaderService = propertyReaderService;
	}
}
