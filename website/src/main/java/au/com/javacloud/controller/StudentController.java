package au.com.javacloud.controller;

/**
 * Created by david on 22/05/16.
 */
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.javacloud.dao.StudentDAOImpl;
import au.com.javacloud.dao.StudentDAO;
import au.com.javacloud.model.Student;

@WebServlet("/student")
public class StudentController extends BaseController {

    public static final String URL_lIST = "/page/student/list.jsp";
    public static final String URL_INSERT_OR_EDIT = "/page/student/edit.jsp";
    public static final String PROP_BEANNAME = "student";
    public static final String PROP_BEANSNAME = "students";

    public StudentController() {
        super(new StudentDAOImpl(), PROP_BEANNAME, PROP_BEANSNAME, URL_lIST, URL_INSERT_OR_EDIT);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Student student = new Student();
        student.setFirstName( request.getParameter( "firstName" ) );
        student.setLastName( request.getParameter( "lastName" ) );
        student.setCourse( request.getParameter( "course" ) );
        student.setYear( Integer.parseInt( request.getParameter( "year" ) ) );
        String studentId = request.getParameter("studentId");

        if( studentId == null || studentId.isEmpty() )
            dao.add(student);
        else {
            student.setId( Integer.parseInt(studentId) );
            dao.update(student);
        }
        RequestDispatcher view = request.getRequestDispatcher( URL_lIST );
        request.setAttribute(PROP_BEANSNAME, dao.getAll());
        view.forward(request, response);
    }
}