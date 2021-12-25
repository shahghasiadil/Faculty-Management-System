package com.faculty.support;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactoryUtil {
    private static SessionFactoryUtil instance;

    private SessionFactory sessionFactory;

    private SessionFactoryUtil() {
        StandardServiceRegistry ssr = null;

        try {
            ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();

            MetadataSources sources = new MetadataSources(ssr);
            MetadataBuilder builder = sources.getMetadataBuilder();

            sessionFactory = builder.build().buildSessionFactory();
        } catch(Exception ex) {
            ex.printStackTrace();
            StandardServiceRegistryBuilder.destroy(ssr);
        }
    }

    public SessionFactory getFactory() {
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return getInstance().getFactory();
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }

    public static SessionFactoryUtil getInstance() {
        if (null == instance) {
            instance = new SessionFactoryUtil();
        }

        return instance;
    }
}
