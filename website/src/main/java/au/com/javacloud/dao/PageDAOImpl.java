package au.com.javacloud.dao;

import au.com.javacloud.model.Page;
import au.com.javacloud.util.DBUtil;

/**
 * Created by david on 22/05/16.
 */
public class PageDAOImpl extends BaseDAOImpl<Page> implements PageDAO<Page> {

    public PageDAOImpl() {
        super(Page.class);
        conn = DBUtil.getConnection();
    }

}
