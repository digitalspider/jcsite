package au.com.javacloud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import au.com.javacloud.model.BaseBean;
import au.com.javacloud.util.DBUtil;

/**
 * Created by david on 22/05/16.
 */
public abstract class BaseDAOImpl<T extends BaseBean> implements BaseDAO<T> {

    protected Connection conn;
    protected BaseDAOMapper<T> mapper;
    protected String tableName = "TABLE_NOT_DEFINED";

    public BaseDAOImpl(BaseDAOMapper<T> mapper) {
        conn = DBUtil.getConnection();
        this.mapper = mapper;
        tableName = mapper.getTableName();
    }

    /**
     * Creates the insert part of the SQL. e.g. (name,email,date) values (?,?,?)
     */
    private String getInsertIntoColumnsSQL() {
        String names = "";
        String params = "";
        List<String> columns = mapper.getInsertColumns();
        for (String column : columns) {
            if (names.length()>0) {
                names +=", ";
                params +=", ";
            }
            names += column;
            params += "?";
        }
        return "("+names+") values ("+params+")";
    }

    /**
     * Create the update part of the SQL. e.g. name=?, email=?, date=?
     */
    private String getUpdateColumnsSQL() {
        String sql = "";
        List<String> columns = mapper.getInsertColumns();
        for (String column : columns) {
            if (sql.length()>0) {
                sql +=", ";
            }
            sql += column+"=?";
        }
        return sql;
    }

    @Override
    public void add( T bean ) {
        try {
            String query = "insert into "+tableName+" "+getInsertIntoColumnsSQL();
            PreparedStatement preparedStatement = conn.prepareStatement( query );
            mapper.prepareStatementForSave(bean, preparedStatement);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update( T bean ) {
        try {
            String query = "update "+tableName+" set "+getUpdateColumnsSQL()+" where id=?";
            PreparedStatement preparedStatement = conn.prepareStatement( query );
            int index = mapper.prepareStatementForSave(bean, preparedStatement);
            preparedStatement.setInt(++index, bean.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete( int id ) {
        try {
            String query = "delete from "+tableName+" where id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> getAll(Class<T> clazz) throws InstantiationException, IllegalAccessException, ParseException {
        List<T> beans = new ArrayList<T>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery( "select * from "+tableName );
            while( resultSet.next() ) {
                T bean = clazz.newInstance();
                bean.setId( resultSet.getInt( "id" ) );
                mapper.populateBeanFromResultSet(bean, resultSet);
                beans.add(bean);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }
    
    @Override
    public T get(int id, Class<T> clazz) throws InstantiationException, IllegalAccessException, ParseException {
        T bean = clazz.newInstance();
        try {
            String query = "select * from "+tableName+" where id=?";
            PreparedStatement preparedStatement = conn.prepareStatement( query );
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if( resultSet.next() ) {
                bean.setId( resultSet.getInt( "id" ) );
                mapper.populateBeanFromResultSet(bean, resultSet);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }
}
