package com.example.diplomaapplication.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.diplomaapplication.Model.QuestionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

public class QuestionRepository {
    private FirebaseFirestore firebaseFirestore;
    private String subjectId, courseId, subjectName;

    // interfaces
    private OnQuestionLoad onQuestionLoad;
    private OnResultAdded onResultAdded;

    // for saving user results
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();


    //
    public void addResults(HashMap<String, Object> resultMap) {
        firebaseFirestore.collection("Results").document(currentUserId)
                .collection("Course").document(courseId)
                .collection("Subject").document(subjectId)
                .set(resultMap).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        onResultAdded.onSubmit();
                    } else {
                        onResultAdded.onError(task.getException());
                    }
                });
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public QuestionRepository(OnQuestionLoad onQuestionLoad, OnResultAdded onResultAdded) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onQuestionLoad = onQuestionLoad;
        this.onResultAdded = onResultAdded;
    }

    public void getQuestions() {
        firebaseFirestore.collection("Course").document(courseId).collection("Subjects")
                .document(subjectId).collection("Questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            onQuestionLoad.onLoad(task.getResult().toObjects(QuestionModel.class));
                        }else {
                            onQuestionLoad.onError(task.getException());
                        }
                    }
                });
    }

    public interface OnQuestionLoad{
        void onLoad(List<QuestionModel> questionModels);
        void onError(Exception e);
    }

    public interface OnResultAdded {
        boolean onSubmit();
        void onError(Exception e);
    }


}
