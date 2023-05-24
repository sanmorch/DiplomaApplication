package com.example.diplomaapplication.Repository;

import com.example.diplomaapplication.Model.SubjectModel;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;
import java.util.List;

public class SubjectRepository {
    private OnFirestormTaskComplete onFirestormTaskComplete;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firebaseFirestore.collection("Subjects");


    public SubjectRepository(OnFirestormTaskComplete onFirestormTaskComplete) {
        this.onFirestormTaskComplete = onFirestormTaskComplete;
    }
    public void getSubjectData() {
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<SubjectModel> subjectList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    SubjectModel subject = document.toObject(SubjectModel.class);
                    subjectList.add(subject);
                }
                onFirestormTaskComplete.subjectDataLoaded(task.getResult()
                        .toObjects(SubjectModel.class));
            } else {
                onFirestormTaskComplete.onError(task.getException());
            }
        });
    }

    public interface OnFirestormTaskComplete {
        void subjectDataLoaded(List<SubjectModel> subjectListModel);
        void onError(Exception e);
    }
}
