package au.com.javacloud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import au.com.javacloud.model.User;
import au.com.javacloud.util.DBUtil;

/**
 * Created by david on 22/05/16.
 */
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO<User> {

    private Connection conn;

    public UserDAOImpl() {
        super(new UserDAOMapper());
        conn = DBUtil.getConnection();
    }

    @Override
    public List<User> getAll() {
        try {
            return getAll(User.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<User>();
        }
    }

    @Override
    public User get(int id) {
        try {
            return get(id, User.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new User();
        }
    }
}
