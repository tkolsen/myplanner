package MyPlanner.dao;

import MyPlanner.model.User;

import java.util.List;

public interface UserDao {
    public List<User> getAllUsers();
    public User getUser(int id);
    public void updateUser(User user);
    public void deleteUser(User user);
}
