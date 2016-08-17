package com.example.allison.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

/**
 * Created by Allison on 2016-08-12.
 */
public class ListFragment extends Fragment {
    public static final String EXTRA_TASK_ID_OR_NEW_TASK =
            "com.example.allison.todolist.task_id";
    private static final int REQUEST_TASK = 1;

    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    private FloatingActionButton mFAB;

    private int mTaskPosition;
    private boolean mNewTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        mRecyclerView = (RecyclerView)
                v.findViewById(R.id.list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFAB = (FloatingActionButton)
                v.findViewById(R.id.list_fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TaskActivity.class);
                UUID nullId = null;
                intent.putExtra(EXTRA_TASK_ID_OR_NEW_TASK, nullId);
                startActivityForResult(intent, REQUEST_TASK);
            }
        });
        
        updateUI();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        List<Task> tasks = TaskList.getInstance(getActivity()).getTasks();

        if (mAdapter == null) {
            mAdapter = new TaskAdapter(tasks);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            if (mNewTask == true) {
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.notifyItemChanged(mTaskPosition);
            }
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        private CheckBox mTaskCompletedCheckBox;
        private TextView mTitleTextView;

        private Task mTask;

        public TaskHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTaskCompletedCheckBox = (CheckBox)
                    itemView.findViewById(R.id.list_item_task_completed_check_box);
            // implement a listener that crosses out the list item when the box is checked
            //      consider also moving item to the bottom of the list
            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_title_text_view);
        }

        public void bindTask(Task task) {
            mTask = task;
            mTaskCompletedCheckBox.setChecked(mTask.isCompleted());
            mTitleTextView.setText(mTask.getTitle());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), TaskActivity.class);
            intent.putExtra(EXTRA_TASK_ID_OR_NEW_TASK, mTask.getId());
            startActivityForResult(intent, REQUEST_TASK);
            // TODO: start new Task fragment with info about title (and date, later)
        }

        /*@Override
        public void onLongClick(View v) {

        }*/
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_task, parent, false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bindTask(task);
        }

        public int getItemCount() {
            return mTasks.size();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        } if (requestCode == REQUEST_TASK) {
            if (data == null) {
                return;
            }
        }
        if (data.getBooleanExtra(TaskFragment.EXTRA_NEW_TASK, false) == true) {
            mNewTask = true;
//            mAdapter.notifyDataSetChanged();
        } else {
            mTaskPosition = data.getIntExtra(TaskFragment.EXTRA_TASK_POSITION, 0);
            mNewTask = false;
        }
        updateUI();
    }
}