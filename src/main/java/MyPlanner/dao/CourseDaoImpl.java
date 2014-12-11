package MyPlanner.dao;

import MyPlanner.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao{
    List<Course> courses;

    public CourseDaoImpl(){
        courses = new ArrayList<Course>();
    }
    public List<Course> getAllCourses(){
        return courses;
    }

    public Course getCourse(int id){
        return courses.get(id);
    }

    public void updateCourse(Course course){
        courses.get(course.getId()).setName(course.getName());
    }
    public void deleteCourse(Course course){
        courses.remove(course.getId());
    }
}
