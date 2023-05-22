package com.example.diplomaapplication.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.diplomaapplication.Model.QuestionModel;
import com.example.diplomaapplication.Repository.QuestionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class QuestionViewModel extends ViewModel implements QuestionRepository.OnQuestionLoad, QuestionRepository.OnResultAdded {

    private MutableLiveData<List<QuestionModel>> questionMutableLiveData;
    private QuestionRepository repository;

    public QuestionViewModel() {
        questionMutableLiveData = new MutableLiveData<>();
        repository = new QuestionRepository(this, this);
    }

    public void addResults(HashMap<String, Object> resultMap) {
        repository.addResults(resultMap);
    }

    public void setSubjectID(String subjectId) {
        repository.setSubjectID(subjectId);
        repository.getQuestions();
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

    @Override
    public void onError(Exception e) {
        Log.d("QuizERROR", "OnError: " + e.getMessage());
    }
}
