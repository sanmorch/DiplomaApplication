package com.example.diplomaapplication.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.diplomaapplication.Adapter.CourseListAdapter;
import com.example.diplomaapplication.Adapter.SubjectListAdapter;
import com.example.diplomaapplication.R;
import com.example.diplomaapplication.Repository.Course;
import com.example.diplomaapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements SubjectListAdapter.OnItemClickedListener {
    private ListView listView;
    private CourseListAdapter listAdapter;
    private ArrayList<Course> courseArrayList = new ArrayList<>();

    // static data for my courses
    private int[] imageList = {R.drawable.first_course_new_menu, R.drawable.second_course_new_menu,
            R.drawable.third_course_new_menu, R.drawable.fourth_course_new_menu};
    private String[] nameList = {"ПЕРВЫЙ КУРС", "ВТОРОЙ КУРС", "ТРЕТИЙ КУРС", "ЧЕТВЕРТЫЙ КУРС"};
    private Course course;

    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        for (int i = 0; i < imageList.length; i++){
            course = new Course(nameList[i], imageList[i]);
            courseArrayList.add(course);
        }
        listAdapter = new CourseListAdapter(getActivity(), courseArrayList);
        listView = view.findViewById(R.id.courseListView);
        listView.setAdapter(listAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemClick(int position) {
        HomeFragmentDirections.ActionHomeFragmentToFirstCourseFragment2 action =
                HomeFragmentDirections.actionHomeFragmentToFirstCourseFragment2();
        action.setPosition(position);
        navController.navigate(action);
    }
}