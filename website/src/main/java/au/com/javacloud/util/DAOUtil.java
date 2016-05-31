package au.com.javacloud.util;

import java.util.List;

/**
 * Created by david on 1/06/16.
 */
public class DAOUtil {
    /**
     * Creates the insert part of the SQL. e.g. (name,email,date) values (?,?,?)
     */
    public static String getInsertIntoColumnsSQL(List<String> columns) {
        String names = "";
        String params = "";
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
    public static String getUpdateColumnsSQL(List<String> columns) {
        String sql = "";
        for (String column : columns) {
            if (sql.length()>0) {
                sql +=", ";
            }
            sql += column+"=?";
        }
        return sql;
    }
}
