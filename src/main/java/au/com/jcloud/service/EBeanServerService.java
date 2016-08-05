package au.com.jcloud.service;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.H2Platform;

import org.avaje.datasource.DataSourceConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import au.com.jcloud.emodel.Server;
import au.com.jcloud.emodel.User;

/**
 * Created by david on 5/08/16.
 */
public class EBeanServerService {
    static private ServerConfig serverConfig;
    static private EbeanServer ebeanServer;

    static {
        //init();
    }

    public static void init() {
        try {
            if (ebeanServer == null) {
                serverConfig = new ServerConfig();
                serverConfig.setName("jc");
                serverConfig.setDefaultServer(true);
                Properties properties = new Properties();
                properties.setProperty("ebean.jc","au.com.jcloud.emodel.*");
                properties.setProperty("datasource.jc.username","sa");
                properties.setProperty("datasource.jc.password","");
                properties.setProperty("datasource.jc.databaseUrl","jdbc:h2:mem:tests;DB_CLOSE_DELAY=-1");
                properties.setProperty("datasource.jc.databaseDriver","org.h2.Driver");
                serverConfig.loadFromProperties(properties);
                ebeanServer = EbeanServerFactory.create(serverConfig);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

        //c.setDatabaseBooleanTrue("1");
        //c.setDatabaseBooleanFalse("0");
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
}