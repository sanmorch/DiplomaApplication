package com.example.diplomaapplication.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diplomaapplication.Model.QuestionModel;
import com.example.diplomaapplication.R;
import com.example.diplomaapplication.ViewModel.QuestionViewModel;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;


public class QuizFragment extends Fragment {
    private QuestionViewModel viewModel;
    private NavController navController;

    // for statistics on the screen
    private long totalQuestions = 60;
    private int percent = 0;
    private int currentQuestionNumber = 0;

    // for statistics on result fragment
    private int correctAnswered = 0;
    private int wrongAnswered = 0;

    // fragment's elements
    private Button option1Btn, option2Btn, option3Btn, option4Btn, nextQuestionBtn;
    private TextView questionTv, ansFeedBackTv, questionNumberTv, subjectNameTv;
    private ProgressBar progressBar;
    private ImageButton closeQuizBtn;
    private boolean canAnswer = false;

    // for data from collection
    private String answer = "";

    // arguments from previous fragment
    private String subjectId, subjectName, courseId;



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

        navController = Navigation.findNavController(view);

        //init for fragment elements
        init(view);
        loadData();
    }

    private void init(View view) {
        closeQuizBtn = view.findViewById(R.id.closeQuizButton);
        option1Btn = view.findViewById(R.id.Answer1Button);
        option2Btn = view.findViewById(R.id.Answer2Button);
        option3Btn = view.findViewById(R.id.Answer3Button);
        option4Btn = view.findViewById(R.id.Answer4Button);
        nextQuestionBtn = view.findViewById(R.id.nextQuestionButton);
        ansFeedBackTv = view.findViewById(R.id.tvExplanation);
        questionTv = view.findViewById(R.id.tvQuizQuestion);
        questionNumberTv = view.findViewById(R.id.quizQuestionsCount);
        subjectNameTv = view.findViewById(R.id.tvSubjectTitle);
        progressBar = view.findViewById(R.id.quizProgressBar);

        subjectId = QuizFragmentArgs.fromBundle(getArguments()).getSubjectId();
        viewModel.setSubjectId(subjectId);

        option1Btn.setOnClickListener(view12 -> {
            verifyAnswer(option1Btn);
            showNextButton();
        });
        option2Btn.setOnClickListener(view13 -> {
            verifyAnswer(option2Btn);
            showNextButton();
        });
        option3Btn.setOnClickListener(view14 -> {
            verifyAnswer(option3Btn);
            showNextButton();
        });
        option4Btn.setOnClickListener(view15 -> {
            verifyAnswer(option4Btn);
            showNextButton();
        });
        nextQuestionBtn.setOnClickListener(view16 -> {
            if (currentQuestionNumber == totalQuestions) {
                submitResults();
            } else {
                currentQuestionNumber++;
                loadQuestions(currentQuestionNumber);
                resetOptions();
            }
        });

        closeQuizBtn.setOnClickListener(view1 -> navController.navigate(R.id.action_quizFragment_to_subjectFragment));


    }

    private void submitResults() {
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("correct", correctAnswered);
        resultMap.put("wrong", wrongAnswered);

        viewModel.addResults(resultMap);
        navController.navigate(R.id.action_quizFragment_to_resultFragment);
    }

    // when user click on NextQuestionBtn
    // then we make it back with color and button's visibility
    private void resetOptions() {
        ansFeedBackTv.setText(View.INVISIBLE);
        nextQuestionBtn.setVisibility(View.INVISIBLE);
        nextQuestionBtn.setEnabled(false);
        option1Btn.setBackground(ContextCompat.getDrawable(getContext(), R.color.bg_selector_quiz_answers));
        option2Btn.setBackground(ContextCompat.getDrawable(getContext(), R.color.bg_selector_quiz_answers));
        option3Btn.setBackground(ContextCompat.getDrawable(getContext(), R.color.bg_selector_quiz_answers));
        option4Btn.setBackground(ContextCompat.getDrawable(getContext(), R.color.bg_selector_quiz_answers));
    }

    private void loadData() {
        enableOptions();
        loadQuestions(1);
    }

    private void enableOptions() {
        option1Btn.setVisibility(View.VISIBLE);
        option2Btn.setVisibility(View.VISIBLE);
        option3Btn.setVisibility(View.VISIBLE);
        option4Btn.setVisibility(View.VISIBLE);

        // enable buttons, hide feedback tv, hide nextQuestionBtn

        option1Btn.setEnabled(true);
        option2Btn.setEnabled(true);
        option3Btn.setEnabled(true);
        option4Btn.setEnabled(true);

        ansFeedBackTv.setVisibility(View.INVISIBLE);
        nextQuestionBtn.setVisibility(View.INVISIBLE);
    }

    private void loadQuestions(int i) {
        currentQuestionNumber = i;
        percent = (int) (i * 100 / totalQuestions);
        progressBar.setProgress(percent);
        viewModel.getQuestionMutableLiveData().observe(getViewLifecycleOwner(), questionModels -> {
            questionTv.setText(questionModels.get(i - 1).getQuestion());
            option1Btn.setText(questionModels.get(i-1).getOption_a());
            option2Btn.setText(questionModels.get(i-1).getOption_b());
            option3Btn.setText(questionModels.get(i-1).getOption_c());
            option4Btn.setText(questionModels.get(i-1).getOption_d());
            answer = questionModels.get(i-1).getAnswer();
        });
        canAnswer = true;
    }



    private void showNextButton() {
        if (currentQuestionNumber == totalQuestions) {
            nextQuestionBtn.setText("Результаты");
            nextQuestionBtn.setEnabled(true);
            nextQuestionBtn.setVisibility(View.VISIBLE);
        } else {
            nextQuestionBtn.setText("Следующий вопрос");
            nextQuestionBtn.setEnabled(true);
            nextQuestionBtn.setVisibility(View.VISIBLE);
        }
    }

    private void verifyAnswer(Button button) {
        if (canAnswer) {
            if (answer.equals(button.getText())) {
                button.setBackground(ContextCompat.getDrawable(getContext(), R.color.green_correctAnswer));
                correctAnswered++;
                ansFeedBackTv.setText("Правильный ответ");
            } else {
                button.setBackground(ContextCompat.getDrawable(getContext(), R.color.red_wrongAnswer));
                wrongAnswered++;
                ansFeedBackTv.setText("Вы ошиблись, но это нормально, все ошибаются \n Правильный ответ: " + answer);
            }
        }
        canAnswer = false;
        showNextButton();
    }
}