package MyPlanner.service;

import MyPlanner.model.LoginInfo;

public interface LoginInfoRepo {
    public void saveUser(LoginInfo loginInfo);

    public LoginInfo getUser(int id);
}