package com.lucutovidiu.async;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class Job {
    private JobTypes name;
    private Runnable runnable;
    private LocalDate createdDate;
    private boolean isBeenRunAlready = false;
    private boolean isMarkedForRemoval = false;

    public Job(JobTypes name, Runnable runnable) {
        this.name = name;
        this.runnable = runnable;
        this.createdDate = LocalDate.now();
    }

    public void setMarkedForRemoval(boolean markedForRemoval) {
        isMarkedForRemoval = markedForRemoval;
    }

    public void isBeenRunAlready(boolean beenRunAlready) {
        this.isBeenRunAlready = beenRunAlready;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return name == job.name && createdDate.equals(job.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, createdDate);
    }

    public enum JobTypes {
        UserVisitAndEmail,
        ExpiredProductsEmail;
    }
}
