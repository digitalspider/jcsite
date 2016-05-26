package au.com.javacloud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import au.com.javacloud.model.Page;
import au.com.javacloud.util.DBUtil;

/**
 * Created by david on 22/05/16.
 */
public class PageDAOImpl extends BaseDAOImpl<Page> implements PageDAO<Page> {

    private Connection conn;

    public PageDAOImpl() {
        super(new PageDAOMapper());
        conn = DBUtil.getConnection();
    }

    @Override
    public List<Page> getAll() {
        try {
            return getAll(Page.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Page>();
        }
    }

    @Override
    public Page get(int id) {
        try {
            return get(id, Page.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new Page();
        }
    }


}
