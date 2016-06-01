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

import au.com.javacloud.dao.BaseDAO;
import au.com.javacloud.model.Page;
import au.com.javacloud.model.User;
import au.com.javacloud.util.ReflectUtil;

@WebServlet("/page/*")
public class PageController extends BaseControllerImpl<Page> {

    public PageController() {
		super(Page.class);
	}

    @Override
	public Page populateBean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Page page = new Page();
        page.setTitle( request.getParameter( "title" ) );
        page.setDescription( request.getParameter( "description" ) );
        page.setContent( request.getParameter( "content" ) );
        page.setUrl( request.getParameter( "url" ) );
        page.setType( request.getParameter( "type" ) );
        page.setTags( request.getParameter( "tags" ) );
        page.setStatus( request.getParameter( "status" ) );
        try {
            if (StringUtils.isNumeric(request.getParameter("authorId"))) {
                BaseDAO<User> dao = ReflectUtil.getDaoMap().get("user");
                if (dao!=null) {
                    User author = dao.get(Integer.parseInt(request.getParameter("authorId")));
                    if (author!=null) {
                        page.setAuthorId(author);
                    }
                }
            }
            if (StringUtils.isNumeric(request.getParameter("parentId"))) {
                BaseDAO<Page> dao = ReflectUtil.getDaoMap().get("page");
                if (dao!=null) {
                    Page parent = dao.get(Integer.parseInt(request.getParameter("parentId")));
                    if (parent != null) {
                        page.setParentId(parent);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }
}