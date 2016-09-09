package au.com.jcloud.actionbean;

import static au.com.jcloud.WebConstants.URL_CC_PREFIX;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;

import au.com.jcloud.WebConstants;
import au.com.jcloud.enums.Status;
import au.com.jcloud.model.Link;
import au.com.jcloud.util.HttpUtil;
import au.com.jcloud.util.PathParts;
import net.sourceforge.stripes.action.JsonResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.RestActionBean;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * Created by david.vittor on 3/08/16.
 */
@RestActionBean
@UrlBinding(WebConstants.ACTION_PUBLIC_LINK)
public class LinkActionBean extends JCActionBean {

	public static final int DEFAULT_ROWS=10;
	public static final int MAX_ROWS=50;
	
	public Resolution get() {
		return new JsonResolution( getLinkData() );
	}

	public Resolution post() throws Exception {
		return new JsonResolution( getLinkData() );
	}

	protected List<Link> getLinkData() {
		String pathInfo = getRequest().getPathInfo();
		LOG.info("pathInfo="+pathInfo);
		PathParts pathParts = HttpUtil.getPathParts(getRequest());
		LOG.info("pathParts="+pathParts);
		String url = getRequest().getRequestURI();
		LOG.info("uri="+url);
		int index = url.indexOf(WebConstants.ACTION_PUBLIC_LINK);
		if (index<1) {
			return new ArrayList<>();
		}
		String tags = null;
		int maxRows = DEFAULT_ROWS;
		String urlPath = url.substring(index+WebConstants.ACTION_PUBLIC_LINK.length());
		if (StringUtils.isNotBlank(urlPath)) {
			String[] urlParts = urlPath.split("/");
			tags = urlParts[0];
			if (urlParts.length>1 && StringUtils.isNumeric(urlParts[1])) {
				maxRows = Integer.parseInt(urlParts[1]);
				if (maxRows>MAX_ROWS) {
					maxRows = MAX_ROWS;
				}
			}
		}
		ExpressionList<Link> query = Ebean.find(Link.class).setMaxRows(maxRows).where().eq("status", Status.ENABLED.value());
		if (StringUtils.isNotBlank(tags)) {
			query = query.and().ilike("tags",'%'+tags+'%');
		}
		List<Link> linkList = query.findList();
		for (Link link : linkList) {
			link.setUrl(getConextPath()+URL_CC_PREFIX+link.getUrl());
		}
		return linkList;
	}
}
