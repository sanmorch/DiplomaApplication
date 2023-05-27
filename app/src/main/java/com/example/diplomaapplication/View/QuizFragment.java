package com.example.diplomaapplication.View;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.diplomaapplication.R;
import com.example.diplomaapplication.ViewModel.QuestionViewModel;
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
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);



        // Инициализация элементов интерфейса
        progressBar = view.findViewById(R.id.resultProgressBar);
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

        // Назначение обработчика кликов
        option1Btn.setOnClickListener(this);
        option2Btn.setOnClickListener(this);
        option3Btn.setOnClickListener(this);
        option4Btn.setOnClickListener(this);
        nextQuestionBtn.setOnClickListener(this);
        backToSubjectFragmentBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

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

        viewModel.getQuestionMutableLiveData().observe(getViewLifecycleOwner(), questionModels -> {
            questionTv.setText(questionModels.get(i-1).getQuestion());
            option1Btn.setText(questionModels.get(i-1).getOption_a());
            option2Btn.setText(questionModels.get(i-1).getOption_b());
            option3Btn.setText(questionModels.get(i-1).getOption_c());
            option4Btn.setText(questionModels.get(i-1).getOption_d());
            answer = questionModels.get(i-1).getAnswer();
            infoMessage = questionModels.get(i-1).getExplanation();

            questionNumberTv.setText(String.valueOf(currentQuestionNumber));

            // get questionImage if it exists
            if (questionModels.get(i-1).getQuestion_img() != null && !questionModels.get(i-1).getQuestion_img().isEmpty()) {
                Glide.with(view).load(questionModels.get(i-1).getQuestion_img()).into(questionImage);
                questionImage.setVisibility(View.VISIBLE); // Показывать ImageView, если изображение существует
            } else {
                questionImage.setVisibility(View.INVISIBLE);
            }

            // get answer1 image if it exists
            loadAnswerImage(view, questionModels.get(i-1).getOptionA_img(), option1Btn);

            // get answer2 image if it exists
            loadAnswerImage(view, questionModels.get(i-1).getOptionB_img(), option2Btn);

            // get answer3 image if it exists
            loadAnswerImage(view, questionModels.get(i-1).getOptionC_img(), option3Btn);

            // get answer4 image if it exists
            loadAnswerImage(view, questionModels.get(i-1).getOptionD_img(), option4Btn);

        });

        canAnswer = true;
        // get progress of quiz (how many you have already done)
        Long percent = Long.valueOf(currentQuestionNumber * 100 / totalQuestions);
        progressBar.setProgress(percent.intValue());
    }

    private void loadAnswerImage(View view, String imageUrl, Button button) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(view).load(imageUrl)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            resource.setBounds(0,0, resource.getIntrinsicWidth() * 4, resource.getIntrinsicHeight()* 4);
                            button.setCompoundDrawables(null, null, null, resource);
                        }
                    });
        } else {
            button.setCompoundDrawables(null, null, null, null);
        }

    }

    @Override
    public void onClick(View view) {
        // choose your answer
        if (view.getId() == R.id.answer1Button) {
            option1Btn.setTypeface(null, Typeface.BOLD);
            verifyAnswer(option1Btn);
            showNextButton();
        } else if (view.getId() == R.id.answer2Button) {
            option2Btn.setTypeface(null, Typeface.BOLD);
            verifyAnswer(option2Btn);
            showNextButton();
        } else if (view.getId() == R.id.answer3Button) {
            option3Btn.setTypeface(null, Typeface.BOLD);
            verifyAnswer(option3Btn);
            showNextButton();
        } else if (view.getId() == R.id.answer4Button) {
            option4Btn.setTypeface(null, Typeface.BOLD);
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
        QuizFragmentDirections.ActionQuizFragmentToResultFragment action = QuizFragmentDirections.actionQuizFragmentToResultFragment();
        action.setSubjectID(subjectID);
        navController.navigate(action);
    }



    // come back to start station
    private void resetOption() {
        // Сброс фона кнопок на исходный
        option1Btn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_background_answers));
        option1Btn.setTypeface(Typeface.DEFAULT);

        option2Btn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_background_answers));
        option2Btn.setTypeface(Typeface.DEFAULT);

        option3Btn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_background_answers));
        option3Btn.setTypeface(Typeface.DEFAULT);

        option4Btn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_background_answers));
        option4Btn.setTypeface(Typeface.DEFAULT);


        answerExplanationTv.setBackground(ContextCompat.getDrawable(getContext(), R.color.grey_for_background));

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
                //answerExplanationTv.setBackground(ContextCompat.getDrawable(getContext(), R.color.green_correctAnswer));
                button.setTextColor(ContextCompat.getColor(getContext(), R.color.green_correctAnswer));
                correctAnswers++;
                answerExplanationTv.setText("Верно подмечено! \n\n" + infoMessage);
            } else {
                //answerExplanationTv.setBackground(ContextCompat.getDrawable(getContext(), R.color.red_wrongAnswer));
                button.setTextColor(ContextCompat.getColor(getContext(), R.color.red_wrongAnswer));
                wrongAnswers++;
                answerExplanationTv.setText("Неправильно, но ничего страшного \n\n"
                        + "Верный ответ: " + answer + "\n\n" + infoMessage);

            }
        }
        canAnswer = false;
    }

    private void showNextButton() {
        if (currentQuestionNumber == totalQuestions) {
            nextQuestionBtn.setText("завершить");
            nextQuestionBtn.setVisibility(View.VISIBLE);
            answerExplanationTv.setVisibility(View.VISIBLE);
            nextQuestionBtn.setEnabled(true);
        } else {
            nextQuestionBtn.setVisibility(View.VISIBLE);
            nextQuestionBtn.setEnabled(true);
            answerExplanationTv.setVisibility(View.VISIBLE);
        }
    }


}