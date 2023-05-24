package com.example.diplomaapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplomaapplication.Course;
import com.example.diplomaapplication.R;

import java.util.ArrayList;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {
    private ArrayList<Course> courseArrayList;
    private OnItemClickListener listener;

    public CourseListAdapter(ArrayList<Course> courseArrayList) {
        this.courseArrayList = courseArrayList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseArrayList.get(position);
        holder.imageView.setImageResource(course.getImage());
        holder.courseName.setText(course.getName());
    }

    @Override
    public int getItemCount() {
        return courseArrayList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView courseName;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.listImage);
            courseName = itemView.findViewById(R.id.listName);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(v, position);
                    }
                }
            });
        }
    }
}