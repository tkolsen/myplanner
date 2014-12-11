package MyPlanner.dao;

import MyPlanner.model.Course;

import java.util.List;

public interface CourseDao {
    public List<Course> getAllCourses();
    public Course getCourse(int id);
    public void updateCourse(Course course);
    public void deleteCourse(Course course);
}
