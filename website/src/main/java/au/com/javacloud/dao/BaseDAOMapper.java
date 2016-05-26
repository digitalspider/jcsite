package au.com.javacloud.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import au.com.javacloud.model.BaseBean;

/**
 * Created by david on 26/05/16.
 */
public interface BaseDAOMapper<T extends BaseBean> {

    public String getTableName();

    public List<String> getInsertColumns();

    public List<String> getUpdateColumns();

    public void populateBeanFromResultSet(T bean, ResultSet rs) throws SQLException, ParseException;

    public int prepareStatementForSave(T bean, PreparedStatement preparedStatement) throws SQLException;
}
