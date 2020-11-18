package web.dao;

import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    void saveUser(User user);
    void deleteUser(long id);
    void updateUser(User user);
    User getUser(long id);
    List<User> getUsersList();
    User findByUsername(String username);
    Set<Role> getRoles();
}
