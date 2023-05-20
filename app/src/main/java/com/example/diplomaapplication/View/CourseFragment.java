package com.example.diplomaapplication.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.diplomaapplication.Model.CourseListModel;
import com.example.diplomaapplication.R;
import com.example.diplomaapplication.Repository.Subject;
import com.example.diplomaapplication.ViewModel.CourseListViewModel;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment implements View.OnClickListener {

    private TextView heading;
    private NavController navController;
    private int position;
    private CourseListViewModel viewModel;
    private DatabaseReference databaseReference;
    private int courseNum;

    // for buttons
    protected MaterialButtonToggleGroup toggleGroup;
    private Button firstSemesterButton, secondSemesterButton;
    private TextView semesterName;


    // DELETE for listView
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private List<Subject> listSubject;


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
                .getInstance(getActivity().getApplication())).get(CourseListViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // init for first semester
        firstSemesterButton = view.findViewById(R.id.firstSemesterButton);
        init(view);


        // show list of disciplines
        viewModel.getCourseListLiveData().observe(getViewLifecycleOwner(), courseListModels -> {
            CourseListModel course = courseListModels.get(position);
            heading.setText(course.getHeaderCourse());

            // ЗАДЕРЖКА (зачем...)
            Handler handler = new Handler();
            handler.postDelayed(() -> {

            }, 2000);

            courseNum = course.getCourseNum();

            // show by default data about first semester this course
            getDataFromDB(courseNum, 1);

        });


    }

    private void getDataFromDB(int course, int semester) {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listData.size() > 0) listData.clear();
                if (listSubject.size() > 0) listSubject.clear();
                // record data from database from first course, first semester
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Subject subject = ds.getValue(Subject.class);
                    assert subject != null;
                    if (subject.course == course && subject.semester == semester) {
                        listData.add(subject.name);
                        listSubject.add(subject);
                    }
                }
                // notify arrayAdapter about changing data
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(vListener);
    }


    private void init(View view) {
        heading = view.findViewById(R.id.headingTextView);
        navController = Navigation.findNavController(view);
        position = FirstCourseFragmentArgs.fromBundle(getArguments()).getPosition();
        semesterName = view.findViewById(R.id.nameSemesterFirstCourse);

        //init for list
        listView = view.findViewById(R.id.listFirstCourse);
        listData = new ArrayList<>();
        listSubject = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
        setOnClickItem();

        //init for toggleGroup
        toggleGroup = view.findViewById(R.id.toggleButtonGroup);

        // init for first semester
        firstSemesterButton = view.findViewById(R.id.firstSemesterButton);
        firstSemesterButton.setOnClickListener(this);

        // init for second semester
        secondSemesterButton = view.findViewById(R.id.secondSemesterButton);
        secondSemesterButton.setOnClickListener(this);

        //init for DB
        databaseReference = FirebaseDatabase.getInstance().getReference("Subjects");
    }

    // for buttons "First Semester"/"Second Semester"
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.firstSemesterButton) {
            semesterName.setText("Первый семестр");
            getDataFromDB(courseNum,1);
        }
        if (view.getId() == R.id.secondSemesterButton) {
            semesterName.setText("Второй семестр");
            getDataFromDB(courseNum, 2);
        }
    }

    // go to the subjectPage with data about this subject
    private void setOnClickItem() {
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Subject subjectSelected = listSubject.get(i);
            FirstCourseFragmentDirections.ActionFirstCourseFragmentToSubjectFragment action =
                    FirstCourseFragmentDirections.actionFirstCourseFragmentToSubjectFragment();
            action.setSubjectId(subjectSelected.getKey());
            action.setSubjectName(subjectSelected.getName());
            action.setSubjectDesc(subjectSelected.getDescription());
            navController.navigate(action);
        });
    }
}