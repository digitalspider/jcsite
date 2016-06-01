package au.com.javacloud.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import au.com.javacloud.dao.BaseDAO;
import au.com.javacloud.model.BaseBean;
import au.com.javacloud.util.HttpUtil;
import au.com.javacloud.util.ReflectUtil;

/**
 * Created by david on 22/05/16.
 */
public class BaseControllerImpl<T extends BaseBean> extends HttpServlet implements BaseController<T> {

	private static final long serialVersionUID = -2841993759251817415L;
	protected BaseDAO<T> dao;
	protected Class<T> clazz;
    protected String beanName = "bean";
    protected Map<String,List<BaseBean>> lookupMap = new HashMap<String, List<BaseBean>>();
    protected String listUrl = "/";
	protected String showUrl = "/";
    protected String insertOrEditUrl = "/";
    protected String baseUrl;
    protected String[] pathParts = new String[0];
	protected DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static final String BEANS_SUFFIX = "s";
    public static final String BEANS_FIELDSUFFIX = "fields";
    public static final String LOOKUPMAP = "lookupMap";
    public static final String DEFAULT_JSPPAGE_PREFIX = "/jsp/";
    public static final String DEFAULT_LIST_PAGE = "/list.jsp";
    public static final String DEFAULT_SHOW_PAGE = "/show.jsp";
    public static final String DEFAULT_EDIT_PAGE = "/edit.jsp";

    public BaseControllerImpl(Class<T> clazz) {
		super();
		this.clazz = clazz;
    	dao = ReflectUtil.getDaoMap().get(clazz);
		updateUrls(clazz.getSimpleName().toLowerCase());
    }

	protected void updateUrls(String contextName) {
		updateUrls(DEFAULT_JSPPAGE_PREFIX,contextName);
	}

    protected void updateUrls(String prefix, String contextName) {
    	this.listUrl = prefix+contextName+DEFAULT_LIST_PAGE;
    	this.showUrl = prefix+contextName+DEFAULT_SHOW_PAGE;
    	this.insertOrEditUrl = prefix+contextName+DEFAULT_EDIT_PAGE;
    }
    
    public BaseControllerImpl(BaseDAO<T> dao, String beanName, String beansName, String listUrl, String showUrl, String insertOrEditUrl) {
		super();
        this.dao = dao;
        this.beanName = beanName;
        this.listUrl = listUrl;
		this.showUrl = showUrl;
        this.insertOrEditUrl = insertOrEditUrl;
	}

