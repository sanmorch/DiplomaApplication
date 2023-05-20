package com.example.diplomaapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.diplomaapplication.R;
import com.example.diplomaapplication.Repository.Course;


import java.util.ArrayList;


public class CourseListAdapter extends ArrayAdapter<Course> {
    private ImageView imageView;
    private TextView courseName;

    public CourseListAdapter(@NonNull Context context, ArrayList<Course> courseArrayList) {
        super(context, R.layout.list_item, courseArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Course course = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        imageView = view.findViewById(R.id.listImage);
        courseName = view.findViewById(R.id.listName);

        imageView.setImageResource(course.getImage());
        courseName.setText(course.getName());

        return view;
    }
}
