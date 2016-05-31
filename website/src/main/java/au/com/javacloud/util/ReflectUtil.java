package au.com.javacloud.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import au.com.javacloud.dao.BaseDAO;
import au.com.javacloud.dao.BaseDAOImpl;
import au.com.javacloud.model.BaseBean;
import au.com.javacloud.model.Page;
import au.com.javacloud.model.Student;
import au.com.javacloud.model.User;

public class ReflectUtil {

    private static Map<String,BaseDAO> daoMap = new HashMap<String,BaseDAO>();
    static {
    	daoMap.put(Page.class.getSimpleName().toLowerCase(),new BaseDAOImpl<Page>(Page.class));
    	daoMap.put(Student.class.getSimpleName().toLowerCase(),new BaseDAOImpl<Student>(Student.class));
    	daoMap.put(User.class.getSimpleName().toLowerCase(),new BaseDAOImpl<User>(User.class));
    }
    
    public static Map<String,BaseDAO> getDaoMap() {
    	return daoMap;
    }
    
    public static Map<Method,Class> getPublicSetterMethods(Class<?> objectClass) {
    	Method[] allMethods = objectClass.getDeclaredMethods();
    	Map<Method,Class> setterMethods = new HashMap<Method,Class>();
        if (objectClass.getSuperclass() != null && objectClass.getSuperclass().isInstance(BaseBean.class)) {
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
        if (objectClass.getSuperclass() != null && objectClass.getSuperclass().isInstance(BaseBean.class)) {
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
    
	public static <U extends BaseBean> List<String> getBeanFieldNames(Class<U> clazz) {
		Set<Method> methods = ReflectUtil.getPublicGetterMethods(clazz).keySet();
		List<String> beanFieldNames = new ArrayList<String>();
		for (Method method : methods) {
			beanFieldNames.add(ReflectUtil.getFieldName(method));
		}
		return beanFieldNames;
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
}
