package au.com.javacloud.controller;

/**
 * Created by david on 22/05/16.
 */

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.javacloud.dao.PageDAOImpl;
import au.com.javacloud.model.Page;

@WebServlet("/page")
public class PageController extends BaseController {

    public static final String URL_lIST = "/page/page/list.jsp";
    public static final String URL_INSERT_OR_EDIT = "/page/page/edit.jsp";
    public static final String PROP_BEANNAME = "page";
    public static final String PROP_BEANSNAME = "pages";

    public PageController() {
        super(new PageDAOImpl(), PROP_BEANNAME, PROP_BEANSNAME, URL_lIST, URL_INSERT_OR_EDIT);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Page page = new Page();
        page.setTitle( request.getParameter( "title" ) );
        page.setDescription( request.getParameter( "description" ) );
        page.setContent( request.getParameter( "content" ) );
        page.setUrl( request.getParameter( "url" ) );
        page.setType( request.getParameter( "type" ) );
        page.setTags( request.getParameter( "tags" ) );
        page.setStatus( request.getParameter( "status" ) );
        page.setAuthorId( Integer.parseInt(request.getParameter( "authorId" )) );
        page.setParentId( Integer.parseInt(request.getParameter( "parentId" )) );

        String id = request.getParameter("id");
        if( id == null || id.isEmpty() )
            dao.add(page);
        else {
            page.setId( Integer.parseInt(id) );
            dao.update(page);
        }
        RequestDispatcher view = request.getRequestDispatcher( URL_lIST );
        request.setAttribute(PROP_BEANSNAME, dao.getAll());
        view.forward(request, response);
    }
}