package xin.charming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.charming.bean.User;
import xin.charming.dao.UserDao;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public boolean addUser(User user) {
        User u = userDao.selectUserByAuth(user);
        if (u != null) {
            return false;
        } else {
            userDao.insertUser(user);
            return true;
        }
    }

    public User queryUserByAuth(User user) {
        return userDao.selectUserByAuth(user);
    }
}
