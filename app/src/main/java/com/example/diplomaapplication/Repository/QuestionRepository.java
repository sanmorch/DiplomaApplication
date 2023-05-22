package com.example.diplomaapplication.Repository;

import androidx.annotation.NonNull;

import com.example.diplomaapplication.Model.QuestionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class QuestionRepository {
    private FirebaseFirestore firebaseFirestore;

    // for getting data from DB collection
    private String subjectID;

    // hash map for saving the result data
    private HashMap<String, Long> resultMap = new HashMap<>();

    // interfaces
    private OnQuestionLoad onQuestionLoad;
    private OnResultAdded onResultAdded;
    private OnResultLoad onResultLoad;

    // ID of currentUser for saving result
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public void getResults() {
        firebaseFirestore.collection("Subjects").document(subjectID)
                .collection("Results").document(currentUserId)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        resultMap.put("correct", task.getResult().getLong("correct"));
                        resultMap.put("wrong", task.getResult().getLong("wrong"));
                        onResultLoad.onResultLoad(resultMap);
                    } else {
                        onResultLoad.onError(task.getException());
                    }
                });
    }

    public void addResults(HashMap<String, Object> resultMap) {
        firebaseFirestore.collection("Subjects").document(subjectID)
                .collection("Results").document(currentUserId)
                .set(resultMap).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        onResultAdded.onSubmit();
                    } else {
                        onResultAdded.onError(task.getException());
                    }
                });
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public QuestionRepository(OnQuestionLoad onQuestionLoad, OnResultAdded onResultAdded, OnResultLoad onResultLoad) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onQuestionLoad = onQuestionLoad;
        this.onResultAdded = onResultAdded;
        this.onResultLoad = onResultLoad;
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

    public interface OnResultLoad {
        void onResultLoad(HashMap<String, Long> resultMap);
        void onError(Exception e);
    }
}
