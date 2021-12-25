package com.faculty.support;

public class ConcurrencyUtil {
    public static void run(Runnable task) {
        run(task, true);
    }

    public static void run(Runnable task, boolean daemon) {
        run(null, task, daemon);
    }

    public static void run(ThreadGroup group, Runnable task, boolean daemon) {
        Thread thread = null;

        if (null != group) {
            thread = new Thread(group, task);
        } else {
            thread = new Thread(task);
        }

        thread.setDaemon(daemon);
        thread.start();
    }
}
