package MyPlanner.dao;

import MyPlanner.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void save(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
        System.out.println("User saved in database!");
    }

    @Override
    @Transactional
    public User get(User user) {
        String hql = "from User where id=" + user.getId();
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<User> userList = (List<User>)query.list();
        if(userList != null && !userList.isEmpty()){
            return userList.get(0);
        }
        return null;
    }

    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
