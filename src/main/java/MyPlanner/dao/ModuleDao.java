package MyPlanner.dao;

import MyPlanner.model.Module;

import java.util.List;

public interface ModuleDao {
    public List<Module> getAllModules();
    public Module getModule(int id);
    public void updateModule(Module module);
    public void deleteModule(Module module);
}
