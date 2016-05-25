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

import au.com.javacloud.dao.StudentDAO;
import au.com.javacloud.dao.StudentDAOImpl;
import au.com.javacloud.model.Student;

@WebServlet("/student")
public class UserController extends HttpServlet {

    private StudentDAO dao;
    private static final long serialVersionUID = 1L;
    public static final String lIST_STUDENT = "/page/student/list.jsp";
    public static final String INSERT_OR_EDIT = "/page/student/edit.jsp";

    public UserController() {
        dao = new StudentDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = null;
        String action = request.getParameter( "action" );

        if (action!=null) {
            if (action.equalsIgnoreCase("delete")) {
                forward = lIST_STUDENT;
                int studentId = Integer.parseInt(request.getParameter("studentId"));
                dao.deleteStudent(studentId);
                request.setAttribute("students", dao.getAllStudents());
            } else if (action.equalsIgnoreCase("edit")) {
                forward = INSERT_OR_EDIT;
                int studentId = Integer.parseInt(request.getParameter("studentId"));
                Student student = dao.getStudentById(studentId);
                request.setAttribute("student", student);
            } else if (action.equalsIgnoreCase("insert")) {
                forward = INSERT_OR_EDIT;
            }
        }
        if (forward==null) {
            forward = lIST_STUDENT;
            request.setAttribute("students", dao.getAllStudents() );
        }
        RequestDispatcher view = request.getRequestDispatcher( forward );
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Student student = new Student();
        student.setFirstName( request.getParameter( "firstName" ) );
        student.setLastName( request.getParameter( "lastName" ) );
        student.setCourse( request.getParameter( "course" ) );
        student.setYear( Integer.parseInt( request.getParameter( "year" ) ) );
        String studentId = request.getParameter("studentId");

        if( studentId == null || studentId.isEmpty() )
            dao.addStudent(student);
        else {
            student.setId( Integer.parseInt(studentId) );
            dao.updateStudent(student);
        }
        RequestDispatcher view = request.getRequestDispatcher( lIST_STUDENT );
        request.setAttribute("students", dao.getAllStudents());
        view.forward(request, response);
    }
}