package MyPlanner.dao;

import MyPlanner.model.Module;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class ModuleDaoImpl implements ModuleDao{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void save(Module module) {
        sessionFactory.getCurrentSession().saveOrUpdate(module);
    }

}
