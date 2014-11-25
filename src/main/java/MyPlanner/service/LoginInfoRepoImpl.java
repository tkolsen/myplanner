package MyPlanner.service;

import MyPlanner.model.LoginInfo;
import java.util.ArrayList;

public class LoginInfoRepoImpl implements LoginInfoRepo {
    ArrayList<LoginInfo> users;

    public LoginInfoRepoImpl(){
        users = new ArrayList<LoginInfo>();
    }

    @Override
    public boolean saveUser(LoginInfo loginInfo) {
        if(!containsUser(loginInfo.getUser().getId())) {
            users.add(loginInfo);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public LoginInfo getUser(int id) {
        for(LoginInfo user : users){
            if(user.getUser().getId() == id){
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean containsUser(int id) {
        for(LoginInfo user : users){
            if(user.getUser().getId() == id){
                return true;
            }
        }
        return false;
    }
}
