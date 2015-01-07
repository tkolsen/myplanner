package MyPlanner.dao;

import MyPlanner.model.Course;
import MyPlanner.model.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void save(Course course) {
        sessionFactory.getCurrentSession().saveOrUpdate(course);
    }

    @Override
    @Transactional
    public List<Course> list() {
        @SuppressWarnings("unchecked")
        List<Course> courseList = (List<Course>)sessionFactory.getCurrentSession()
                .createCriteria(Course.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        return courseList;
    }

    @Override
    @Transactional
    public Course get(Course course) {
        String hql = "from Course where id=" + course.getId();
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<Course> courseList = (List<Course>)query.list();
        if(courseList != null && !courseList.isEmpty()){
            return courseList.get(0);
        }
        return null;
    }
}
