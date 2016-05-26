package au.com.javacloud.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.javacloud.dao.BaseDAO;
import au.com.javacloud.model.BaseBean;
import au.com.javacloud.model.Student;

/**
 * Created by david on 22/05/16.
 */
public abstract class BaseController extends HttpServlet {

    protected BaseDAO dao;
    protected String beanName = "bean";
    protected String beansName = "beans";
    protected String listUrl = "/";
    protected String insertOrEditUrl = "/";

    public BaseController(BaseDAO dao, String beanName, String beansName, String listUrl, String insertOrEditUrl) {
        this.dao = dao;
        this.beanName = beanName;
        this.beansName = beansName;
        this.listUrl = listUrl;
        this.insertOrEditUrl = insertOrEditUrl;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = null;
        String action = request.getParameter( "action" );

        if (action!=null) {
            if (action.equalsIgnoreCase("delete")) {
                forward = listUrl;
                int id = Integer.parseInt(request.getParameter("id"));
                dao.delete(id);
                request.setAttribute(beansName, dao.getAll());
            } else if (action.equalsIgnoreCase("edit")) {
                forward = insertOrEditUrl;
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute(beanName, dao.get(id));
            } else if (action.equalsIgnoreCase("insert")) {
                forward = insertOrEditUrl;
            }
        }
        if (forward==null) {
            forward = listUrl;
            request.setAttribute(beansName, dao.getAll() );
        }
        RequestDispatcher view = request.getRequestDispatcher( forward );
        view.forward(request, response);
    }
}
