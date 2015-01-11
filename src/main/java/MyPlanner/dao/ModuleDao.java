package MyPlanner.dao;

import MyPlanner.model.Module;

import java.util.List;

public interface ModuleDao {
    public void save(Module module);
    public List<Module> list();
}
