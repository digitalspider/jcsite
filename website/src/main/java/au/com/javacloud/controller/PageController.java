package au.com.javacloud.controller;

import javax.servlet.annotation.WebServlet;

/**
 * Created by david on 22/05/16.
 */

import au.com.javacloud.model.Page;

@WebServlet(urlPatterns = {"/page/*", "/page.json/*"})
public class PageController extends BaseControllerImpl<Page> {

    public PageController() {
		super(Page.class);
	}

}