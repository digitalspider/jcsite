package au.com.javacloud.dao;

import static au.com.javacloud.util.Constants.dft;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import au.com.javacloud.model.Page;

/**
 * Created by david on 26/05/16.
 */
public class PageDAOMapper implements BaseDAOMapper<Page> {
    private static final List<String> INSERT_COLUMNS;
    private static final List<String> UPDATE_COLUMNS;
    private static final String TABLE_NAME = "page";

    static {
        String[] columnNames = new String[] {"cdate", "mdate", "type", "tags", "description",
            "status", "authorId", "parentId", "title", "content", "url"};

        INSERT_COLUMNS = Arrays.asList(columnNames);
        UPDATE_COLUMNS = Arrays.asList(columnNames);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void populateBeanFromResultSet(Page bean, ResultSet rs) throws SQLException, ParseException {
        bean.setCdate(dft.parse(rs.getString("cdate")));
        bean.setMdate(dft.parse(rs.getString("mdate")));
        bean.setType(rs.getString("type"));
        bean.setTags(rs.getString("tags"));
        bean.setDescription(rs.getString("description"));
        bean.setStatus(rs.getString("status"));
        bean.setAuthorId(rs.getInt("authorId"));
        bean.setParentId(rs.getInt("parentId"));
        bean.setTitle(rs.getString("title"));
        bean.setContent(rs.getString("content"));
        bean.setUrl(rs.getString("url"));
    }

    @Override
    public int prepareStatementForSave(Page bean, PreparedStatement preparedStatement) throws SQLException {
        int index = 0;
        preparedStatement.setString(++index, dft.format(bean.getCdate()));
        preparedStatement.setString(++index, dft.format(bean.getMdate()));
        preparedStatement.setString(++index, bean.getType());
        preparedStatement.setString(++index, bean.getTags());
        preparedStatement.setString(++index, bean.getDescription());
        preparedStatement.setString(++index, bean.getStatus());
        preparedStatement.setInt(++index, bean.getAuthorId());
        preparedStatement.setInt(++index, bean.getParentId());
        preparedStatement.setString(++index, bean.getTitle());
        preparedStatement.setString(++index, bean.getContent());
        preparedStatement.setString(++index, bean.getUrl());
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
