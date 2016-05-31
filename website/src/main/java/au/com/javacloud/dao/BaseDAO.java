package au.com.javacloud.dao;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
/**
 * Created by david on 22/05/16.
 */
import java.util.List;

import au.com.javacloud.model.BaseBean;

public interface BaseDAO<T extends BaseBean> {
	
    public String getTableName();
    public void populateBeanFromResultSet(T bean, ResultSet rs) throws SQLException, ParseException, InvocationTargetException, IllegalAccessException, IOException;
    public PreparedStatement prepareStatementForSave(Connection conn, T bean) throws SQLException, InvocationTargetException, IllegalArgumentException, IllegalAccessException;
    public List<String> getBeanFieldNames();
    
    public void saveOrUpdate(T bean) throws SQLException, IOException;
    public List<T> getAll() throws SQLException, IOException;
    public List<T> getLookup() throws SQLException, IOException;
    public T get(int id) throws SQLException, IOException;
    public void delete(int beanId) throws SQLException;
    public List<T> find(String field, String value) throws SQLException, IOException;
    
    public void setOrderBy(String orderBy);
    public String getOrderBy();
}
