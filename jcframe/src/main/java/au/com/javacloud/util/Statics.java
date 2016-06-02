package au.com.javacloud.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import au.com.javacloud.auth.AuthService;
import au.com.javacloud.auth.BaseAuthServiceImpl;
import au.com.javacloud.controller.BaseController;
import au.com.javacloud.controller.BaseControllerImpl;
import au.com.javacloud.dao.BaseDAO;
import au.com.javacloud.dao.BaseDAOImpl;
import au.com.javacloud.dao.BaseDataSource;

public class Statics {

	private static final Logger LOG = Logger.getLogger(Statics.class);

    private static final String DEFAULT_PACKAGE = "au.com.javacloud.model";
    private static final String DEFAULT_JC_CONFIG_FILE = "jc.properties";
    private static final String DEFAULT_DB_CONFIG_FILE = "db.properties";
    private static final String DEFAULT_AUTH_CLASS = "au.com.javacloud.auth.BaseAuthServiceImpl";
    private static final String DEFAULT_DS_CLASS = "au.com.javacloud.dao.BaseDataSource";
    
    private static final String PROP_PACKAGE_NAME = "package.name";
    private static final String PROP_AUTH_CLASS = "auth.class";
    private static final String PROP_DS_CLASS = "ds.class";
	private static final String PROP_DS_CONFIG_FILE = "ds.config.file";
    
    private static Map<Class,BaseDAO> daoMap = new HashMap<Class,BaseDAO>();
    private static Map<Class,BaseController> controllerMap = new HashMap<Class,BaseController>();
    private static AuthService authService;
    private static DataSource dataSource;

    static {
		try {
			Properties properties = ResourceUtil.loadProperties(DEFAULT_JC_CONFIG_FILE);
			String packageName = properties.getProperty(PROP_PACKAGE_NAME,DEFAULT_PACKAGE);
			String authClassName = properties.getProperty(PROP_AUTH_CLASS,DEFAULT_AUTH_CLASS);
			String dsClassName = properties.getProperty(PROP_DS_CLASS,DEFAULT_DS_CLASS);
			String dsPropertiesFilename = properties.getProperty(PROP_DS_CONFIG_FILE,DEFAULT_DB_CONFIG_FILE);

			Properties dbProperties = ResourceUtil.loadProperties(dsPropertiesFilename);
			try {
				LOG.info("authClassName="+authClassName);
				authService = (AuthService)Class.forName(authClassName).newInstance();
			} catch (Exception e) {
				LOG.error(e,e);
				authService = new BaseAuthServiceImpl();
			}
			LOG.info("authService="+authService);
			try {
				LOG.info("dsClassName="+dsClassName);
				dataSource = (DataSource)Class.forName(dsClassName).newInstance();
				if (dataSource instanceof BaseDataSource) {
					((BaseDataSource)dataSource).setProperties(dbProperties);
				}
			} catch (Exception e) {
				LOG.error(e,e);
				dataSource = new BaseDataSource();
				((BaseDataSource)dataSource).setProperties(dbProperties);
			}
			LOG.info("dataSource="+dataSource);
			List<Class> beanClasses = ReflectUtil.getClasses(packageName);
			for (Class classType : beanClasses) {
				if (!classType.getSimpleName().equals("BaseBean")) {
					daoMap.put(classType, new BaseDAOImpl<>(classType, dataSource));
					controllerMap.put(classType, new BaseControllerImpl<>(classType, authService));
				}
			}
		} catch(Exception e) {
			LOG.error(e,e);
		}

    }

	public static AuthService getAuthService() {
		return authService;
	}

	public static DataSource getDataSource() {
		return dataSource;
	}
    
    public static Map<Class,BaseDAO> getDaoMap() {
    	return daoMap;
    }
    
    public static Map<Class,BaseController> getControllerMap() {
    	return controllerMap;
    }
}
