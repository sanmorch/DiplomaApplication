package com.example.diplomaapplication.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diplomaapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubjectFragment extends Fragment {
    // textViews for name and description of subject
    private TextView tvSubjectName, tvSubjectDescription;

    // for navigation
    NavController navController;

    // arguments from previous fragment
    private String arg_key, arg_name, arg_description;

    // TEST for getting data from DB by position
    DatabaseReference reference;

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
        tvSubjectName.setText(arg_name);
        tvSubjectDescription.setText(arg_description);

    }

    private void init(View view) {
        // init for fragment's views
        tvSubjectName = view.findViewById(R.id.subject_name);
        tvSubjectDescription = view.findViewById(R.id.subject_desc);

        // arguments
        arg_key = SubjectFragmentArgs.fromBundle(getArguments()).getSubjectId();
        arg_name = SubjectFragmentArgs.fromBundle(getArguments()).getSubjectName();
        arg_description = SubjectFragmentArgs.fromBundle(getArguments()).getSubjectDesc();


        // init for navigation
        navController = Navigation.findNavController(view);

        // TEST init for DB
        reference = FirebaseDatabase.getInstance().getReference("Subjects");
    }
}