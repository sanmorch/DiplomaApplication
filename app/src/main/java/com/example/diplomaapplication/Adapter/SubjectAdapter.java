package com.example.diplomaapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplomaapplication.Model.SubjectModel;
import com.example.diplomaapplication.R;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private List<SubjectModel> subjectModelList;

    // interface for clicking on item of RecycleView
    private OnItemClickedListener onItemClickedListener;

    public void setSubjectModelList(List<SubjectModel> subjectModelList) {
        this.subjectModelList = subjectModelList;
    }

    public SubjectAdapter(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    // при создании элемента класса ViewHolder сохраняем состояние представления
    // как each_course (CardView, для каждого отдельного элемента)
    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_subject, parent, false);
        return new SubjectViewHolder(view);
    }

    // алгоритм назначения всем элементам названия дисциплины
    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        SubjectModel model = subjectModelList.get(position);
        holder.title.setText(model.getName());
    }

    // возвращает кол-во элементов recyclerView
    @Override
    public int getItemCount() {
        if (subjectModelList == null) {
            return 0;
        } else {
            return subjectModelList.size();
        }
    }

    // исп. для для кэширования ссылок на эти элементы
    // может быть переиспользован для разных элементов списка, что улучшает производительность
    public class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private ConstraintLayout constraintLayout;
        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);

            // pointer to the cardView title field
            title = itemView.findViewById(R.id.subjectTitleList);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            constraintLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickedListener != null) {
                int position = getAdapterPosition();
                SubjectModel subjectModel = subjectModelList.get(position);
                onItemClickedListener.onItemClick(subjectModel.getSubjectID());
            }
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(String subjectId);
    }

}
