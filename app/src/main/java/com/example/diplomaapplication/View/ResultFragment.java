package com.example.diplomaapplication.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.diplomaapplication.R;
import com.example.diplomaapplication.View.ResultFragmentDirections;
import com.example.diplomaapplication.ViewModel.QuestionViewModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class ResultFragment extends Fragment {

    // navigation to homeFragment
    private NavController navController;

    // for DB
    private CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Users");
    private DocumentReference documentReference;
    private String userID;

    // for getting result data from DB
    private String subjectID;
    private QuestionViewModel viewModel;

    // fragment's elements
    private TextView correctAnswer, wrongAnswer;
    private TextView percentTextView;
    private ProgressBar scoreProgressBar;
    private Button homeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init
        navController = Navigation.findNavController(view);
        correctAnswer = view.findViewById(R.id.tvCorrectAnswersCount);
        wrongAnswer = view.findViewById(R.id.tvWrongAnswerCount);
        percentTextView = view.findViewById(R.id.resultPercentageTv);
        scoreProgressBar = view.findViewById(R.id.resultProgressBar);
        homeButton = view.findViewById(R.id.homeButton);

        homeButton.setOnClickListener(view1 -> {
            ResultFragmentDirections.ActionResultFragmentToSubjectFragment action =
                    ResultFragmentDirections.actionResultFragmentToSubjectFragment();
            action.setSubjectId(subjectID);
            navController.navigate(action);
        });

        subjectID = ResultFragmentArgs.fromBundle(getArguments()).getSubjectID();
        viewModel.setSubjectID(subjectID);
        viewModel.getResults();
        viewModel.getResultMutableLiveData().observe(getViewLifecycleOwner(), stringLongHashMap -> {
            Long correct = stringLongHashMap.get("correct");
            Long wrong = stringLongHashMap.get("wrong");

            correctAnswer.setText(correct.toString());
            wrongAnswer.setText(wrong.toString());

            Long total = correct + wrong;
            Long percent = (correct*100)/total;

            percentTextView.setText(String.valueOf(percent).concat("%"));
            scoreProgressBar.setProgress(percent.intValue());
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuestionViewModel.class);
    }
}