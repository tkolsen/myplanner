package MyPlanner.dao;

import MyPlanner.model.Course;

import java.util.List;

public interface CourseDao {
    public void save(Course course);
    public List<Course> list();
    public Course get(Course course);
}
