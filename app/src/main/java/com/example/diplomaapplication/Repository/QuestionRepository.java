package com.example.diplomaapplication.Repository;

import androidx.annotation.NonNull;

import com.example.diplomaapplication.Model.QuestionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class QuestionRepository {
    private FirebaseFirestore firebaseFirestore;

    // for getting data from DB collection
    private String subjectID;

    // interfaces
    private OnQuestionLoad onQuestionLoad;
    private OnResultAdded onResultAdded;

    // ID of currentUser for saving result
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public void addResults(HashMap<String, Object> resultMap) {
        firebaseFirestore.collection("Subjects").document(subjectID)
                .collection("Results").document(currentUserId)
                .set(resultMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            onResultAdded.onSubmit();
                        } else {
                            onResultAdded.onError(task.getException());
                        }
                    }
                });
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public QuestionRepository(OnQuestionLoad onQuestionLoad, OnResultAdded onResultAdded) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onQuestionLoad = onQuestionLoad;
        this.onResultAdded = onResultAdded;
    }

    public void getQuestions() {
        firebaseFirestore.collection("Subjects").document(subjectID)
                .collection("Questions").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        onQuestionLoad.onLoad(task.getResult().toObjects(QuestionModel.class));
                    } else {
                        onQuestionLoad.onError(task.getException());
                    }
                });
    }

    public interface OnQuestionLoad {
        void onLoad(List<QuestionModel> questionModels);
        void onError(Exception e);
    }

    public interface OnResultAdded {
        boolean onSubmit();
        void onError(Exception e);
    }
}
