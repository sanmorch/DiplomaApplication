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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diplomaapplication.Adapter.CourseListAdapter;
import com.example.diplomaapplication.Model.CourseListModel;
import com.example.diplomaapplication.R;
import com.example.diplomaapplication.ViewModel.AuthViewModel;
import com.example.diplomaapplication.ViewModel.CourseListViewModel;

import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private NavController navController;
    private CourseListViewModel viewModel;
    private CourseListAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
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

        recyclerView = view.findViewById(R.id.listCourseRecyclerview);
        navController = Navigation.findNavController(view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CourseListAdapter();

        recyclerView.setAdapter(adapter);

        viewModel.getCourseListLiveData().observe(getViewLifecycleOwner(), new Observer<List<CourseListModel>>() {
            @Override
            public void onChanged(List<CourseListModel> courseListModels) {
                adapter.setCourseListModel(courseListModels);
                adapter.notifyDataSetChanged();
            }
        });

    }


}