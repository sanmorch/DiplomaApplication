package com.example.diplomaapplication.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.diplomaapplication.Model.SubjectModel;
import com.example.diplomaapplication.Repository.SubjectRepository;

import java.util.List;

public class SubjectViewModel extends ViewModel implements SubjectRepository.OnFirestormTaskComplete {

    private MutableLiveData<List<SubjectModel>> subjectListLiveData = new MutableLiveData<>();
    private SubjectRepository repository = new SubjectRepository(this);

    public MutableLiveData<List<SubjectModel>> getSubjectListLiveData() {
        return subjectListLiveData;
    }

    public SubjectViewModel() {
        repository.getSubjectData();
    }

    @Override
    public void subjectDataLoaded(List<SubjectModel> subjectListModel) {
        subjectListLiveData.setValue(subjectListModel);
    }

    @Override
    public void onError(Exception e) {
        Log.d("SubjectERROR", "onError: " + e.getMessage());
    }
}
