package au.com.javacloud.dao;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
/**
 * Created by david on 22/05/16.
 */
import java.util.List;

import au.com.javacloud.model.BaseBean;

public interface BaseDAO<T extends BaseBean> {
	
    public String getTableName();
    public Class<T> getBeanClass();
    public void populateBeanFromResultSet(T bean, ResultSet rs) throws Exception;
    public PreparedStatement prepareStatementForSave(Connection conn, T bean) throws Exception;
    public List<String> getBeanFieldNames();
    
    public void saveOrUpdate(T bean) throws Exception;
    public List<T> getAll() throws Exception;
    public List<T> getLookup() throws Exception;
    public T get(int id) throws Exception;
    public void delete(int beanId) throws Exception;
    public List<T> find(String field, String value) throws Exception;
    
    public void setOrderBy(String orderBy);
    public String getOrderBy();

    public List<String> getExcludeForSaveGetMethods();
    public void setExcludeForSaveGetMethods(List<String> excludeForSaveGetMethods);

    public DateFormat getDateFormat();
    public void setDateFormat(DateFormat dateFormat);
}