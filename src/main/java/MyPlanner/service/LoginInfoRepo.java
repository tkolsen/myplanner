package MyPlanner.service;

import MyPlanner.model.LoginInfo;

public interface LoginInfoRepo {
    public boolean saveUser(LoginInfo loginInfo);

    public LoginInfo getUser(int id);

    public boolean containsUser(int id);
}
