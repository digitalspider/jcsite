package au.com.javacloud.dao;

import au.com.javacloud.model.User;
import au.com.javacloud.util.DBUtil;

/**
 * Created by david on 22/05/16.
 */
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO<User> {

    public UserDAOImpl() {
        super(User.class);
        conn = DBUtil.getConnection();
        excludeForSaveGetMethods.add("cdate");
        excludeForSaveGetMethods.add("mdate");
    }
}
