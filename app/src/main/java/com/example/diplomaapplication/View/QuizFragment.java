package com.example.diplomaapplication.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.diplomaapplication.R;
import com.example.diplomaapplication.ViewModel.QuestionViewModel;

public class QuizFragment extends Fragment {

    private QuestionViewModel viewModel;
    private NavController navController;
    private String subjectID;
    private long totalQuestions;

    // for fragment's elements
    private ProgressBar progressBar;
    private Button option1Btn,option2Btn,option3Btn, option4Btn, nextQuestionBtn;
    private ImageButton backToSubjectFragmentBtn;
    private TextView questionTv, answerExplanationTv, questionNumberTv;
    private TextView subjectName;
    private ImageView questionImage;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuestionViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init for fragment's pointers
        navController = Navigation.findNavController(view);
        progressBar = view.findViewById(R.id.quizProgressBar);
        option1Btn = view.findViewById(R.id.answer1Button);
        option2Btn = view.findViewById(R.id.answer2Button);
        option3Btn = view.findViewById(R.id.answer3Button);
        option4Btn = view.findViewById(R.id.answer4Button);
        nextQuestionBtn = view.findViewById(R.id.nextQuestionButton);
        backToSubjectFragmentBtn = view.findViewById(R.id.closeQuizButton);
        questionTv = view.findViewById(R.id.tvQuizQuestion);
        answerExplanationTv = view.findViewById(R.id.tvExplanation);
        questionNumberTv = view.findViewById(R.id.quizQuestionsCount);
        subjectName = view.findViewById(R.id.tvSubjectTitle);
        questionImage = view.findViewById(R.id.questionIMG);



        // get subjectID for getting data from collection
        subjectID = QuizFragmentArgs.fromBundle(getArguments()).getSubjectId();
        viewModel.setSubjectID(subjectID);
        totalQuestions = 1;

        viewModel.getQuestions();

        loadData(view);
    }

    private void loadData(View view) {
        enableOptions();
        loadQuestions(1, view);

    }

    private void enableOptions() {
        // set answer's buttons visible
        option1Btn.setVisibility(View.VISIBLE);
        option2Btn.setVisibility(View.VISIBLE);
        option3Btn.setVisibility(View.VISIBLE);
        option4Btn.setVisibility(View.VISIBLE);

        // set buttons enable
        option1Btn.setEnabled(true);
        option2Btn.setEnabled(true);
        option3Btn.setEnabled(true);
        option4Btn.setEnabled(true);

        // hide explanation and nextQuestionBtn
        answerExplanationTv.setVisibility(View.INVISIBLE);
        nextQuestionBtn.setVisibility(View.INVISIBLE);
    }

    // set data for this question
    private void loadQuestions(int i, View view) {
        viewModel.getQuestionMutableLiveData().observe(getViewLifecycleOwner(), questionModels -> {
            questionTv.setText(questionModels.get(i-1).getQuestion());
            option1Btn.setText(questionModels.get(i-1).getOption_a());
            option2Btn.setText(questionModels.get(i-1).getOption_b());
            option3Btn.setText(questionModels.get(i-1).getOption_c());
            option4Btn.setText(questionModels.get(i-1).getOption_d());


            // get questionImage if it exists
            if (!questionModels.get(i-1).getQuestion_img().isEmpty()) {
                Glide.with(view).load(questionModels.get(i-1).getQuestion_img()).into(questionImage);
            } else {
                questionImage.setVisibility(View.INVISIBLE);
            }
        });
    }

}