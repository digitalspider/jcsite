package au.com.javacloud.controller;

import java.io.IOException;
import java.text.DateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.javacloud.auth.AuthService;
import au.com.javacloud.dao.BaseDAO;
import au.com.javacloud.model.BaseBean;

/**
 * Created by david on 22/05/16.
 */
public interface BaseController<T extends BaseBean> {

    public void create(HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    public void read(HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    public void update(String id, HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    public T populateBean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    public Class<T> getBeanClass();

    public String getBeanName();
    public void setBeanName(String name);

	public BaseDAO<T> getDao();
	public void setDao(BaseDAO<T> dao);

    public DateFormat getDateFormat();
    public void setDateFormat(DateFormat dateFormat);
    
    public AuthService getAuthService();
    public void setAuthService(AuthService auth);

}