	@Override
    public void init() throws ServletException {
    	super.init();
    	Map<Method,Class> fieldMethods = ReflectUtil.getPublicSetterMethods(dao.getBeanClass());
    	for (Method method : fieldMethods.keySet()) {
    		Class lookupClass = fieldMethods.get(method);
//			System.out.println("lookupClass="+lookupClass.getName());
    		if (ReflectUtil.isBean(lookupClass)) {
    			try {
        			BaseDAO lookupDao = ReflectUtil.getDaoMap().get(lookupClass);
        			String fieldName = ReflectUtil.getFieldName(method);
//					System.out.println("fieldName="+fieldName+" lookupDao="+lookupDao);
        			lookupMap.put(fieldName,lookupDao.getLookup());
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = null;

        pathParts = HttpUtil.getPathParts(request);
//        System.out.println("pathParts="+pathParts);
        baseUrl = HttpUtil.getBaseUrl(request);
//        System.out.println("baseUrl="+baseUrl);
    	try {
    		request.setAttribute(beanName+BEANS_FIELDSUFFIX, dao.getBeanFieldNames() );
    		request.setAttribute(LOOKUPMAP, lookupMap );

    		if (pathParts!=null && pathParts.length>0) {
	            if (pathParts[0].equals("delete")) {
	            	delete(request, response);
	            } else if (pathParts[0].equals("edit")) {
	            	read(request, response);
	            	forward = insertOrEditUrl;
				} else if (pathParts[0].equals("show")) {
					read(request, response);
					forward = showUrl;
				} else if (StringUtils.isNumeric(pathParts[0])) {
					read(request, response);
					forward = showUrl;
	            } else if (pathParts[0].equals("insert")) {
					forward = insertOrEditUrl;
				} else if (pathParts[0].equals("find")) {
					find(request, response);
					forward = listUrl;
				} else if (pathParts[0].equals("order")) {
					order(request, response);
				}
    		}
	        if (forward==null) {
	            forward = listUrl;
	           	request.setAttribute(beanName+BEANS_SUFFIX, dao.getAll() );
	        }
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}

        RequestDispatcher view = request.getRequestDispatcher( forward );
        view.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pathParts = HttpUtil.getPathParts(request);
        baseUrl = HttpUtil.getBaseUrl(request);
        try {
        	String id = request.getParameter("id");
            if( id == null || id.isEmpty() ) {
            	create(request, response);
            } else {
            	update(id, request, response);
            }

	        request.setAttribute(beanName+BEANS_SUFFIX, dao.getAll() );
	        request.setAttribute(beanName+BEANS_FIELDSUFFIX, dao.getBeanFieldNames() );
	        request.setAttribute(LOOKUPMAP, lookupMap );
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
        RequestDispatcher view = request.getRequestDispatcher( listUrl );
        view.forward(request, response);
    }
    
    @Override
    public T populateBean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	T bean = ReflectUtil.getNewBean(clazz);
    	Map<Method,Class> methods = ReflectUtil.getPublicSetterMethods(clazz);
    	for (Method method : methods.keySet()) {
    		Class classType = methods.get(method);
    		String fieldName = ReflectUtil.getFieldName(method);
    		try {
    			String value = request.getParameter( fieldName );
    			
            	if (ReflectUtil.isBean(classType)) {
            		// Handle BaseBeans
                    if (StringUtils.isNumeric(value)) {
						int id = Integer.parseInt(value);
						ReflectUtil.invokeSetterMethodForBeanType(bean, method, classType, id);
                    }
            	} else if (ReflectUtil.isCollection(classType)) {
            		// Handle Collections
					String[] valueArray = value.split(",");
					ReflectUtil.invokeSetterMethodForCollection(bean, method, classType, value);
            	} else {
            		// Handle primitives
					ReflectUtil.invokeSetterMethodForPrimitive(bean, method, classType, value, dateFormat);
            	}
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	return bean;
    }
    
    @Override
    public void create(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	T bean = populateBean(request, response);
        dao.saveOrUpdate(bean);
    }
    
    @Override
    public void read(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String idValue = null;
		if (StringUtils.isNumeric(pathParts[0])) {
			idValue = pathParts[0];
		} else if (pathParts.length>1 && StringUtils.isNumeric(pathParts[1])) {
			idValue = pathParts[1];
		}
		if (idValue!=null) {
			int id = Integer.parseInt(idValue);
			request.setAttribute(beanName, dao.get(id));
		}
    }
    
    @Override
    public void update(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        T bean = populateBean(request, response);
        bean.setId( Integer.parseInt(id) );
        dao.saveOrUpdate(bean);
    }
    
    @Override
    public void delete(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		if (pathParts.length > 1) {
			int id = Integer.parseInt(pathParts[1]);
			dao.delete(id);
		}
    }

	@Override
	public void find(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		if (pathParts.length > 2) {
			String field = pathParts[1];
			String value = pathParts[2];
			request.setAttribute(beanName+BEANS_SUFFIX, dao.find(field,value) );
		}
	}

	@Override
	public void order(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		dao.setOrderBy("");
		if (pathParts.length > 1) {
			String field = pathParts[1];
			dao.setOrderBy(field);
			if (pathParts.length > 2) {
				String direction = pathParts[2];
				if (direction.equalsIgnoreCase("ASC") || direction.equalsIgnoreCase("DESC")) {
					dao.setOrderBy(field+" "+direction);
				}
			}
		}
	}

	public BaseDAO<T> getDao() {
		return dao;
	}

	public void setDao(BaseDAO<T> dao) {
		this.dao = dao;
	}

	@Override
	public String getBeanName() {
		return beanName;
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	@Override
	public Class<T> getBeanClass() {
		return clazz;
	}

	@Override
	public DateFormat getDateFormat() {
		return dateFormat;
	}

	@Override
	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
		dao.setDateFormat(dateFormat);
	}

}
