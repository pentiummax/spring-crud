package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;
import javax.persistence.*;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveUser(User user) {
        em.persist(user);
    }

    @Override
    public void deleteUser(long id) {
        em.remove(em.find(User.class, id));
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public User getUser(long id) {
        User user = em.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException("Can't find User for ID "
                    + id);
        }
        return user;
    }

    @Override
    public List<User> getUsersList() {
        Query query = em.createQuery("SELECT u FROM User u", User.class);
        return (List<User>) query.getResultList();
    }

    @Override
    public User findByUsername(String username) {
        Query query = em.createQuery("SELECT u FROM User u where u.username = :username", User.class);
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }

}
