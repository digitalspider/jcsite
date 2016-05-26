package au.com.javacloud.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.javacloud.model.Page;
import au.com.javacloud.model.Student;

/**
 * Created by david on 26/05/16.
 */
public class StudentDAOMapper implements BaseDAOMapper<Student> {
    public static final List<String> INSERT_COLUMNS;
    public static final List<String> UPDATE_COLUMNS;
    private static final String TABLE_NAME = "student";

    static {
        String[] columnNames = new String[] {"firstName", "lastName", "course", "year"};

        INSERT_COLUMNS = Arrays.asList(columnNames);
        UPDATE_COLUMNS = Arrays.asList(columnNames);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void populateBeanFromResultSet(Student bean, ResultSet rs) throws SQLException {
        bean.setFirstName(rs.getString("firstName"));
        bean.setLastName(rs.getString("lastName"));
        bean.setCourse(rs.getString("course"));
        bean.setYear(rs.getInt("year"));
    }

    @Override
    public int prepareStatementForSave(Student bean, PreparedStatement preparedStatement) throws SQLException {
        int index = 0;
        preparedStatement.setString(++index, bean.getFirstName());
        preparedStatement.setString(++index, bean.getLastName());
        preparedStatement.setString(++index, bean.getCourse());
        preparedStatement.setInt(++index, bean.getYear());
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
