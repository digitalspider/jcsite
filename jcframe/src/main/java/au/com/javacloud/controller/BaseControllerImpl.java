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
import org.apache.log4j.Logger;

import au.com.javacloud.auth.Action;
import au.com.javacloud.auth.AuthService;
import au.com.javacloud.dao.BaseDAO;
import au.com.javacloud.model.BaseBean;
import au.com.javacloud.util.HttpUtil;
import au.com.javacloud.util.ReflectUtil;
import au.com.javacloud.util.Statics;

/**
 * Created by david on 22/05/16.
 */
public class BaseControllerImpl<T extends BaseBean> extends HttpServlet implements BaseController<T> {

	private final static Logger LOG = Logger.getLogger(BaseControllerImpl.class);

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
	protected AuthService authService;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public static final String BEANS_SUFFIX = "s";
	public static final String BEANS_FIELDSUFFIX = "fields";
	public static final String LOOKUPMAP = "lookupMap";
	public static final String BASEURL = "baseUrl";
	public static final String DEFAULT_JSPPAGE_PREFIX = "/jsp/";
	public static final String DEFAULT_LIST_PAGE = "/list.jsp";
	public static final String DEFAULT_SHOW_PAGE = "/show.jsp";
	public static final String DEFAULT_EDIT_PAGE = "/edit.jsp";

	public BaseControllerImpl(Class<T> clazz) {
		this(clazz, Statics.getAuthService());
	}

    public BaseControllerImpl(Class<T> clazz, AuthService authService) {
		super();
		this.clazz = clazz;
		this.authService = authService;
		dao = Statics.getDaoMap().get(clazz);
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
		dao.init(getServletConfig());
    	Map<Method,Class> fieldMethods = ReflectUtil.getPublicSetterMethods(dao.getBeanClass());
    	for (Method method : fieldMethods.keySet()) {
    		Class lookupClass = fieldMethods.get(method);
			LOG.debug("lookupClass="+lookupClass.getName());
    		if (ReflectUtil.isBean(lookupClass)) {
    			try {
        			BaseDAO lookupDao = Statics.getDaoMap().get(lookupClass);
        			String fieldName = ReflectUtil.getFieldName(method);
					LOG.debug("fieldName="+fieldName+" lookupDao="+lookupDao);
        			lookupMap.put(fieldName,lookupDao.getLookup());
    			} catch (Exception e) {
					LOG.error(e,e);
    			}
    		}
    	}
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.info("doGet() START");
		this.request = request;
		this.response = response;

        pathParts = HttpUtil.getPathParts(request);
        LOG.info("pathParts="+pathParts);
        baseUrl = HttpUtil.getBaseUrl(request);
        LOG.info("baseUrl="+baseUrl);

		String forward = null;

    	try {
    		request.setAttribute(beanName+BEANS_FIELDSUFFIX, dao.getBeanFieldNames() );
    		request.setAttribute(LOOKUPMAP, lookupMap );
			request.setAttribute(BASEURL, baseUrl );

			if (pathParts!=null && pathParts.length>0) {
				if (pathParts[0].equals("delete")) {
					LOG.info("action=delete");
					if (authService.checkACL(authService.getUser(request), this.clazz, Action.DELETE)) {
						delete();
					}
				} else if (pathParts[0].equals("edit")) {
					LOG.info("action=edit");
					if (authService.checkACL(authService.getUser(request), this.clazz, Action.READ)) {
						read();
						forward = insertOrEditUrl;
					}
				} else if (pathParts[0].equals("show")) {
					LOG.info("action=show");
					if (authService.checkACL(authService.getUser(request), this.clazz, Action.READ)) {
						read();
						forward = showUrl;
					}
				} else if (StringUtils.isNumeric(pathParts[0])) {
					LOG.info("action=<int>");
					if (authService.checkACL(authService.getUser(request), this.clazz, Action.READ)) {
						read();
						forward = showUrl;
					}
				} else if (pathParts[0].equals("insert")) {
					LOG.info("action=insert");
					if (authService.checkACL(authService.getUser(request), this.clazz, Action.CREATE)) {
						forward = insertOrEditUrl;
					}
				} else if (pathParts[0].equals("find")) {
					LOG.info("action=find");
					if (authService.checkACL(authService.getUser(request), this.clazz, Action.FIND)) {
						find();
						forward = listUrl;
					}
				} else if (pathParts[0].equals("config")) {
					LOG.info("action=config");
					if (authService.checkACL(authService.getUser(request), this.clazz, Action.CONFIG)) {
						config();
					}
				} else if (pathParts[0].equals("p")) {
					LOG.info("action=p");
					if (authService.checkACL(authService.getUser(request), this.clazz, Action.READ)) {
						forward = listUrl;
						int pageNo = getNumberFromPathParts(1);
						request.setAttribute(beanName+BEANS_SUFFIX, dao.getAll(pageNo) );
					}
				}
			}
	        if (forward==null) {
				LOG.info("action=list");
	            forward = listUrl;
	           	request.setAttribute(beanName+BEANS_SUFFIX, dao.getAll() );
	        }
		} catch (Exception e) {
			LOG.error(e,e);
			throw new ServletException(e);
		}

        RequestDispatcher view = request.getRequestDispatcher( forward );
		LOG.info("doGet() DONE. view="+view);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.info("doPost() START");
		this.request = request;
		this.response = response;

		pathParts = HttpUtil.getPathParts(request);
		LOG.info("pathParts="+pathParts);
		baseUrl = HttpUtil.getBaseUrl(request);
		LOG.info("baseUrl="+baseUrl);

		try {
			String id = request.getParameter("id");
			if( id == null || id.isEmpty() ) {
				LOG.info("action=create");
				if (authService.checkACL(authService.getUser(request), this.clazz, Action.CREATE)) {
					create();
				}
			} else {
				LOG.info("action=update("+id+")");
				if (authService.checkACL(authService.getUser(request), this.clazz, Action.UPDATE)) {
					update(id);
				}
			}

			request.setAttribute(beanName+BEANS_SUFFIX, dao.getAll() );
			request.setAttribute(beanName+BEANS_FIELDSUFFIX, dao.getBeanFieldNames() );
			request.setAttribute(LOOKUPMAP, lookupMap );
			request.setAttribute(BASEURL, baseUrl );
		} catch (Exception e) {
			LOG.error(e,e);
			throw new ServletException(e);
		}
		RequestDispatcher view = request.getRequestDispatcher( listUrl );
		LOG.info("doPost() DONE. view="+view);
		view.forward(request, response);
	}

