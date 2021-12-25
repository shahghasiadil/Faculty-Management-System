package com.faculty.support;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class DBUtil {

    private static Object result;

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> tClass, long id) {

        exec(session -> result = session.get(tClass, id));

        return (T) result;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getAll(Class<T> tClass) {
        String className = tClass.getSimpleName();

        exec(session -> {
            try {
                result = session.createQuery("from " + className).list();
            } catch (EntityNotFoundException enfEx) {
                System.err.println(enfEx.getMessage());
                result = new ArrayList<>();
            }
        });

        return (List<T>) result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T findById(Class<T> tClass, long id) {
        return get(tClass, id);
    }

    public static <T> void save(T instance) {
        exec(session -> session.save(instance));
    }

    public static <T> void update(T instance) {
        exec(session -> session.update(instance));
    }

    public static <T> void saveOrUpdate(T instance) {
        exec(session -> session.saveOrUpdate(instance));
    }

    public static <T> void delete(T instance) {
        exec(session -> session.delete(instance));
    }

    public static void exec(Consumer<Session> execute) {
        Session session = SessionFactoryUtil.getSession();
        session.beginTransaction();
        execute.accept(session);
        session.getTransaction().commit();
        session.close();
    }

    public static <R> R execute(Function<Session, R> action) {
        Session session = SessionFactoryUtil.getSession();
        Transaction transaction = session.beginTransaction();

        final R result = action.apply(session);
        transaction.commit();
        session.close();

        return result;
    }
}
