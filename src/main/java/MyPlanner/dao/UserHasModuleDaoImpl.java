package MyPlanner.dao;

import MyPlanner.model.UserHasModule;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserHasModuleDaoImpl implements UserHasModuleDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void update(UserHasModule userHasModule) {
        sessionFactory.getCurrentSession().saveOrUpdate(userHasModule);
    }

}
