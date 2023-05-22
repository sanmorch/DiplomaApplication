package com.example.diplomaapplication.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.diplomaapplication.Model.QuestionModel;
import com.example.diplomaapplication.Repository.QuestionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class QuestionViewModel extends ViewModel implements QuestionRepository.OnQuestionLoad, QuestionRepository.OnResultAdded, QuestionRepository.OnResultLoad {

    private MutableLiveData<List<QuestionModel>> questionMutableLiveData;
    private MutableLiveData<HashMap<String, Long>> resultMutableLiveData;
    private QuestionRepository repository;

    public QuestionViewModel() {
        questionMutableLiveData = new MutableLiveData<>();
        resultMutableLiveData = new MutableLiveData<>();
        repository = new QuestionRepository(this, this, this);
    }

    public void addResults(HashMap<String, Object> resultMap) {
        repository.addResults(resultMap);
    }

    public void setSubjectID(String subjectId) {
        repository.setSubjectID(subjectId);
    }


    public void getQuestions() {
        repository.getQuestions();
    }

    public MutableLiveData<List<QuestionModel>> getQuestionMutableLiveData() {
        return questionMutableLiveData;
    }

    @Override
    public void onLoad(List<QuestionModel> questionModels) {
        questionMutableLiveData.setValue(questionModels);
    }

    @Override
    public boolean onSubmit() {
        return true;
    }

    public MutableLiveData<HashMap<String, Long>> getResultMutableLiveData() {
        return resultMutableLiveData;
    }

    public void getResults() {
        repository.getResults();
    }

    @Override
    public void onResultLoad(HashMap<String, Long> resultMap) {
        resultMutableLiveData.setValue(resultMap);
    }

    @Override
    public void onError(Exception e) {
        Log.d("QuizERROR", "OnError: " + e.getMessage());
    }
}
