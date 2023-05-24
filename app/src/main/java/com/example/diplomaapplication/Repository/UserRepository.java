package com.example.diplomaapplication.Repository;

import com.example.diplomaapplication.Model.SubjectModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class UserRepository {

    private HashMap<String, Long> resultMap = new HashMap<>();
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firebaseFirestore.collection("Users");

    private SubjectRepository.OnFirestormTaskComplete onFirestormTaskComplete;

    public UserRepository(SubjectRepository.OnFirestormTaskComplete onFirestormTaskComplete) {
        this.onFirestormTaskComplete = onFirestormTaskComplete;
    }

    public interface OnFirestormTaskComplete {
        void subjectDataLoaded(List<SubjectModel> subjectListModel);
        void onError(Exception e);
    }
}
