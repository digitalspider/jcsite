package au.com.javacloud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import au.com.javacloud.model.Page;
import au.com.javacloud.model.Student;
import au.com.javacloud.util.DBUtil;

/**
 * Created by david on 22/05/16.
 */
public class StudentDAOImpl extends BaseDAOImpl<Student> implements StudentDAO<Student> {

    private Connection conn;

    public StudentDAOImpl() {
        super(new StudentDAOMapper());
        conn = DBUtil.getConnection();
    }

    @Override
    public List<Student> getAll() {
        try {
            return getAll(Student.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Student>();
        }
    }

    @Override
    public Student get(int id) {
        try {
            return get(id, Student.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new Student();
        }
    }
}
