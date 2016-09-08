package au.com.jcloud.actionbean;

import com.avaje.ebean.Ebean;

import net.sourceforge.stripes.action.JsonResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.RestActionBean;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.jcloud.WebConstants;
import au.com.jcloud.model.Blog;

import static au.com.jcloud.WebConstants.URL_CC_PREFIX;

/**
 * Created by david.vittor on 3/08/16.
 */
@RestActionBean
@UrlBinding(WebConstants.ACTION_PUBLIC_BLOG)
public class BlogLinkActionBean extends JCActionBean {

	public Resolution get() {
		return new JsonResolution( getActiveBlogs() );
	}

	public Resolution post() throws Exception {
		return new JsonResolution( getActiveBlogs() );
	}

	protected List<Blog> getActiveBlogs() {
		LOG.info("getActiveBlogs() START");
		List<Blog> data = new ArrayList<>();
		try {
			data = Ebean.find(Blog.class).where().eq("status", 1).setMaxRows(4).findList();
			LOG.info("data="+data);
		} catch (Exception e) {
			LOG.error(e,e);
		}
		for (Blog blog : data) {
			// Update link to go through clickCount
			blog.setLink( getConextPath()+URL_CC_PREFIX+blog.getLink());
		}
		LOG.info("getActiveBlogs() DONE. data="+data);
		return data;
	}
}
