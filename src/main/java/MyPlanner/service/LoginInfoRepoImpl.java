package MyPlanner.service;

import MyPlanner.model.LoginInfo;
import java.util.ArrayList;

public class LoginInfoRepoImpl implements LoginInfoRepo {
    ArrayList<LoginInfo> users;

    public LoginInfoRepoImpl(){
        users = new ArrayList<LoginInfo>();
    }

    @Override
    public void saveUser(LoginInfo loginInfo) {
        users.add(loginInfo);
    }

    @Override
    public LoginInfo getUser(int id) {
        for(LoginInfo user : users){
            if(Integer.parseInt(user.getUser().getId()) == id){
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean containsUser(int id) {
        for(LoginInfo user : users){
            if(Integer.parseInt(user.getUser().getId()) == id){
                return true;
            }
        }
        return false;
    }
}
