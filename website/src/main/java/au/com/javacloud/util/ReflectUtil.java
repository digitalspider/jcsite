package au.com.javacloud.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import au.com.javacloud.controller.BaseController;
import au.com.javacloud.controller.BaseControllerImpl;
import au.com.javacloud.dao.BaseDAO;
import au.com.javacloud.dao.BaseDAOImpl;
import au.com.javacloud.model.BaseBean;

public class ReflectUtil {

    private static Map<Class,BaseDAO> daoMap = new HashMap<Class,BaseDAO>();
    private static Map<Class,BaseController> controllerMap = new HashMap<Class,BaseController>();
    static {
//        Reflections reflections = new Reflections("au.com.javacloud.model");
//        Set<Class<? extends BaseBean>> beanClasses =  reflections.getSubTypesOf(BaseBean.class);
//
		try {
			List<Class> beanClasses = getClasses("au.com.javacloud.model");
			for (Class classType : beanClasses) {
				if (!classType.getSimpleName().equals("BaseBean")) {
					daoMap.put(classType, new BaseDAOImpl<>(classType));
					controllerMap.put(classType, new BaseControllerImpl<>(classType));
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

//    	daoMap.put(Page.class,new BaseDAOImpl<Page>(Page.class));
//    	daoMap.put(Student.class,new BaseDAOImpl<Student>(Student.class));
//    	daoMap.put(User.class,new BaseDAOImpl<User>(User.class));
    }
    
    public static Map<Class,BaseDAO> getDaoMap() {
    	return daoMap;
    }
    
    public static Map<Class,BaseController> getControllerMap() {
    	return controllerMap;
    }

    public static boolean isBean(Class clazz) {
        if (BaseBean.class.isAssignableFrom(clazz)) {
        	if (clazz!=BaseBean.class) {
        		return true;
        	}
        }
        return false;
    }

    public static Map<Method,Class> getPublicSetterMethods(Class<?> objectClass) {
    	Method[] allMethods = objectClass.getDeclaredMethods();
    	Map<Method,Class> setterMethods = new HashMap<Method,Class>();
//        System.out.println("set objectClass="+objectClass+" super="+objectClass.getSuperclass());
        if (objectClass.getSuperclass() != null && ReflectUtil.isBean(objectClass.getSuperclass())) {
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
//        System.out.println("setters="+setterMethods);
    	return setterMethods;
    }
    
    public static Map<Method,Class> getPublicGetterMethods(Class<?> objectClass) {
    	Method[] allMethods = objectClass.getDeclaredMethods();
    	Map<Method,Class> getterMethods = new HashMap<Method,Class>();
//        System.out.println("get objectClass="+objectClass+" super="+objectClass.getSuperclass());
        if (objectClass.getSuperclass() != null && ReflectUtil.isBean(objectClass.getSuperclass())) {
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
//        System.out.println("getters="+getterMethods);
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
    
	public static <U extends BaseBean> List<String> getBeanFieldNames(Class<U> clazz, List<String> excludeValues) {
		Set<Method> methods = ReflectUtil.getPublicGetterMethods(clazz).keySet();
		List<String> beanFieldNames = new ArrayList<String>();
		for (Method method : methods) {
            String fieldName = ReflectUtil.getFieldName(method);
            if (!excludeValues.contains(fieldName)) {
                beanFieldNames.add(fieldName);
            }
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
	 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
	 *
	 * @param packageName The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static List<Class> getClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		List<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes;
	}

	/**
	 * Recursive method used to find all classes in a given directory and subdirs.
	 *
	 * @param directory   The base directory
	 * @param packageName The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	public static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {

		List<Class> classes = new ArrayList<Class>();

		if (!directory.exists()) {
			return classes;
		}

		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}
}
