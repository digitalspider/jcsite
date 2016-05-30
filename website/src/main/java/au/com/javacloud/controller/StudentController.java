package au.com.javacloud.controller;

/**
 * Created by david on 22/05/16.
 */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.javacloud.dao.StudentDAOImpl;
import au.com.javacloud.model.Student;

@WebServlet("/student/*")
public class StudentController extends BaseController<Student> {

    public StudentController() {
        super(new StudentDAOImpl(), Student.class);
    }

    protected Student populateBean(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Student student = new Student();
        student.setFirstName( request.getParameter( "firstName" ) );
        student.setLastName( request.getParameter( "lastName" ) );
        student.setCourse( request.getParameter( "course" ) );
        student.setYear( Integer.parseInt( request.getParameter( "year" ) ) );
        String studentId = request.getParameter("studentId");
        return student;
    }
}