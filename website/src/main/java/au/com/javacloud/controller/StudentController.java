package au.com.javacloud.controller;

/**
 * Created by david on 22/05/16.
 */
import javax.servlet.annotation.WebServlet;

import au.com.javacloud.model.Student;

@WebServlet(urlPatterns = {"/student/*", "/student.json/*"})
public class StudentController extends BaseControllerImpl<Student> {

    public StudentController() {
		super(Student.class);
	}

}