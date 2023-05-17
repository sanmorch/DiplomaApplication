package com.example.diplomaapplication.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.diplomaapplication.Model.CourseListModel;
import com.example.diplomaapplication.Repository.CourseListRepository;

import java.util.List;

public class CourseListViewModel extends ViewModel implements CourseListRepository.onFireStoreTaskComplete {

    private MutableLiveData<List<CourseListModel>> courseListLiveData = new MutableLiveData<>();
    private CourseListRepository repository = new CourseListRepository(this);

    public MutableLiveData<List<CourseListModel>> getCourseListLiveData() {
        return courseListLiveData;
    }

    public CourseListViewModel() {
        repository.getCourseData();
    }

    @Override
    public void courseDataLoaded(List<CourseListModel> courseListModel) {
        courseListLiveData.setValue(courseListModel);
    }

    @Override
    public void onError(Exception e) {
        Log.d("CourseERROR", "onError: " + e.getMessage());
    }
}
