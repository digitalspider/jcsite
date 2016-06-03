package au.com.javacloud.controller;

import java.security.Principal;

import javax.servlet.annotation.WebServlet;

/**
 * Created by david on 22/05/16.
 */

import au.com.javacloud.model.Page;

@WebServlet(urlPatterns = {"/page/*", "/page.json/*", "/blog/*"})
public class PageController extends BaseControllerImpl<Page,Principal> {

    public PageController() {
		super(Page.class);
	}

}