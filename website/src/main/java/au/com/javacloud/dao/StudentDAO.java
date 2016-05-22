package au.com.javacloud.dao;

/**
 * Created by david on 22/05/16.
 */
import java.util.List;

import au.com.javacloud.model.Student;

public interface StudentDAO {
    public void addStudent( Student student );
    public void deleteStudent( int studentId );
    public void updateStudent( Student student );
    public List<Student> getAllStudents();
    public Student getStudentById( int studentId );
}
