package com.example.diplomaapplication.Repository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class SubjectListRepository {

    private SubjectListRepository.OnFireStoreTaskComplete onFireStoreTaskComplete;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firebaseFirestore
            .collection("Course").document(courseId)
            .collection("Subjects");

    public SubjectListRepository(SubjectListRepository.OnFireStoreTaskComplete onFireStoreTaskComplete) {
        this.onFireStoreTaskComplete = onFireStoreTaskComplete;
    }

    public void getCourseData() {
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onFireStoreTaskComplete.courseDataLoaded(task.getResult()
                        .toObjects(SubjectListModel.class));
            } else {
                onFireStoreTaskComplete.onError(task.getException());
            }
        });
    }

    public interface OnFireStoreTaskComplete {
        void courseDataLoaded(List<SubjectListModel> courseListModel);
        void onError(Exception e);
    }

}