	protected T populateBean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				LOG.error(e,e);
			}
		}
		return bean;
	}

	protected HttpServletRequest getRequest() {
		return request;
	}

	protected HttpServletResponse getResponse() {
		return response;
	}

    @Override
    public void create() throws Exception {
		T bean = populateBean(request, response);
		dao.saveOrUpdate(bean);
    }

    @Override
    public void read() throws Exception {
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

	private int getNumberFromPathParts(int pathPartIndex) throws Exception {
		if (pathParts.length>pathPartIndex && StringUtils.isNumeric(pathParts[pathPartIndex])) {
			return Integer.parseInt(pathParts[pathPartIndex]);
		}
		return 0;
	}

    @Override
    public void update(String id) throws Exception {
		T bean = populateBean(request, response);
		bean.setId( Integer.parseInt(id) );
		dao.saveOrUpdate(bean);
    }

    @Override
    public void delete()  throws Exception {
		int id = getNumberFromPathParts(1);
		if (id>0) {
			dao.delete(id);
		}
    }

	@Override
	public void find()  throws Exception {
		if (pathParts.length > 2) {
			String field = pathParts[1];
			String value = pathParts[2];
			int pageNo = getNumberFromPathParts(3);
			request.setAttribute(beanName + BEANS_SUFFIX, dao.find(field, value, pageNo));
		}
	}

	@Override
	public void config()  throws Exception {
		if (pathParts.length>1) {
			if (pathParts[1].equals("order")) {
				dao.setOrderBy("");
				if (pathParts.length > 2) {
					String field = pathParts[2];
					dao.setOrderBy(field);
					if (pathParts.length > 3) {
						String direction = pathParts[3];
						if (direction.equalsIgnoreCase("ASC") || direction.equalsIgnoreCase("DESC")) {
							dao.setOrderBy(field + " " + direction);
						}
					}
				}
			}
			if (pathParts[1].equals("limit")) {
				int limit = getNumberFromPathParts(2);
				dao.setLimit(limit);
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

	@Override
	public AuthService getAuthService() {
		return authService;
	}

	@Override
	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}

}
