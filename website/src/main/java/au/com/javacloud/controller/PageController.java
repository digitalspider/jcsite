package au.com.javacloud.controller;

/**
 * Created by david on 22/05/16.
 */

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.javacloud.dao.PageDAOImpl;
import au.com.javacloud.model.Page;

@WebServlet("/blog/*")
public class PageController extends BaseController<Page> {

    public PageController() {
        super(new PageDAOImpl());
    }

    protected Page populateBean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        return page;
    }
}