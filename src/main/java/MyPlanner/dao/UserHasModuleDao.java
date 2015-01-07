package MyPlanner.dao;

import MyPlanner.model.UserHasModule;

import java.util.List;

/**
 * Created by Tom on 06.01.2015.
 */
public interface UserHasModuleDao {
    public void update(UserHasModule userHasModule);
    public UserHasModule get(UserHasModule userHasModule);
    public List<UserHasModule> list();
}
