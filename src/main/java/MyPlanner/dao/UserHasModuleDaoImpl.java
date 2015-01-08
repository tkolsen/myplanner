package MyPlanner.dao;

import MyPlanner.model.UserHasModule;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UserHasModuleDaoImpl implements UserHasModuleDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void update(UserHasModule userHasModule) {
        sessionFactory.getCurrentSession().saveOrUpdate(userHasModule);
    }

    @Override
    public UserHasModule get(UserHasModule userHasModule) {
        return null;
    }

    @Override
    @Transactional
    public List<UserHasModule> list() {
        List<UserHasModule> userHasModuleList = (List<UserHasModule>)sessionFactory.getCurrentSession()
                .createCriteria(UserHasModule.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        return userHasModuleList;
    }

    @Override
    @Transactional
    public void updateList(List<UserHasModule> userHasModule) {
        for (UserHasModule u : userHasModule){ update(u); }
    }
}
