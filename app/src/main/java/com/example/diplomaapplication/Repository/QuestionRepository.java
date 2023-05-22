package com.example.diplomaapplication.Repository;

import com.example.diplomaapplication.Model.QuestionModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class QuestionRepository {
    private FirebaseFirestore firebaseFirestore;

    // for getting data from DB collection
    private String subjectID;

    // interface
    private OnQuestionLoad onQuestionLoad;

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public QuestionRepository(OnQuestionLoad onQuestionLoad) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onQuestionLoad = onQuestionLoad;
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
}
