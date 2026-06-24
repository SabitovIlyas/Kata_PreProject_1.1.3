package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private final String url = "jdbc:mysql://localhost:3306/kata_preproject_1.1.3";
    private final String user = "root";
    private final String password = "root";

    private SessionFactory sessionFactory;

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public Session getSession() {
        return getSessionFactory().openSession();
    }

    private SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .addAnnotatedClass(User.class)
                    .setProperty("hibernate.connection.driver_class",
                            "com.mysql.cj.jdbc.Driver")
                    .setProperty("hibernate.connection.url", url)
                    .setProperty("hibernate.connection.username", user)
                    .setProperty("hibernate.connection.password", password)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
                    .setProperty("hibernate.show_sql", "true")
                    .setProperty("hibernate.hbm2ddl.auto", "update")
                    .buildSessionFactory();

        }
        return sessionFactory;
    }

    public void initialiZeSession() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        sessionFactory = null;
        getSessionFactory();
    }
}