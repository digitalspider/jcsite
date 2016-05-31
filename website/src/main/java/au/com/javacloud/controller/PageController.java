package au.com.javacloud.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by david on 22/05/16.
 */

import org.apache.commons.lang3.StringUtils;

import au.com.javacloud.dao.BaseDAOImpl;
import au.com.javacloud.model.Page;

@WebServlet("/page/*")
public class PageController extends BaseController<Page> {

    protected Page populateBean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Page page = new Page();
        page.setTitle( request.getParameter( "title" ) );
        page.setDescription( request.getParameter( "description" ) );
        page.setContent( request.getParameter( "content" ) );
        page.setUrl( request.getParameter( "url" ) );
        page.setType( request.getParameter( "type" ) );
        page.setTags( request.getParameter( "tags" ) );
        page.setStatus( request.getParameter( "status" ) );
        if (StringUtils.isNumeric(request.getParameter( "authorId" ))) {
            page.setAuthorId(Integer.parseInt(request.getParameter("authorId")));
        }
        if (StringUtils.isNumeric(request.getParameter( "parentId" ))) {
            page.setParentId(Integer.parseInt(request.getParameter("parentId")));
        }
        return page;
    }
}