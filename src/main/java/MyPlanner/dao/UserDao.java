package MyPlanner.dao;

import MyPlanner.model.User;

import java.util.List;

public interface UserDao {
    public void save(User user);
    public User get(User user);
}
