package com.example.allison.todolist;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Allison on 2016-08-15.
 */
public class TaskList {
    private static TaskList sTaskList;

    private List<Task> mTasks;

    public static TaskList getInstance(Context context) {
        if (sTaskList == null) {
            sTaskList = new TaskList();
        }
        return sTaskList;
    }

    private TaskList() {
        mTasks = new ArrayList<>();
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    public void addTask(Task task) {
        mTasks.add(task);
    }

    public void removeTaskById(UUID id) {
        for (Task task : mTasks) {
            if (task.getId().equals(id)) {
                mTasks.remove(task);
            }
        }
    }
    // TODO: handle case when no task with id exists (throw exception)
    public Task getTaskById(UUID id) {
        for (Task task : mTasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    public int getPositionOfTaskById(UUID id) {
        Task desiredTask = null;
        for (Task task : mTasks) {
            if (task.getId().equals(id)) {
                desiredTask = task;
            }
        }
        return mTasks.indexOf(desiredTask);
    }
}
