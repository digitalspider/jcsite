package au.com.javacloud.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import au.com.javacloud.model.Page;
import au.com.javacloud.model.User;

import static au.com.javacloud.util.Constants.dft;

/**
 * Created by david on 26/05/16.
 */
public class UserDAOMapper implements BaseDAOMapper<User> {
    public static final List<String> INSERT_COLUMNS;
    public static final List<String> UPDATE_COLUMNS;
    private static final String TABLE_NAME = "user";

    static {
        String[] columnNames = new String[] {"cdate", "mdate", "type", "tags", "description",
                "status", "fname", "lname", "email", "username", "password", "url", "mobile",
                "token", "image"};

        INSERT_COLUMNS = Arrays.asList(columnNames);
        UPDATE_COLUMNS = Arrays.asList(columnNames);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void populateBeanFromResultSet(User bean, ResultSet rs) throws SQLException, ParseException {
        bean.setCdate(dft.parse(rs.getString("cdate")));
        bean.setMdate(dft.parse(rs.getString("mdate")));
        bean.setType(rs.getString("type"));
        bean.setTags(rs.getString("tags"));
        bean.setDescription(rs.getString("description"));
        bean.setStatus(rs.getString("status"));
        bean.setFirstname(rs.getString("fname"));
        bean.setLastname(rs.getString("lname"));
        bean.setEmail(rs.getString("email"));
        bean.setUsername(rs.getString("username"));
        bean.setPassword(rs.getString("password"));
        bean.setUrl(rs.getString("url"));
        bean.setMobile(rs.getString("mobile"));
        bean.setToken(rs.getString("token"));
        bean.setImage(rs.getString("image"));
    }

    @Override
    public int prepareStatementForSave(User bean, PreparedStatement preparedStatement) throws SQLException {
        int index = 0;
        preparedStatement.setString(++index, dft.format(bean.getCdate()));
        preparedStatement.setString(++index, dft.format(bean.getMdate()));
        preparedStatement.setString(++index, bean.getType());
        preparedStatement.setString(++index, bean.getTags());
        preparedStatement.setString(++index, bean.getDescription());
        preparedStatement.setString(++index, bean.getStatus());
        preparedStatement.setString(++index, bean.getFirstname());
        preparedStatement.setString(++index, bean.getLastname());
        preparedStatement.setString(++index, bean.getEmail());
        preparedStatement.setString(++index, bean.getUsername());
        preparedStatement.setString(++index, bean.getPassword());
        preparedStatement.setString(++index, bean.getUrl());
        preparedStatement.setString(++index, bean.getMobile());
        preparedStatement.setString(++index, bean.getToken());
        preparedStatement.setString(++index, bean.getImage());
        return index;
    }

    @Override
    public List<String> getInsertColumns() {
        return INSERT_COLUMNS;
    }

    @Override
    public List<String> getUpdateColumns() {
        return UPDATE_COLUMNS;
    }
}
