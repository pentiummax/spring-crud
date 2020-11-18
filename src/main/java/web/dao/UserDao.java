package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    void saveUser(User user);
    void deleteUser(long id);
    void updateUser(User user);
    User getUser(long id);
    List<User> getUsersList();
    User findByUsername(String username);
}
