package com.example.diplomaapplication.Model;

import com.google.firebase.firestore.DocumentId;

public class QuestionModel {

    @DocumentId
    private String questionID;
    private String answer, explanation, question, option_a, option_b, option_c, option_d;
    private String question_img, optionA_img, optionB_img, optionC_img, optionD_img;

    public QuestionModel() {}

    public QuestionModel(String questionID, String answer, String explanation, String question, String option_a, String option_b, String option_c, String option_d, String question_img, String optionA_img, String optionB_img, String optionC_img, String optionD_img) {
        this.questionID = questionID;
        this.answer = answer;
        this.explanation = explanation;
        this.question = question;
        this.option_a = option_a;
        this.option_b = option_b;
        this.option_c = option_c;
        this.option_d = option_d;
        this.question_img = question_img;
        this.optionA_img = optionA_img;
        this.optionB_img = optionB_img;
        this.optionC_img = optionC_img;
        this.optionD_img = optionD_img;
    }

    public String getOptionA_img() {
        return optionA_img;
    }

    public void setOptionA_img(String optionA_img) {
        this.optionA_img = optionA_img;
    }

    public String getOptionB_img() {
        return optionB_img;
    }

    public void setOptionB_img(String optionB_img) {
        this.optionB_img = optionB_img;
    }

    public String getOptionC_img() {
        return optionC_img;
    }

    public void setOptionC_img(String optionC_img) {
        this.optionC_img = optionC_img;
    }

    public String getOptionD_img() {
        return optionD_img;
    }

    public void setOptionD_img(String optionD_img) {
        this.optionD_img = optionD_img;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getQuestion_img() {
        return question_img;
    }

    public void setQuestion_img(String question_img) {
        this.question_img = question_img;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getOption_b() {
        return option_b;
    }

    public void setOption_b(String option_b) {
        this.option_b = option_b;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption_a() {
        return option_a;
    }

    public void setOption_a(String option_a) {
        this.option_a = option_a;
    }


    public String getOption_c() {
        return option_c;
    }

    public void setOption_c(String option_c) {
        this.option_c = option_c;
    }

    public String getOption_d() {
        return option_d;
    }

    public void setOption_d(String option_d) {
        this.option_d = option_d;
    }

}
