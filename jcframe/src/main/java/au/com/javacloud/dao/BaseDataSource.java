package au.com.javacloud.dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class BaseDataSource implements DataSource {

	private Properties properties;
	
	public BaseDataSource(Properties properties) {
		this.properties = properties;
	}
	
    private static Connection conn;
    
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BaseDataSource.class);
    
    private static final String PROP_DRIVER = "driver";
    private static final String PROP_URL = "url";
    private static final String PROP_USER = "user";
    private static final String PROP_PASSWORD = "password";

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
    	try {
	        if( conn != null && !conn.isClosed()) {
	            return conn;
	        }
    	} catch ( Exception e ) {
    		// ignore
    	}

        String username = properties.getProperty( PROP_USER );
        String password = properties.getProperty( PROP_PASSWORD );
        return getConnection(username,password);
	}            


	@Override
	public Connection getConnection(String username, String password) throws SQLException {
    	try {
	        if( conn != null && !conn.isClosed()) {
	            return conn;
	        }
    	} catch ( Exception e ) {
    		// ignore
    	}

    	try {
	        String driver = properties.getProperty( PROP_DRIVER );
	        String url = properties.getProperty( PROP_URL );
	        Class.forName( driver );
	        conn = DriverManager.getConnection( url, username, password );
        } catch (Exception e) {
            LOG.error(e,e);
        }

        return conn;
	}


}