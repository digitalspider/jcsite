package au.com.javacloud.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.javacloud.dao.BaseDAO;
import au.com.javacloud.model.BaseBean;
import au.com.javacloud.util.HttpUtil;
import au.com.javacloud.util.ReflectUtil;

/**
 * Created by david on 22/05/16.
 */
public abstract class BaseController<T extends BaseBean> extends HttpServlet {

	private static final long serialVersionUID = -2841993759251817415L;
	protected BaseDAO<T> dao;
    protected String beanName = "bean";
    protected String beansName = "beans";
    protected String beanHeaders = beanName+"headers";
    protected Map<String,List<BaseBean>> lookupMap = new HashMap<String, List<BaseBean>>();
    protected String listUrl = "/";
	protected String showUrl = "/";
    protected String insertOrEditUrl = "/";

    public BaseController() {
		super();
        String className = getClass().getSimpleName();
        if (className.toLowerCase().endsWith("controller")) {
        	beanName = className.substring(0, className.length()-"controller".length()).toLowerCase();
        	dao = ReflectUtil.getDaoMap().get(beanName);
        	beansName = beanName+"s";
        	beanHeaders = beanName+"headers";
        	listUrl = "/jsp/"+beanName+"/list.jsp";
			showUrl = "/jsp/"+beanName+"/show.jsp";
        	insertOrEditUrl = "/jsp/"+beanName+"/edit.jsp";
        }
    }
    
    public BaseController(BaseDAO<T> dao, String beanName, String beansName, String listUrl, String showUrl, String insertOrEditUrl) {
		super();
        this.dao = dao;
        this.beanName = beanName;
        this.beansName = beansName;
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
        			BaseDAO lookupDao = ReflectUtil.getDaoMap().get(lookupClass.getSimpleName().toLowerCase());
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

        String[] pathparts = HttpUtil.getPathParts(request);
//        System.out.println("pathParts="+pathparts);
        String baseUrl = HttpUtil.getBaseUrl(request);
//        System.out.println("baseUrl="+baseUrl);
    	try {
    		request.setAttribute(beanHeaders, dao.getBeanFieldNames() );
    		request.setAttribute("lookupMap", lookupMap );

    		if (pathparts!=null && pathparts.length>0) {
	            if (pathparts[0].equals("delete")) {
					if (pathparts.length > 1) {
						int id = Integer.parseInt(pathparts[1]);
						dao.delete(id);
					}
				} else if (pathparts[0].equals("show")) {
					if (pathparts.length>1) {
						int id = Integer.parseInt(pathparts[1]);
						request.setAttribute(beanName, dao.get(id));
						forward = showUrl;
					}
	            } else if (pathparts[0].equals("edit")) {
	            	if (pathparts.length>1) {
	            		int id = Integer.parseInt(pathparts[1]);
						request.setAttribute(beanName, dao.get(id));
						forward = insertOrEditUrl;
	            	}
	            } else if (pathparts[0].equals("insert")) {
	                forward = insertOrEditUrl;
	            }
    		}
	        if (forward==null) {
	            forward = listUrl;
	           	request.setAttribute(beansName, dao.getAll() );
	        }
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}

        RequestDispatcher view = request.getRequestDispatcher( forward );
        view.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	T bean = populateBean(request, response);
    	
        String id = request.getParameter("id");
        try {
	        if( id == null || id.isEmpty() )
	            dao.saveOrUpdate(bean);
	        else {
	            bean.setId( Integer.parseInt(id) );
	            dao.saveOrUpdate(bean);
	        }
	        request.setAttribute(beansName, dao.getAll() );
	        request.setAttribute(beanHeaders, dao.getBeanFieldNames() );
	        request.setAttribute("lookupMap", lookupMap );
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
        RequestDispatcher view = request.getRequestDispatcher( listUrl );
        view.forward(request, response);
    }
    
    protected abstract T populateBean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	public BaseDAO<T> getDao() {
		return dao;
	}

	public void setDao(BaseDAO<T> dao) {
		this.dao = dao;
	}

}
