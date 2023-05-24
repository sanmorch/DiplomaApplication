package com.example.diplomaapplication.Repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class AuthRepository {
    private Application application;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private FirebaseAuth mAuth;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firebaseFirestore.collection("Users");

    Map<String, Object> user = new HashMap<>();

    public AuthRepository(Application application) {
        this.application = application;
        firebaseUserMutableLiveData = new MutableLiveData<>();
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(String email, String password, String name, String username) {
        // sign up user (creating profile for authorization Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    // if user created
                    if(task.isSuccessful()) {
                        // then create a new object of User in Firebase
                        // and add user to the list
                        firebaseUserMutableLiveData.postValue(mAuth.getCurrentUser());
                        user.put("name", name);
                        user.put("username", username);
                        user.put("email", email);
                        user.put("testCount", 0);
                        user.put("correctSum", 0);
                        user.put("wrongSum", 0);
                        user.put("profilePhoto", null);

                        reference.document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user). addOnCompleteListener(task1 -> {
                            if(task1.isSuccessful()) {
                                Toast.makeText(application, "Вы успешно зарегистрированы", Toast.LENGTH_SHORT).show();
                                mAuth.signInWithEmailAndPassword(email, password);
                            }
                            else {
                                Toast.makeText(application, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                firebaseUserMutableLiveData.postValue(mAuth.getCurrentUser());
            }
            else {
                Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public void signOut() {
        // sign out
        FirebaseAuth.getInstance().signOut();
    }
}
