package au.com.javacloud.dao;

import static au.com.javacloud.util.Constants.dft;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import au.com.javacloud.model.BaseBean;
import au.com.javacloud.util.DBUtil;

/**
 * Created by david on 22/05/16.
 */
public class BaseDAOImpl<T extends BaseBean> implements BaseDAO<T> {

    protected Connection conn;
    protected String tableName;
    protected Class<T> clazz;
    protected List<String> excludeForSaveGetMethods = new ArrayList<String>();
    protected String orderBy;

    public BaseDAOImpl(Class<T> clazz) {
		this.clazz = clazz;
        conn = DBUtil.getConnection();
        tableName= getTableName();
        excludeForSaveGetMethods.addAll(Arrays.asList(new String[] {"id", "name", "class", "namecolumn"}));
    }

    @Override
    public void saveOrUpdate( T bean ) throws SQLException, IOException {
    	PreparedStatement statement = null;
		try {
			statement = prepareStatementForSave(conn, bean);
			statement.executeUpdate();
		} catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException e) {
			throw new IOException(e);
		} finally {
			if (statement!=null) statement.close();
		}
    }

    @Override
    public void delete( int id ) throws SQLException {
        String query = "delete from "+tableName+" where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public List<T> getAll() throws SQLException, IOException {
        List<T> beans = new ArrayList<T>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();
            String query = "select * from "+tableName;
            if (!StringUtils.isBlank(orderBy)) {
            	query += " order by "+orderBy;
            }
            resultSet = statement.executeQuery( query );
            while( resultSet.next() ) {
                T bean = getNewBean(clazz);
				populateBeanFromResultSet(bean, resultSet);
                beans.add(bean);
            }
		} catch (InvocationTargetException | IllegalAccessException | ParseException e) {
			throw new IOException(e);
        } finally {
            if (resultSet!=null) resultSet.close();
            if (statement!=null) statement.close();
        }
        return beans;
    }
    
    public List<T> getLookup() throws SQLException, IOException {
        List<T> beans = new ArrayList<T>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();
            String query = "select id,name from "+tableName;
            if (!StringUtils.isBlank(orderBy)) {
            	query += " order by "+orderBy;
            }
            resultSet = statement.executeQuery( query );
            while( resultSet.next() ) {
                T bean = getNewBean(clazz);
                bean.setId( resultSet.getInt( "id" ) );
                bean.setName( resultSet.getString( bean.getNameColumn() ) );
                beans.add(bean);
            }
            resultSet.close();
            statement.close();
        } finally {
            if (resultSet!=null) resultSet.close();
            if (statement!=null) statement.close();
        }
        return beans;
    }
    
    @Override
    public T get(int id) throws SQLException, IOException {
        T bean = getNewBean(clazz);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "select * from "+tableName+" where id=?";
            statement = conn.prepareStatement( query );
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if( resultSet.next() ) {
                bean.setId( resultSet.getInt( "id" ) );
                populateBeanFromResultSet(bean, resultSet);
            }
            resultSet.close();
            statement.close();
        } catch (InvocationTargetException | IllegalAccessException | ParseException e) {
    		throw new IOException(e);
        } finally {
            if (resultSet!=null) resultSet.close();
            if (statement!=null) statement.close();
        }
        return bean;
    }
    
    @Override
    public List<T> find(String field, String value) throws SQLException, IOException {
    	List<T> results = new ArrayList<T>();
    	// TODO: Implement!
    	return results;
    }
    
	@Override
    public String getTableName() {
    	return clazz.getSimpleName().toLowerCase();
    }
	
	@Override
	public List<String> getBeanFieldNames() {
		Set<Method> methods = BaseDAOImpl.getPublicGetterMethods(clazz).keySet();
		List<String> beanFieldNames = new ArrayList<String>();
		for (Method method : methods) {
			beanFieldNames.add(getFieldName(method));
		}
		return beanFieldNames;
	}

	@Override
    public void populateBeanFromResultSet(T bean, ResultSet rs) throws SQLException, ParseException, InvocationTargetException, IllegalAccessException {
    	Map<Method,Class> methods = getPublicSetterMethods(clazz);
    	for (Method m : methods.keySet()) {
    		Class paramClass = methods.get(m);
//            System.out.println("m="+m+" paramClass.getSimpleName()="+paramClass.getSimpleName());
            try {
                switch (paramClass.getSimpleName()) {
                    case "String":
                        m.invoke(bean, rs.getString(getFieldName(m)));
                        break;
                    case "Integer":
                    case "int":
                        m.invoke(bean, rs.getInt(getFieldName(m)));
                        break;
                    case "Boolean":
                        m.invoke(bean, rs.getBoolean(getFieldName(m)));
                        break;
                    case "Date":
                        m.invoke(bean, dft.parse(rs.getString(getFieldName(m))));
                        break;
                    case "Long":
                        m.invoke(bean, rs.getLong(getFieldName(m)));
                        break;
                    case "Short":
                        m.invoke(bean, rs.getShort(getFieldName(m)));
                        break;
                    case "Float":
                        m.invoke(bean, rs.getFloat(getFieldName(m)));
                        break;
                    case "Double":
                        m.invoke(bean, rs.getDouble(getFieldName(m)));
                        break;
                    case "BigDecimal":
                        m.invoke(bean, rs.getBigDecimal(getFieldName(m)));
                        break;
                    case "Blob":
                        m.invoke(bean, rs.getBlob(getFieldName(m)));
                        break;
                    case "Clob":
                        m.invoke(bean, rs.getClob(getFieldName(m)));
                        break;
                }
            } catch (SQLException e) {
                if (m.getName().toLowerCase().equals("setname")) {
                    // ignore as this is custom to BaseBean
                } else {
                    throw e;
                }
            }

    	}
    }
    
	@Override
    public PreparedStatement prepareStatementForSave(Connection conn, T bean) throws SQLException, InvocationTargetException, IllegalArgumentException, IllegalAccessException {
    	boolean updateStmt = false;
    	List<String> columns = new ArrayList<String>();
    	Map<Method,Class> methods = getPublicGetterMethods(clazz);
    	for (Method m : methods.keySet()) {
            if (!excludeForSaveGetMethods.contains(getFieldName(m))) {
                columns.add(getFieldName(m));
            }
    	}
    	String query = "insert into "+tableName+" "+getInsertIntoColumnsSQL(columns);
    	if (bean.getId()>0) {
    		updateStmt = true;
        	query = "update "+tableName+" set "+getUpdateColumnsSQL(columns)+" where id=?";
    	}
        System.out.println("query="+query+" columns="+columns);
        PreparedStatement preparedStatement = conn.prepareStatement( query );
        
    	int index = 0;
        for (Method m : methods.keySet()) {
            if (!excludeForSaveGetMethods.contains(getFieldName(m))) {
                Object result = m.invoke(bean);
                if (result==null) {
                    System.out.println("m="+m.getName() + " result.class=null result="+result);
                    preparedStatement.setString(++index, null);
                } else {
                    System.out.println("m="+m.getName() + " result.class=" + result.getClass().getSimpleName()+" result="+result);
                    switch (result.getClass().getSimpleName()) {
                        case "String":
                            preparedStatement.setString(++index, (String) result);
                            break;
                        case "Integer":
                        case "int":
                            preparedStatement.setInt(++index, (Integer) result);
                            break;
                        case "Boolean":
                            preparedStatement.setBoolean(++index, (Boolean) result);
                            break;
                        case "Date":
                            preparedStatement.setString(++index, dft.format(result));
                            break;
                        case "Long":
                            preparedStatement.setLong(++index, (Long) result);
                            break;
                        case "Short":
                            preparedStatement.setShort(++index, (Short) result);
                            break;
                        case "Float":
                            preparedStatement.setFloat(++index, (Float) result);
                            break;
                        case "Double":
                            preparedStatement.setDouble(++index, (Double) result);
                            break;
                        case "BigDecimal":
                            preparedStatement.setBigDecimal(++index, (BigDecimal) result);
                            break;
                        case "Blob":
                            preparedStatement.setBlob(++index, (Blob) result);
                            break;
                        case "Clob":
                            preparedStatement.setClob(++index, (Clob) result);
                            break;
                    }
                }
    		}
    	}
    	if (updateStmt) {
    		preparedStatement.setInt(++index, bean.getId());
    	}
    	return preparedStatement;
    }
    
    public static Map<Method,Class> getPublicSetterMethods(Class<?> objectClass) {
    	Method[] allMethods = objectClass.getDeclaredMethods();
    	Map<Method,Class> setterMethods = new HashMap<Method,Class>();
        if (objectClass.getSuperclass() != null) {
            Class<?> superClass = objectClass.getSuperclass();
            Map<Method,Class> superClassMethods = getPublicSetterMethods(superClass);
            setterMethods.putAll(superClassMethods);
        }
    	for (Method method : allMethods) {
    	    if (Modifier.isPublic(method.getModifiers()) && !Modifier.isAbstract(method.getModifiers())) {
    	        if (method.getName().startsWith("set")) {
    	        	Class[] params = method.getParameterTypes();
    	        	if (params.length==1) {
    	        		setterMethods.put(method,params[0]);
    	        	}
    	        }
    	    }
    	}
    	return setterMethods;
    }
    
    public static Map<Method,Class> getPublicGetterMethods(Class<?> objectClass) {
    	Method[] allMethods = objectClass.getDeclaredMethods();
    	Map<Method,Class> getterMethods = new HashMap<Method,Class>();
        if (objectClass.getSuperclass() != null) {
            Class<?> superClass = objectClass.getSuperclass();
            Map<Method,Class> superClassMethods = getPublicGetterMethods(superClass);
            getterMethods.putAll(superClassMethods);
        }
    	for (Method method : allMethods) {
    	    if (Modifier.isPublic(method.getModifiers()) && !Modifier.isAbstract(method.getModifiers())) {
    	        if (method.getName().startsWith("get")) {
    	        	Class returnClass = method.getReturnType();
    	        	getterMethods.put(method,returnClass);
    	        }
    	    }
    	}
    	return getterMethods;
    }
    
    public static <U extends BaseBean> U getNewBean(Class<U> clazz) {
    	try {
    		return (U)clazz.newInstance();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public static String getFieldName(String methodName) {
    	String methodString = methodName.substring(3);
    	return methodString.toLowerCase();
    }
    
    public static String getFieldName(Method method) {
    	String methodString = method.getName().substring(3);
    	return methodString.toLowerCase();
    }
    

    /**
     * Creates the insert part of the SQL. e.g. (name,email,date) values (?,?,?)
     */
    public static String getInsertIntoColumnsSQL(List<String> columns) {
        String names = "";
        String params = "";
        for (String column : columns) {
            if (names.length()>0) {
                names +=", ";
                params +=", ";
            }
            names += column;
            params += "?";
        }
        return "("+names+") values ("+params+")";
    }

    /**
     * Create the update part of the SQL. e.g. name=?, email=?, date=?
     */
    public static String getUpdateColumnsSQL(List<String> columns) {
        String sql = "";
        for (String column : columns) {
            if (sql.length()>0) {
                sql +=", ";
            }
            sql += column+"=?";
        }
        return sql;
    }

    public List<String> getExcludeForSaveGetMethods() {
        return excludeForSaveGetMethods;
    }

    public void setExcludeForSaveGetMethods(List<String> excludeForSaveGetMethods) {
        this.excludeForSaveGetMethods = excludeForSaveGetMethods;
    }

    @Override
	public String getOrderBy() {
		return orderBy;
	}

    @Override
    public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
