package service;

import java.util.List;

import dao.CourseClassesDAO;
import entity.CourseClasses;

public class CourseClassesService {
private CourseClassesDAO courseClassesDAO;
    
    public CourseClassesService() {
        this.courseClassesDAO = new CourseClassesDAO();
    }
    
    public List<CourseClasses> getAllCourseClasses() {
        return courseClassesDAO.getAllCourseClasses();
    }
}
