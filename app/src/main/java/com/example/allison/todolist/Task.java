package com.example.allison.todolist;

import java.util.UUID;

/**
 * Created by Allison on 2016-08-15.
 */
public class Task {
    private UUID mId;
    private String mTitle;
    private boolean mCompleted;

    public Task() {
        mId = UUID.randomUUID();
        mTitle = null;
        mCompleted = false;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }
}
