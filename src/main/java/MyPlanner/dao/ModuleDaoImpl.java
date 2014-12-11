package MyPlanner.dao;

import MyPlanner.model.Module;

import java.util.ArrayList;
import java.util.List;

public class ModuleDaoImpl {
    List<Module> moduleList;
    public ModuleDaoImpl() {
        moduleList = new ArrayList<Module>();
    }

    public List<Module> getAllModules(){
        return moduleList;
    }
    public Module getModule(int id){
        return moduleList.get(id);
    }
    public void updateModule(Module module){
        moduleList.get(module.getId()).setName(module.getName());
    }
    public void deleteModule(Module module){
        moduleList.remove(module.getId());
    }
}
