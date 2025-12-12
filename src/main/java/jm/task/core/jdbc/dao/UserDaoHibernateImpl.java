package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory session;

    public UserDaoHibernateImpl() {
        try {
            this.session = Util.getSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка соединения", e);
        }
    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50), " +
                "last_name VARCHAR(50), " +
                "age TINYINT" +
                ")";

        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при созданий", e);
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка дропа", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            User user = new User();
            user.setName ( name);
            user.setLastName(lastName);
            user.setAge(age);

            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка сохранении", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        }catch (Exception e) {
            throw new RuntimeException("Ошибка удаления");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении пользователей", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM users";

        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        }catch (Exception e) {
            throw new RuntimeException("Ошибка очистки", e);
        }
    }
}
