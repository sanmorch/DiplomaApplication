package com.example.diplomaapplication.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.diplomaapplication.Model.QuestionModel;
import com.example.diplomaapplication.Repository.QuestionRepository;

import java.util.HashMap;
import java.util.List;

public class QuestionViewModel extends ViewModel implements QuestionRepository.OnQuestionLoad, QuestionRepository.OnResultAdded {
    private MutableLiveData<List<QuestionModel>> questionMutableLiveData;
    private QuestionRepository repository;

    public MutableLiveData<List<QuestionModel>> getQuestionMutableLiveData() {
        return questionMutableLiveData;
    }

    public QuestionViewModel() {
        questionMutableLiveData = new MutableLiveData<>();
        repository = new QuestionRepository(this, this);
    }

    public void addResults(HashMap<String, Object> resultMap) {
        repository.addResults(resultMap);
    }

    public void setSubjectName(String subjectName) {repository.setSubjectName(subjectName);}

    public void setSubjectId(String subjectId) {

        repository.setSubjectId(subjectId);
        repository.getQuestions();
    }

    public void setCourseId(String courseId) {
        repository.setCourseId(courseId);
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
        Log.d("QuizError", "onError " + e.getMessage());
    }
}
