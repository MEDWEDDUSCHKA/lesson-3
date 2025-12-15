package jm.task.core.jdbc.util;
import java.sql.*;
import java.util.*;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Util {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123123";
    private static final String URL = "jdbc:mysql://localhost:3306/lesson";

    public SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration cfg = new Configuration();
                cfg.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
                cfg.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/lesson");
                cfg.setProperty("hibernate.connection.username", "root");
                cfg.setProperty("hibernate.connection.password", "123123");
                cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
                cfg.addAnnotatedClass(User.class);
                ServiceRegistry srt = new StandardServiceRegistryBuilder()
                        .applySettings(cfg.getProperties()).build();

                return cfg.buildSessionFactory(srt);
            }catch (Exception e) {
                throw new RuntimeException("ошибка H");
            }
        }
        return sessionFactory;
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка");
        }
    }
}