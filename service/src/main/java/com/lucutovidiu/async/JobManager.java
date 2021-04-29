package com.lucutovidiu.async;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class JobManager {
    private static JobManager instance;
    private volatile Set<Job> queue = new HashSet<>();

    private JobManager() {
    }

    public static JobManager getInstance() {
        if (instance == null) {
            synchronized (JobManager.class) {
                if (instance == null) {
                    instance = new JobManager();
                    instance.startJobManager();
                }
            }
        }
        return instance;
    }

    public synchronized void addJobToQueue(Job job) {
        queue.add(job);
        log.info("adding job: {} on thread: {}", job.getName().toString(), Thread.currentThread().getName());
    }

    private void startJobManager() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!queue.isEmpty()) {
                    Iterator<Job> iterator = queue.iterator();
                    while (iterator.hasNext()) {
                        Job job = iterator.next();
                        if (!job.isBeenRunAlready()) {
                            log.info("running job: {} on thread: {}", job.getName().toString(), Thread.currentThread().getName());
                            job.isBeenRunAlready(true);
                            job.getRunnable().run();
                        }
                        if (isMarkForRemoval(job)) {
                            iterator.remove();
                        }
                    }
                }
            }
        }).start();
    }

    private boolean isMarkForRemoval(Job job) {
        if (job.getName().equals(Job.JobTypes.UserVisitAndEmail)) {
            if (job.isBeenRunAlready()) {
                return true;
            }
        }
        if (job.getName().equals(Job.JobTypes.ExpiredProductsEmail)) {
            // has been ran in another day
            return job.getCreatedDate().getDayOfMonth() != LocalDate.now().getDayOfMonth() && job.isBeenRunAlready();
        }
        return false;
    }
}
