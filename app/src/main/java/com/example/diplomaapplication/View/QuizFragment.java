package com.example.diplomaapplication.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class QuizFragment extends Fragment implements View.OnClickListener {

    private QuestionViewModel viewModel;
    private NavController navController;

    // for checking progress in quiz
    private int currentQuestionNumber = 0;
    private boolean canAnswer = false;
    private String answer;
    private String infoMessage;

    // arguments from subjectFragment
    private String subjectID;
    private long totalQuestions = 4;

    // for fragment's elements
    private ProgressBar progressBar;
    private Button option1Btn,option2Btn,option3Btn, option4Btn, nextQuestionBtn;
    private ImageButton backToSubjectFragmentBtn;
    private TextView questionTv, answerExplanationTv, questionNumberTv;
    private TextView subjectName;
    private ImageView questionImage;

    // for results
    private int correctAnswers = 0;
    private int wrongAnswers = 0;


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

        // get subjectName by subjectID
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Subjects")
                        .document(subjectID);
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot currentDocument = task.getResult();
                if (currentDocument.exists()) {
                    subjectName.setText(currentDocument.getString("name"));
                }else {
                    Log.d("SubjectERROR", "Document by ID not found");
                }
            } else {
                Log.d("ERROR on getting document", task.getException().getMessage());
            }
        });

        viewModel.setSubjectID(subjectID);

        option1Btn.setOnClickListener(this);
        option2Btn.setOnClickListener(this);
        option3Btn.setOnClickListener(this);
        option4Btn.setOnClickListener(this);

        nextQuestionBtn.setOnClickListener(this);
        backToSubjectFragmentBtn.setOnClickListener(this);

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
        currentQuestionNumber = i;
        questionNumberTv.setText(String.valueOf(currentQuestionNumber));
        viewModel.getQuestionMutableLiveData().observe(getViewLifecycleOwner(), questionModels -> {
            questionTv.setText(questionModels.get(i-1).getQuestion());
            option1Btn.setText(questionModels.get(i-1).getOption_a());
            option2Btn.setText(questionModels.get(i-1).getOption_b());
            option3Btn.setText(questionModels.get(i-1).getOption_c());
            option4Btn.setText(questionModels.get(i-1).getOption_d());
            answer = questionModels.get(i-1).getAnswer();
            infoMessage = questionModels.get(i-1).getExplanation();


            // get questionImage if it exists
            if (!questionModels.get(i-1).getQuestion_img().isEmpty()) {
                Glide.with(view).load(questionModels.get(i-1).getQuestion_img()).into(questionImage);
            } else {
                questionImage.setVisibility(View.INVISIBLE);
            }
        });
        canAnswer = true;
        // get progress of quiz (how many you have already done)
        Long percent = Long.valueOf(currentQuestionNumber * 100 / totalQuestions);
        progressBar.setProgress(percent.intValue());
    }

    @Override
    public void onClick(View view) {
        // choose your answer
        if (view.getId() == R.id.answer1Button) {
            verifyAnswer(option1Btn);
            showNextButton();
        } else if (view.getId() == R.id.answer2Button) {
            verifyAnswer(option2Btn);
            showNextButton();
        } else if (view.getId() == R.id.answer3Button) {
            verifyAnswer(option3Btn);
            showNextButton();
        } else if (view.getId() == R.id.answer4Button) {
            verifyAnswer(option4Btn);
            showNextButton();
        }
        // go to next question OR finish quiz
        else if (view.getId() == R.id.nextQuestionButton) {
            if (currentQuestionNumber == totalQuestions) {
                submitResults();
            } else {
                currentQuestionNumber++;
                loadQuestions(currentQuestionNumber, view);
                resetOption();
            }
        }
        // go back to subjectPage
        else if (view.getId() == R.id.closeQuizButton) {
            QuizFragmentDirections.ActionQuizFragmentToSubjectFragment action =
                    QuizFragmentDirections.actionQuizFragmentToSubjectFragment();
            action.setSubjectId(subjectID);
            navController.navigate(action);
        }
    }

    private void submitResults() {
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("correct", correctAnswers);
        resultMap.put("wrong", wrongAnswers);

        viewModel.addResults(resultMap);
        navController.navigate(R.id.action_quizFragment_to_resultFragment);
    }



    // come back to start station
    private void resetOption() {
        // Сброс фона кнопок на исходный
        option1Btn.setBackground(ContextCompat.getDrawable(getContext(), R.color.bg_selector_quiz_answers));
        option2Btn.setBackground(ContextCompat.getDrawable(getContext(), R.color.bg_selector_quiz_answers));
        option3Btn.setBackground(ContextCompat.getDrawable(getContext(), R.color.bg_selector_quiz_answers));
        option4Btn.setBackground(ContextCompat.getDrawable(getContext(), R.color.bg_selector_quiz_answers));

        // Сброс цвета текста кнопок на исходный
        option1Btn.setTextColor(ContextCompat.getColor(getContext(), R.color.green_dark));
        option2Btn.setTextColor(ContextCompat.getColor(getContext(), R.color.green_dark));
        option3Btn.setTextColor(ContextCompat.getColor(getContext(), R.color.green_dark));
        option4Btn.setTextColor(ContextCompat.getColor(getContext(), R.color.green_dark));

        // Включение кнопок
        option1Btn.setEnabled(true);
        option2Btn.setEnabled(true);
        option3Btn.setEnabled(true);
        option4Btn.setEnabled(true);

        // Скрытие объяснения и кнопки nextQuestionBtn
        answerExplanationTv.setVisibility(View.INVISIBLE);
        nextQuestionBtn.setVisibility(View.INVISIBLE);
    }

    private void verifyAnswer(Button button) {
        if (canAnswer) {
            if (button.getText().equals(answer)) {
                button.setBackground(ContextCompat.getDrawable(getContext(), R.color.green_correctAnswer));
                correctAnswers++;
                answerExplanationTv.setText("Верно подмечено! \n" + infoMessage);
            } else {
                button.setBackground(ContextCompat.getDrawable(getContext(), R.color.red_wrongAnswer));
                wrongAnswers++;
                answerExplanationTv.setText("Неправильно, но ничего страшного \n"
                        + "Верный ответ: " + answer + "\n" + infoMessage);

            }
        }
        canAnswer = false;
    }

    private void showNextButton() {
        if (currentQuestionNumber == totalQuestions) {
            nextQuestionBtn.setText("завершить");
            nextQuestionBtn.setVisibility(View.VISIBLE);
            nextQuestionBtn.setEnabled(true);
        } else {
            nextQuestionBtn.setVisibility(View.VISIBLE);
            nextQuestionBtn.setEnabled(true);
            answerExplanationTv.setVisibility(View.VISIBLE);
        }
    }


}