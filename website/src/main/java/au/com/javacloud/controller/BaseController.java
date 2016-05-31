package au.com.javacloud.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.javacloud.dao.BaseDAO;
import au.com.javacloud.dao.BaseDAOImpl;
import au.com.javacloud.model.BaseBean;
import au.com.javacloud.model.Page;
import au.com.javacloud.model.Student;
import au.com.javacloud.model.User;
import au.com.javacloud.util.HttpUtil;

/**
 * Created by david on 22/05/16.
 */
public abstract class BaseController<T extends BaseBean> extends HttpServlet {

	private static final long serialVersionUID = -2841993759251817415L;
	protected BaseDAO<T> dao;
    protected String beanName = "bean";
    protected String beansName = "beans";
    protected String listUrl = "/";
	protected String showUrl = "/";
    protected String insertOrEditUrl = "/";

    static Map<String,BaseDAO> daoMap = new HashMap<String,BaseDAO>();
    static {
    	daoMap.put(Page.class.getSimpleName().toLowerCase(),new BaseDAOImpl<Page>(Page.class));
    	daoMap.put(Student.class.getSimpleName().toLowerCase(),new BaseDAOImpl<Student>(Student.class));
    	daoMap.put(User.class.getSimpleName().toLowerCase(),new BaseDAOImpl<User>(User.class));
    }
    
    public BaseController() {
        String className = getClass().getSimpleName();
        if (className.toLowerCase().endsWith("controller")) {
        	beanName = className.substring(0, className.length()-"controller".length()).toLowerCase();
        	dao = daoMap.get(beanName);
        	beansName = beanName+"s";
        	listUrl = "/jsp/"+beanName+"/list.jsp";
			showUrl = "/jsp/"+beanName+"/show.jsp";
        	insertOrEditUrl = "/jsp/"+beanName+"/edit.jsp";
        }
    }
    
    public BaseController(BaseDAO<T> dao, String beanName, String beansName, String listUrl, String showUrl, String insertOrEditUrl) {
        this.dao = dao;
        this.beanName = beanName;
        this.beansName = beansName;
        this.listUrl = listUrl;
		this.showUrl = showUrl;
        this.insertOrEditUrl = insertOrEditUrl;
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = null;

        String[] pathparts = HttpUtil.getPathParts(request);
//        System.out.println("pathParts="+pathparts);
        String baseUrl = HttpUtil.getBaseUrl(request);
//        System.out.println("baseUrl="+baseUrl);
    	try {
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
