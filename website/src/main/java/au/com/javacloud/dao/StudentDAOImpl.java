package au.com.javacloud.dao;

import java.util.ArrayList;
import java.util.List;

import au.com.javacloud.model.Student;
import au.com.javacloud.util.DBUtil;

/**
 * Created by david on 22/05/16.
 */
public class StudentDAOImpl extends BaseDAOImpl<Student> implements StudentDAO<Student> {

    public StudentDAOImpl() {
        super(new StudentDAOMapper());
        conn = DBUtil.getConnection();
    }

    public List<Student> getAll() {
        try {
            return getAll(Student.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Student>();
        }
    }

    public Student get(int id) {
        try {
            return get(id, Student.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new Student();
        }
    }
}
