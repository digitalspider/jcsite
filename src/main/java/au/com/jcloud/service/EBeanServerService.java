package au.com.jcloud.service;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.ServerConfig;

/**
 * Created by david on 5/08/16.
 */
public class EBeanServerService {
    static private ServerConfig serverConfig;
    static private EbeanServer ebeanServer;

    static {
        init();
    }

    public static void init() {
        if (ebeanServer==null) {
            serverConfig = new ServerConfig();
            serverConfig.setName("jc");
            serverConfig.setDefaultServer(true);
            serverConfig.loadFromProperties();
            ebeanServer = EbeanServerFactory.create(serverConfig);
        }
    }
    public static EbeanServer getEBeanServer() {
        return ebeanServer;
    }

    public static ServerConfig getServerConfig() {
        return serverConfig;
    }
}
