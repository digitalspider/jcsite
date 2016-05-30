package au.com.javacloud.dao;

import au.com.javacloud.model.Student;
import au.com.javacloud.util.DBUtil;

/**
 * Created by david on 22/05/16.
 */
public class StudentDAOImpl extends BaseDAOImpl<Student> implements StudentDAO<Student> {

    public StudentDAOImpl() {
        super(Student.class);
        conn = DBUtil.getConnection();
    }
}
