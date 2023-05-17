package com.example.diplomaapplication.Repository;

import com.example.diplomaapplication.Model.CourseListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class CourseListRepository {
    private onFireStoreTaskComplete onFireStoreTaskComplete;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firebaseFirestore.collection("Course");

    public CourseListRepository(onFireStoreTaskComplete onFireStoreTaskComplete) {
        this.onFireStoreTaskComplete = onFireStoreTaskComplete;
    }

    public void getCourseData() {
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onFireStoreTaskComplete.courseDataLoaded(task.getResult()
                        .toObjects(CourseListModel.class));
            } else {
                onFireStoreTaskComplete.onError(task.getException());
            }
        });
    }

    public interface onFireStoreTaskComplete {
        void courseDataLoaded(List<CourseListModel> courseListModel);
        void onError(Exception e);
    }
}
