package au.com.javacloud.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.javacloud.dao.BaseDAO;
import au.com.javacloud.model.BaseBean;
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
    protected String insertOrEditUrl = "/";
    protected Class<T> clazz;

    public BaseController(BaseDAO<T> dao, Class<T> clazz) {
        this.dao = dao;
        this.clazz = clazz;
        String className = getClass().getSimpleName();
        if (className.toLowerCase().endsWith("controller")) {
        	beanName = className.substring(0, className.length()-"controller".length()).toLowerCase();
        	beansName = beanName+"s";
        	listUrl = "/page/"+beanName+"/list.jsp";
        	insertOrEditUrl = "/page/"+beanName+"/edit.jsp";
        }
    }
    
    public BaseController(BaseDAO<T> dao, Class<T> clazz, String beanName, String beansName, String listUrl, String insertOrEditUrl) {
        this.dao = dao;
        this.clazz = clazz;
        this.beanName = beanName;
        this.beansName = beansName;
        this.listUrl = listUrl;
        this.insertOrEditUrl = insertOrEditUrl;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = null;

        String[] pathparts = HttpUtil.getPathParts(request);
        System.out.println("pathParts="+pathparts);
        String baseUrl = HttpUtil.getBaseUrl(request);
        System.out.println("baseUrl="+baseUrl);

        if (pathparts!=null && pathparts.length>0) {
            if (pathparts[0].equals("delete")) {
            	if (pathparts.length>1) {
            		int id = Integer.parseInt(pathparts[1]);
            		dao.delete(id);            		
            	}
            } else if (pathparts[0].equals("edit")) {
            	if (pathparts.length>1) {
            		int id = Integer.parseInt(pathparts[1]);
            		try {
						request.setAttribute(beanName, dao.get(id, clazz));
						forward = insertOrEditUrl;
					} catch (Exception e) {
						e.printStackTrace();
						throw new ServletException(e);
					}
            	}
            } else if (pathparts[0].equals("insert")) {
                forward = insertOrEditUrl;
            }
        }
        if (forward==null) {
            forward = listUrl;
            try {
            	request.setAttribute(beansName, dao.getAll(clazz) );
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			}
        }
        RequestDispatcher view = request.getRequestDispatcher( forward );
        view.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	T bean = populateBean(request, response);
    	
        String id = request.getParameter("id");
        if( id == null || id.isEmpty() )
            dao.add(bean);
        else {
            bean.setId( Integer.parseInt(id) );
            dao.update(bean);
        }
        try {
        	request.setAttribute(beansName, dao.getAll(clazz) );
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
        RequestDispatcher view = request.getRequestDispatcher( listUrl );
        view.forward(request, response);
    }
    
    protected abstract T populateBean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
