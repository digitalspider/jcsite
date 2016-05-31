package au.com.javacloud.dao;

import static au.com.javacloud.util.Constants.dft;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import au.com.javacloud.model.BaseBean;
import au.com.javacloud.util.DAOUtil;
import au.com.javacloud.util.DBUtil;
import au.com.javacloud.util.ReflectUtil;

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
        excludeForSaveGetMethods.addAll(Arrays.asList(new String[] {"id", "name", "namecolumn"}));
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
                T bean = ReflectUtil.getNewBean(clazz);
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
            T bean = ReflectUtil.getNewBean(clazz);
            String columnName = bean.getNameColumn();
            statement = conn.createStatement();
            String query = "select id,"+columnName+" from "+tableName;
            if (!StringUtils.isBlank(orderBy)) {
            	query += " order by "+orderBy;
            }
            resultSet = statement.executeQuery( query );
            while( resultSet.next() ) {
                bean = ReflectUtil.getNewBean(clazz);
                bean.setId( resultSet.getInt( "id" ) );
                bean.setName( resultSet.getString( columnName ) );
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
        T bean = ReflectUtil.getNewBean(clazz);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "select * from "+tableName+" where id=?";
            if (!StringUtils.isBlank(orderBy)) {
            	query += " order by "+orderBy;
            }
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
    public Class getBeanClass() {
    	return clazz;
    }
	
	@Override
	public List<String> getBeanFieldNames() {
		return ReflectUtil.getBeanFieldNames(clazz, excludeForSaveGetMethods);
	}

	@Override
    public void populateBeanFromResultSet(T bean, ResultSet rs) throws SQLException, ParseException, InvocationTargetException, IllegalAccessException, IOException {
    	Map<Method,Class> methods = ReflectUtil.getPublicSetterMethods(clazz);
    	for (Method method : methods.keySet()) {
    		Class classType = methods.get(method);
//            System.out.println("m="+m+" paramClass.getSimpleName()="+paramClass.getSimpleName());
        	String fieldName = ReflectUtil.getFieldName(method);
        	if (ReflectUtil.isBean(classType)) {
        		// Handle BaseBeans
        		BaseDAO dao = ReflectUtil.getDaoMap().get(classType.getSimpleName().toLowerCase());
        		int id = rs.getInt(fieldName);
        		method.invoke(bean, dao.get(id));
        	} else if (classType.isInstance(new ArrayList<>())) { // FIXME
        		// Handle Collections
        		BaseDAO dao = ReflectUtil.getDaoMap().get(classType.getSimpleName().toLowerCase());
        		String values = rs.getString(fieldName);
        		String[] valueArray = values.split(",");
        		List<BaseBean> beans = new ArrayList<BaseBean>();
        		Boolean numeric = null;
        		for (String value : valueArray) {
        			if (!StringUtils.isBlank(value)) {
        				if (StringUtils.isNumeric(value)) {
        					numeric = true;
	        			} else {
	        				numeric = false;
	        				break;
	        			}
        			}
        			if (numeric!=null) {
        				BaseBean bean2 = dao.get(Integer.parseInt(value));
            			if (beans!=null) {
            				beans.add(bean2);
            			}
        			}
        		}
        		if (numeric!=null) {
        			if (numeric) {
        				method.invoke(bean, beans);
        			} else {
        				List<String> strings = new ArrayList(Arrays.asList(valueArray));
        				method.invoke(bean, strings);
        			}
        		}
        	} else {
        		// Handle primitives
        		try {
	            	switch (classType.getSimpleName()) {
	                    case "String":
	                        method.invoke(bean, rs.getString(fieldName));
	                        break;
	                    case "Integer":
	                    case "int":
	                        method.invoke(bean, rs.getInt(fieldName));
	                        break;
	                    case "Boolean":
	                        method.invoke(bean, rs.getBoolean(fieldName));
	                        break;
	                    case "Date":
	                        method.invoke(bean, dft.parse(rs.getString(fieldName)));
	                        break;
	                    case "Long":
	                        method.invoke(bean, rs.getLong(fieldName));
	                        break;
	                    case "Short":
	                        method.invoke(bean, rs.getShort(fieldName));
	                        break;
	                    case "Float":
	                        method.invoke(bean, rs.getFloat(fieldName));
	                        break;
	                    case "Double":
	                        method.invoke(bean, rs.getDouble(fieldName));
	                        break;
	                    case "BigDecimal":
	                        method.invoke(bean, rs.getBigDecimal(fieldName));
	                        break;
	                    case "Blob":
	                        method.invoke(bean, rs.getBlob(fieldName));
	                        break;
	                    case "Clob":
	                        method.invoke(bean, rs.getClob(fieldName));
	                        break;
	                }
	            } catch (SQLException e) {
	                if (!fieldName.equals("name")) { // ignore "name", as this is custom to BaseBean
	                    throw e;
	                }
	            }
        	}
    	}
    }
    
	@Override
    public PreparedStatement prepareStatementForSave(Connection conn, T bean) throws SQLException, InvocationTargetException, IllegalArgumentException, IllegalAccessException {
    	boolean updateStmt = false;
    	List<String> columns = new ArrayList<String>();
    	Map<Method,Class> methods = ReflectUtil.getPublicGetterMethods(clazz);
    	for (Method method : methods.keySet()) {
    		String fieldName = ReflectUtil.getFieldName(method);
            if (!excludeForSaveGetMethods.contains(fieldName)) {
                columns.add(fieldName);
            }
    	}
    	String query = "insert into "+tableName+" "+ DAOUtil.getInsertIntoColumnsSQL(columns);
    	if (bean.getId()>0) {
    		updateStmt = true;
        	query = "update "+tableName+" set "+DAOUtil.getUpdateColumnsSQL(columns)+" where id=?";
    	}
        System.out.println("query="+query+" columns="+columns);
        PreparedStatement preparedStatement = conn.prepareStatement( query );
        
    	int index = 0;
    	for (Method method : methods.keySet()) {
    		String fieldName = ReflectUtil.getFieldName(method);
    		Class classType = methods.get(method);
            if (!excludeForSaveGetMethods.contains(fieldName)) {
                Object result = method.invoke(bean);
                System.out.println("m="+method.getName() + " classType="+classType +" result="+result);
                if (ReflectUtil.isBean(classType)) {
                	// Handle BaseBeans
                	if (result==null) {
                		System.out.println("result.class=null");
                		preparedStatement.setInt(++index, 0);
	                } else if (result instanceof BaseBean) {
	                    System.out.println("result.class="+result.getClass().getSimpleName());
	                	preparedStatement.setInt(++index, ((BaseBean)result).getId());
	                }
                } else if (classType.isInstance(new ArrayList<>())) { // FIXME
                	// Handle Collections
                	if (result==null) {
                		System.out.println("result.class=null");
                		preparedStatement.setObject(++index, null);
	                } else {
	                    System.out.println("result.class="+result.getClass().getSimpleName());
	                    Collection c = (Collection) result;
	                    String resString = "";
	                    for (Object o : c) {
	                    	if (resString.length()>0) {
	                    		resString+=",";
	                    	}
	                    	if (o instanceof BaseBean) {
	                    		resString += ((BaseBean)o).getId();
	                    	} else {
	                    		resString += o.toString();
	                    	}
	                    }
	                	preparedStatement.setInt(++index, ((BaseBean)result).getId());
	                }
                } else {
                	// Handle primitives
                	if (result==null) {
                		System.out.println("result.class=null");
                		preparedStatement.setObject(++index, null);
                	} else {
                		System.out.println("result.class="+result.getClass().getSimpleName());
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
    	}
    	if (updateStmt) {
    		preparedStatement.setInt(++index, bean.getId());
    	}
    	return preparedStatement;
    }

    @Override
    public List<String> getExcludeForSaveGetMethods() {
        return excludeForSaveGetMethods;
    }

    @Override
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
