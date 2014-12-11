package MyPlanner.dao;

import MyPlanner.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl {
    List<User> userList;
    public UserDaoImpl() {
        userList = new ArrayList<User>();
    }

    public List<User> getAllUsers(){
        return userList;
    }
    public User getUser(int id){
        return userList.get(id);
    }
    public void updateUser(User user){
        userList.get(user.getId()).setName(user.getName());
    }
    public void deleteUser(User user){
        userList.remove(user.getId());
    }
}
