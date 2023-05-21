package com.example.diplomaapplication.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.diplomaapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SubjectFragment extends Fragment {
    // textViews for name and description of subject
    private TextView tvSubjectName, tvSubjectDescription;
    // button toGoToTheQuiz
    private Button btnGoToQuiz;

    // for navigation
    NavController navController;

    // arguments from previous fragment
    private String arg_key, arg_name, arg_description;

    // for getting data from DB
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Subjects");
    DocumentReference documentReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subject, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);


    }

    private void init(View view) {
        // get ID argument from CourseFragment
        arg_key = SubjectFragmentArgs.fromBundle(getArguments()).getSubjectId();

        // get name and description argument by ID in collection
        documentReference = collectionReference.document(arg_key);
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot currentDocument = task.getResult();
                if (currentDocument.exists()) {
                    arg_name = currentDocument.getString("name");
                    arg_description = currentDocument.getString("description");
                    tvSubjectName.setText(arg_name);
                    tvSubjectDescription.setText(arg_description);
                } else {
                    Log.d("SubjectERROR", "Document by ID not found");
                }
            } else {
                Log.d("ERROR on getting document", task.getException().getMessage());
            }
        });



        // init for fragment's views
        tvSubjectName = view.findViewById(R.id.subject_name);
        tvSubjectDescription = view.findViewById(R.id.subject_desc);

        // init for button to go to quiz
        btnGoToQuiz = view.findViewById(R.id.testYourselfButton);
        btnGoToQuiz.setOnClickListener(view1 -> {
            SubjectFragmentDirections.ActionSubjectFragmentToQuizFragment action =
                    SubjectFragmentDirections.actionSubjectFragmentToQuizFragment();
            action.setSubjectId(arg_key);
            navController.navigate(action);
        });
        
        // init for navigation
        navController = Navigation.findNavController(view);


    }
}