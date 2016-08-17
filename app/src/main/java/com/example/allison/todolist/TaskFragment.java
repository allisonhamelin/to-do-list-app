package com.example.allison.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by Allison on 2016-08-15.
 */
public class TaskFragment extends Fragment {
    private static final String ARG_TASK_ID = "task_id";
    public static final String EXTRA_TASK_POSITION =
            "com.example.allison.todo.task_position";
    public static final String EXTRA_NEW_TASK =
            "com.example.allison.todo.new_task";

    private Task mTask;
    private EditText mTitleField;

    private boolean mNewTask;
    private String mTitleBeforeChanged;

    public static TaskFragment newInstance(UUID taskId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskId);

        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID taskId = (UUID)
                getArguments().getSerializable(ARG_TASK_ID);

        mTitleBeforeChanged = null;

        if (taskId == null) {
            mTask = new Task();
            addTaskToTasklist();
            mNewTask = true;
        } else if (taskId != null) {
            mTask = TaskList.getInstance(getActivity())
                    .getTaskById(taskId);
            mTitleBeforeChanged = mTask.getTitle();
            mNewTask = false;
        }
        returnResult(mNewTask);
    }

    private void returnResult(Boolean newTask) {
        TaskList taskList = TaskList.getInstance(getActivity());
        Intent data = new Intent();
        data.putExtra(EXTRA_TASK_POSITION,
                taskList.getPositionOfTaskById(mTask.getId()));
        data.putExtra(EXTRA_NEW_TASK, newTask);
        getActivity().setResult(Activity.RESULT_OK, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_task, container, false);

        mTitleField = (EditText) v.findViewById(R.id.to_do_item_edit_text);
        if (mNewTask == false) {
            mTitleField.setText(mTask.getTitle());

        }
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mTask.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // makes app totally crash. 
        /*if (mTask.getTitle().isEmpty()) {
            mTask.setTitle(mTitleBeforeChanged);
        }*/
        return v;
    }

    private void addTaskToTasklist() {
        TaskList taskList = TaskList.getInstance(getActivity());
        taskList.addTask(mTask);
    }
}
