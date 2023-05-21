package com.example.diplomaapplication.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.diplomaapplication.Adapter.SubjectAdapter;
import com.example.diplomaapplication.Model.SubjectModel;
import com.example.diplomaapplication.R;
import com.example.diplomaapplication.View.CourseFragmentDirections;
import com.example.diplomaapplication.ViewModel.SubjectViewModel;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseFragment extends Fragment implements SubjectAdapter.OnItemClickedListener {

    // for recycleView of subjects
    private RecyclerView recyclerView;
    private SubjectAdapter adapter;
    private ImageButton goToHomeBtn;

    // for navigation to other fragments
    private NavController navController;

    // for getting data of subjects
    private SubjectViewModel viewModel;

    // arguments
    private int arg_course, arg_semester;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(SubjectViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // settings for recyclerView
        recyclerView = view.findViewById(R.id.subjectsRecyclerView);
        navController = Navigation.findNavController(view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SubjectAdapter(this);
        recyclerView.setAdapter(adapter);

        // get arguments
        arg_course = CourseFragmentArgs.fromBundle(getArguments()).getCourse();
        arg_semester = CourseFragmentArgs.fromBundle(getArguments()).getSemester();

        // get data from DB (saved in LiveListData
        viewModel.getSubjectListLiveData().observe(getViewLifecycleOwner(), subjectModels -> {
            List<SubjectModel> filteredSubjects = subjectModels.stream()
                    .filter(subjectModel -> subjectModel.getCourse() == arg_course &&
                            subjectModel.getSemester() == arg_semester)
                    .collect(Collectors.toList());

            adapter.setSubjectModelList(filteredSubjects);
            adapter.notifyDataSetChanged();
        });

        // set button "go back to home page" behaviour
        goToHomeBtn = view.findViewById(R.id.backToHomeButton);
        goToHomeBtn.setOnClickListener(view1 -> navController.navigate(CourseFragmentDirections.actionFirstCourseFragmentToHomeFragment()));

    }

    // navigate to SubjectFragment
    @Override
    public void onItemClick(String subjectId) {
        CourseFragmentDirections.ActionFirstCourseFragmentToSubjectFragment action =
                CourseFragmentDirections.actionFirstCourseFragmentToSubjectFragment();
        action.setSubjectId(subjectId);
        navController.navigate(action);
    }
}