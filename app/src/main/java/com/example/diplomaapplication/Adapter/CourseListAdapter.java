package com.example.diplomaapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplomaapplication.Model.CourseListModel;
import com.example.diplomaapplication.R;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder> {

    private List<CourseListModel> courseListModel;
    private OnItemClickedListener onItemClickedListener;

    public void setCourseListModel(List<CourseListModel> courseListModel) {
        this.courseListModel = courseListModel;
    }

    @NonNull
    @Override
    public CourseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_course, parent, false);
        return new CourseListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseListViewHolder holder, int position) {
        CourseListModel model = courseListModel.get(position);
        holder.title.setText(model.getTitle());
    }

    @Override
    public int getItemCount() {
        if (courseListModel == null) {
            return 0;
        } else {
            return courseListModel.size();
        }
    }

    public class CourseListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private ConstraintLayout constraintLayout;

        public CourseListViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.courseTitleList);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            constraintLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickedListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(int position);
    }
}
